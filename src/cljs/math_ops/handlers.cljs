(ns math-ops.handlers
  (:require [re-frame.core :as re-frame]
            [math-ops.operations :as operations]
            ))

(defn initialize []
  {:operation (operations/make)
   :number-input "?"})

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    (initialize)))

(defn numeric? [c]
  (not (js/isNaN c)))

(defn add-char [{:keys [number-input] :as state} c]
  (if (numeric? c)
    (assoc state :number-input (apply str (remove #{"?"} (str number-input c))))
    state))

(defn check-input-number [{:keys [operation number-input] :as state}]
  (if (operations/correct-guess? operation (int number-input))
    (initialize)
    (assoc state :number-input "?")))

(defn return-pressed? [key-code]
  (= 13 key-code))

(defn process-key [state key-code]
  (if (return-pressed? key-code)
    (check-input-number state)
    (add-char state (char key-code))))

(re-frame/register-handler
  :press-key
  (fn [state [_ key-code]]
    (process-key state key-code)))

