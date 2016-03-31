(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations-guessing :as operations-guessing]))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (operations-guessing/start)))

(re-frame/register-handler
  :press-key
  (fn [state [_ key-code]]
    (operations-guessing/process-input state key-code)))
