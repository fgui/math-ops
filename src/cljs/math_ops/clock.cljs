(ns math-ops.clock)

(defn current-time-ms []
  (.getTime (js/Date.)))
