(ns math-ops.operations-guessing
  (:require
    [math-ops.operations :as operations]
    [math-ops.history :as history]
    [math-ops.clock :as clock]))

(defn start-new-guessing [{:keys [current-level] :as state}]
  (merge state
         {:operation (operations/make current-level)
          :number-input "?"}))

(defn start-new-guessing-new [{:keys [current-level] :as state}]
  (assoc state :guessing {:operation (operations/make current-level)
                          :number-input "?"
                          :level current-level
                          :init-time (clock/current-time-ms)}))

(defn- numeric? [c]
  (not (js/isNaN c)))

(defn- add-char [{:keys [number-input] :as state} c]
  (if (numeric? c)
    (assoc state :number-input (apply str (remove #{"?"} (str number-input c))))
    state))

(defn add-char-new [{:keys [guessing] :as state} c]
  (if (numeric? c)
    (assoc
      state
      :guessing
      (assoc
        guessing
        :number-input
        (apply str (remove #{"?"} (str (:number-input guessing) c)))))
    state))

(defn- correct-guess? [{:keys [operation number-input]}]
  (operations/correct-guess? operation (int number-input)))

(defn- retry-current-guessing [state]
  (assoc state :number-input "?"))

(defn- check-input-number [state]
  (let [state (history/record state)]
    (if (correct-guess? state)
      (start-new-guessing state)
      (retry-current-guessing state))))

(defn- check-input-number-new [state]
  (let [state (history/record state)]
    (if (correct-guess? (:guessing state))
      (start-new-guessing-new state)
      (assoc state :guessing (retry-current-guessing (:guessing state))))))

(defn- return-pressed? [key-code]
  (= 13 key-code))

(defn process-input [state key-code]
  (if (return-pressed? key-code)
    (check-input-number state)
    (add-char state (char key-code))))

(defn process-input-new [state key-code]
  (if (return-pressed? key-code)
    (check-input-number-new state)
    (add-char-new state (char key-code))))
