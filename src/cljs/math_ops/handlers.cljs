(ns math-ops.handlers
  (:require [re-frame.core :as re-frame]
            [math-ops.game :as game]
            ))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    {:operation
     (game/make-operation)
     }))

(defn new-operation [state]
  (assoc state :operation (game/make-operation)))

(re-frame/register-handler
  :check-guess
  (fn [state [_ guess]]
    (if (game/correct-guess? (:operation state) guess)
      (new-operation state)
      state)))
