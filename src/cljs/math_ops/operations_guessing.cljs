(ns math-ops.operations-guessing
  (:require
    [math-ops.operations :as operations]
    [math-ops.clock :as clock]))

(defn start-new-guessing [current-level]
  {:operation (operations/make current-level)
   :number-input "?"
   :level current-level
   :init-time (clock/current-time-ms)})

(defn- numeric? [c]
  (not (js/isNaN c)))

(defn add-char [guessing c]
  (if (numeric? c)
    (assoc
      guessing
      :number-input
      (apply str (remove #{"?"} (str (:number-input guessing) c))))
    guessing))

(defn- correct-guess? [{:keys [operation number-input]}]
  (operations/correct-guess? operation (int number-input)))

(defn- retry-current-guessing [guessing]
  (assoc guessing :number-input "?"))

(defn- check-input-number [guessing current-level]
  (if (correct-guess? guessing)
    (start-new-guessing current-level)
    (retry-current-guessing guessing)))

(defn- return-pressed? [key-code]
  (= 13 key-code))

(defn process-input [guessing current-level key-code]
  (if (return-pressed? key-code)
    (check-input-number guessing current-level)
    (add-char guessing (char key-code))))
