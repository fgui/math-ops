(ns math-ops.operations-guessing
  (:require
    [math-ops.operations :as operations]
    [math-ops.clock :as clock]
    [math-ops.keyboard :as keyboard]
    [clojure.string :as string]))

(defn start-new-guessing [current-level]
  {:operation (operations/make current-level)
   :number-input "?"
   :level current-level
   :init-time (clock/current-time-ms)})

(defn- numeric? [c]
  (not (js/isNaN c)))

(defn- add-char [guessing c]
  (if (numeric? c)
    (let [number-input
          (apply str (remove #{"?"} (str (:number-input guessing) c)))]
      (assoc guessing :number-input number-input))
    guessing))

(defn- remove-last-char [guessing]
  (let [number-input (apply str (butlast (:number-input guessing)))]
    (assoc guessing :number-input number-input)))

(defn- correct-guess? [{:keys [operation number-input]}]
  (operations/correct-guess? operation (int number-input)))

(defn- retry-current-guessing [guessing]
  (assoc guessing :number-input "?"))

(defn- check-guess [{:keys [number-input] :as guessing} current-level]
  (if (string/blank? number-input)
    guessing
    (if (correct-guess? guessing)
      (start-new-guessing current-level)
      (retry-current-guessing guessing))))

(defn process-input [guessing current-level key-code]
  (cond
    (keyboard/return-pressed? key-code)
    (check-guess guessing current-level)

    (keyboard/backspace-pressed? key-code)
    (remove-last-char guessing)

    :else
    (add-char guessing (char key-code))))
