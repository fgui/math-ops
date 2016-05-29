(ns math-ops.handlers
  (:require
   [re-frame.core :as re-frame]
   [math-ops.operations-guessing :as operations-guessing]
   [math-ops.history :as history]
   [hodgepodge.core :refer [local-storage]]))

(defn state->storage [state]
  (assoc! local-storage :math-ops-state state)
  state)

(defn storage->state []
  (get local-storage :math-ops-state))

(re-frame/register-handler
 :initialize-state
 (fn [_ _]
   (merge {:current-level :max-level
           :guessing (operations-guessing/start-new-guessing :max-level)
           :history []}
          (storage->state))))

(re-frame/register-handler
 :press-key
 (fn [{:keys [history guessing current-level] :as state} [_ key-code]]
   (let [new-guessing (operations-guessing/process-input guessing current-level key-code)]
     (println "changing history")
     (-> state
         (assoc :history (history/record guessing new-guessing history))
         ((fn [state] (print (:history state))
            state))
         (assoc :guessing new-guessing)))))

(re-frame/register-handler
 :select-level
 (fn [state [_ key-level]]
   (-> state
       (assoc :current-level key-level)
       (assoc :guessing (operations-guessing/start-new-guessing key-level)))))
