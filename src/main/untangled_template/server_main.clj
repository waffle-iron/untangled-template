(ns untangled-template.server-main
  (:require
    [com.stuartsierra.component :as component]
    [untangled.server :as c]
    [taoensso.timbre :as timbre]
    [untangled-template.server :refer [make-system]])
  (:gen-class))

(def config-path "/usr/local/etc/untangled_template.edn")
(def production-config-component (c/new-config config-path))

;; This is a separate file, so we can control the server in dev mode from user.clj
(defn -main [& args]
  (let [system (make-system config-path)]
    (component/start system)))
