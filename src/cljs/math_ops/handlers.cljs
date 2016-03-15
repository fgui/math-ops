(ns math-ops.handlers
  (:require [re-frame.core :as re-frame]
            [math-ops.operations :as operations]
            ))

(defn new-operation [state]
  (assoc state :operation (operations/make)))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (new-operation {})))

(re-frame/register-handler
  :check-guess
  (fn [state [_ guess]]
    (if (operations/correct-guess? (:operation state) guess)
      (new-operation state)
      state)))
