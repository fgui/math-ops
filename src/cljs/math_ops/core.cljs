(ns math-ops.core
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [math-ops.handlers]
    [math-ops.subs]
    [math-ops.views :as views]
    [math-ops.config :as config]
    [math-ops.keyboard :as keyboard]))

(when config/debug?
  (println "dev mode"))

(defn on-key-press [event]
  (re-frame/dispatch
    [:press-key (keyboard/key-code event)]))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn listen-key-press []
  (.addEventListener
    js/document "keypress" on-key-press))

(defn read-operation [o]
  (let [values (cljs.reader/read-string o)]
    (math-ops.operations/read-operation values)))

(defn register-reader-operation []
  (cljs.reader/register-tag-parser! "operation" read-operation))

(defn ^:export init []
  (math-ops.storage/use-local-storage!)
  (register-reader-operation)
  (re-frame/dispatch-sync [:initialize-state])
  (listen-key-press)
  (mount-root))
