(ns math-ops.keyboard)

(defn return-pressed? [key-code]
  (= 13 key-code))

(defn key-code [event]
  (let [key-code (.-keyCode event)]
    (if (zero? key-code)
      (.-charCode event)
      key-code)))
