(ns math-ops.core
    (:require [reagent.core :as reagent]
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

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-state])
  (mount-root))
