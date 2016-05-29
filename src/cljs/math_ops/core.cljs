(ns math-ops.core
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [math-ops.handlers]
    [math-ops.subs]
    [math-ops.views :as views]
    [math-ops.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn on-key-press [event]
  (re-frame/dispatch [:press-key (.-charCode event)]))

(defn listen-key-press []
  (.addEventListener js/document "keypress" on-key-press))

(defn read-operation [o]
  (let [values (cljs.reader/read-string o)]
    (math-ops.operations/read-operation values)))

(defn register-reader-operation []
  (println "registes-reader")
  (cljs.reader/register-tag-parser! "operation" read-operation))

(defn ^:export init []
  (register-reader-operation)
  (re-frame/dispatch-sync [:initialize-state])
  (listen-key-press)
  (mount-root))
