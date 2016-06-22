(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations-guessing :as operations-guessing]
    [math-ops.history :as history]
    [hodgepodge.core :refer [local-storage]]
    [math-ops.keyboard :as keyboard]))

(defn state->storage! [state]
  (assoc! local-storage :math-ops-state state))

(defn storage->state []
  (get local-storage :math-ops-state))

(defmulti should-store?
          (fn [[event-type _]]
            event-type))

(defmethod should-store? :press-key [[_ key-code]]
  (keyboard/return-pressed? key-code))

(defmethod should-store? :default [_]
  true)

(defn store-middleware [handler]
  (fn [state event]
    (let [new-state (handler state event)]
      (when (should-store? event)
        (state->storage! new-state))
      new-state)))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (merge {:current-level :max-level
            :guessing (operations-guessing/start-new-guessing :max-level)
            :history []}
           (storage->state))))

(re-frame/register-handler
  :press-key
  store-middleware
  (fn [{:keys [history guessing current-level] :as state} [_ key-code]]
    (let [new-guessing (operations-guessing/process-input guessing current-level key-code)]
      (-> state
          (assoc :history (history/record guessing new-guessing history))
          (assoc :guessing new-guessing)))))

(re-frame/register-handler
  :select-level
  store-middleware
  (fn [state [_ key-level]]
    (-> state
        (assoc :current-level key-level)
        (assoc :guessing (operations-guessing/start-new-guessing key-level)))))
