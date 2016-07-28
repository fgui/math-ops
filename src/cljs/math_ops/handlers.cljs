(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations-guessing :as operations-guessing]
    [math-ops.history :as history]
    [math-ops.state-storing :as state-storing]))

(defn- initialize-state []
  {:current-level :max-level
   :guessing (operations-guessing/start-new-guessing :max-level)
   :history []})

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (if-let [stored-state (state-storing/load-state)]
      stored-state
      (initialize-state))))

(re-frame/register-handler
  :press-key
  state-storing/middleware
  (fn [{:keys [history guessing current-level] :as state} [_ key-code]]
    (let [new-guessing (operations-guessing/process-input guessing current-level key-code)]
      (-> state
          (assoc :history (history/record guessing new-guessing history))
          (assoc :guessing new-guessing)))))

(re-frame/register-handler
  :select-level
  state-storing/middleware
  (fn [state [_ new-level]]
    (-> state
        (assoc :current-level new-level)
        (assoc :guessing (operations-guessing/start-new-guessing new-level)))))
