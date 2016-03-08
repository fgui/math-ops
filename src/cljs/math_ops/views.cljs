(ns math-ops.views
    (:require [re-frame.core :as re-frame]))

(def string-by-keyword {:* "x" :? "?"})

(defn pprint-keyword [s]
  (get string-by-keyword s s)
  )

(defn display [{:keys [op1 op2 operator res]}]
  [:div (clojure.string/join " " (map pprint-keyword [op1 operator op2 "=" res]))]
  )

(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn []
      [:div
       (display @operation)
       [:div "Guess: " [:input {:type :text :size 4}]]
       [:div [:button "Check"]]
       ])))
