(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operation-guessing :as operation-guessing]))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (operation-guessing/start)))

(re-frame/register-handler
  :press-key
  (fn [state [_ key-code]]
    (operation-guessing/process-input state key-code)))
