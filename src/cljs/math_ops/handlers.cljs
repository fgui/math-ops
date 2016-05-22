(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations-guessing :as operations-guessing]))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (operations-guessing/start-new-guessing-new {:current-level :max-level})))

(re-frame/register-handler
  :press-key
  (fn [state [_ key-code]]
    (operations-guessing/process-input-new state key-code)))

(re-frame/register-handler
  :select-level
  (fn [state [_ key-level]]
    (->
      state
      (assoc :current-level key-level)
      operations-guessing/start-new-guessing-new)))
