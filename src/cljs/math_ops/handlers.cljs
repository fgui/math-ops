(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations-guessing :as operations-guessing]))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (assoc
     (operations-guessing/start :max-level)
     :current-level :max-level)))

(re-frame/register-handler
 :press-key
 (fn [state [_ key-code]]
   (merge
    state
    (operations-guessing/process-input state key-code)
    )))

(re-frame/register-handler
 :select-level
 (fn [state [_ key-level]]
   (->
    state
    (assoc
     :current-level
     key-level
     )
    (merge
     (operations-guessing/start key-level)
     )
    )))
