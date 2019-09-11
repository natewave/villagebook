(ns villagebookUI.components.sidebar
  (:require [reagent.core :as r]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.new-org :refer [new-org]]
            [villagebookUI.api.organisation :as org]))

(defn sidebar []
  (let [creating-org (r/atom false)]
    (fn []
      [:div
       [:div.sidebar
        [:div.text-center.sidebar-logo-box
         [:p.sidebar-logo "villagebook"]
         [:div.divider]]
        [:div.sidebar-section
         [:p.sidebar-section-head "Organisations"]
         [:ul.sidebar-section-list
          [:li.item
           [:a.sidebar-link [utils/label "#5fcc5f"] "Org 1"]]
          [:li.item
           [:a.sidebar-link [utils/label "#ff8383"] "Org 2"]]
          [:li.item
           (if @creating-org
             [new-org #(reset! creating-org false)]
             [:a.sidebar-link {:on-click #(reset! creating-org true)} "+ Create new"])]]]]])))
