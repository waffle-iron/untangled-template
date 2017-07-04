(ns untangled-template.ui.user
  (:require
    [om.dom :as dom]
    [om.next :as om :refer-macros [defui]]
    [untangled.client.core :as uc]))

(defui ^:once User
  static om/IQuery
  (query [this] [:uid :name :email])
  static om/Ident
  (ident [this props] [:user/by-id (:uid props)])
  Object
  (render [this]
    (dom/span nil (get (om/props this) :name))))

(def ui-user (om/factory User {:keyfn :uid}))
