(ns math-ops.history)

(defn record [{:keys [current-level history operation number-input] :as state}]
  (if (nil? history)
    (merge state {:history [{:operation operation
                             :number-input number-input
                             :current-level current-level}]})
    state))
