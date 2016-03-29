(ns math-ops.operation-guessing
  (:require
    [math-ops.operations :as operations]))

(defn start []
  {:operation (operations/make)
   :number-input "?"})

(defn- numeric? [c]
  (not (js/isNaN c)))

(defn- add-char [{:keys [number-input] :as state} c]
  (if (numeric? c)
    (assoc state :number-input (apply str (remove #{"?"} (str number-input c))))
    state))

(defn- check-input-number [{:keys [operation number-input] :as state}]
  (if (operations/correct-guess? operation (int number-input))
    (start)
    (assoc state :number-input "?")))

(defn- return-pressed? [key-code]
  (= 13 key-code))

(defn process-input [state key-code]
  (if (return-pressed? key-code)
    (check-input-number state)
    (add-char state (char key-code))))
