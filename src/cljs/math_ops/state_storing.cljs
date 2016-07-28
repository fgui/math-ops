(ns math-ops.state-storing
  (:require
    [math-ops.keyboard :as keyboard]
    [math-ops.storage :as storage]))

(defmulti should-store?
          (fn [[event-type _]]
            event-type))

(defmethod should-store? :press-key [[_ key-code]]
  (keyboard/return-pressed? key-code))

(defmethod should-store? :default [_]
  true)

(defn load-state []
  (storage/storage->state))

(defn middleware [handler]
  (fn [state event]
    (let [new-state (handler state event)]
      (when (should-store? event)
        (storage/state->storage! new-state))
      new-state)))
