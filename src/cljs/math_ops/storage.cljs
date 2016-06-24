(ns math-ops.storage
  (:require
   [hodgepodge.core :refer [local-storage]]))

(defn state->storage! [state]
  (assoc! local-storage :math-ops-state state))

(defn storage->state []
  (get local-storage :math-ops-state))
