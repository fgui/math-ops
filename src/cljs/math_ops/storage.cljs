(ns math-ops.storage
  (:require
   [hodgepodge.core :refer [local-storage]]))

(defprotocol Storage
  (save-state! [this state])
  (retrieve-state [this]))

(defrecord LocalStorage []
  Storage
  (save-state! [_ state]
    (assoc! local-storage :math-ops-state state))
  (retrieve-state [_]
    (get local-storage :math-ops-state)))

(def storage (atom (->LocalStorage)))

(defn use-local-storage []
  (reset! storage (->LocalStorage)))

(defn state->storage! [state]
  (save-state! @storage state))

(defn storage->state []
  (retrieve-state @storage))
