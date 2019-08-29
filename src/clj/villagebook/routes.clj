(ns villagebook.routes
  (:require [bidi.ring :refer [make-handler resources]]
            [buddy.auth.middleware :refer [wrap-authorization]]

            [villagebook.middleware :refer [with-auth]]
            [villagebook.user.handlers :as user]
            [villagebook.organisation.handlers :as org]
            [villagebook.handlers :refer [api-handler frontend-handler]]
            [villagebook.config :as config]))

(def apiroutes
  {"organisations" {["/" :id] {:get (with-auth org/get-by-id)}
                    :post     (with-auth org/create!)}
   "user"          {:get (with-auth user/retrieve)}})


;; Setup routes
(def routes
  ["/" {"assets" (resources {:prefix "public/assets/"})
        "api/"   apiroutes
        "signup" {:post user/signup}
        "login"  {:post user/login}
        true     frontend-handler}])
