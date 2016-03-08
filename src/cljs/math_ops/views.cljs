(ns math-ops.views
  (:require [re-frame.core :as re-frame]))

(def string-by-keyword {:* "x" :? "?"})

(defn pprint-keyword [s]
  (get string-by-keyword s s))

(defn display [{:keys [op1 op2 operator res]}]
  [:div (clojure.string/join " " (map pprint-keyword [op1 operator op2 "=" res]))])

(defn check [guess-input]
  (re-frame/dispatch [:check-guess (int @guess-input)])
  (reset! guess-input ""))

(defn atom-input [value]
  [:input {:type :text
           :size 4
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn guessing-component []
  (let [guess (reagent.ratom/atom "")]
    (fn []
      [:div
       [:div "Guess: " (atom-input guess)]
       [:div [:button {:on-click #(check guess)} "Check"]]])))

(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn []
      [:div
       (display @operation)
       [guessing-component]])))
