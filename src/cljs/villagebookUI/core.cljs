(ns villagebookUI.core
  (:require [reagent.core :as r]
            [accountant.core :as accountant]
            [bidi.bidi :as bidi]

            [villagebookUI.fetchers :as fetchers]
            [villagebookUI.routes :refer [routes]]
            [villagebookUI.store.core :as store]
            [villagebookUI.store.session :as session]))

(accountant/configure-navigation!
 {:nav-handler (fn [path]
                 (let [matched-route (bidi/match-route routes path)
                       current-page  (:handler matched-route)
                       route-params  (:route-params matched-route)]
                   (session/set! {:current-page current-page
                                  :route-params route-params})))
  :path-exists? (fn [path]
                  (boolean (bidi/match-route routes path)))})

(defn root []
  "Root component holding all other components"
  (let [component (session/current-page)]
    [component]))

(defn main! []
  (store/init!)
  (fetchers/fetch-user!)
  (accountant/dispatch-current!)
  (r/render [root]
            (.getElementById js/document "villagebook-app")))

(defn reload! []
  (main!)
  (prn "Reloaded"))
