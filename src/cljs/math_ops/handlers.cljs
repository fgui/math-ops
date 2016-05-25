(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations-guessing :as operations-guessing]))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    {:current-level :max-level
     :guessing      (operations-guessing/start-new-guessing :max-level)}))

(re-frame/register-handler
  :press-key
  (fn [{:keys [guessing current-level] :as state} [_ key-code]]
    (assoc state
      :guessing (operations-guessing/process-input guessing current-level key-code))))

(re-frame/register-handler
  :select-level
  (fn [state [_ key-level]]
    (-> state
        (assoc :current-level key-level)
        (assoc :guessing (operations-guessing/start-new-guessing key-level)))))
