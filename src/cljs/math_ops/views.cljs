(ns math-ops.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(def string-by-keyword {* "x" + "+" - "-" / "/" :? "?"})

(defn check [guess-input]
  (re-frame/dispatch [:check-guess (int @guess-input)])
  (reset! guess-input "?"))

(defn add-char [s c]
  (apply str (remove #(= % "?") (str s c)))
  )

(defn process-key [input-atom key-code]
  (if (= 13 key-code)
    (check input-atom)
    (let [c (char key-code)]
     (swap! input-atom add-char c)))
  )

(defn unknow-component [display-value]
  (let [input (reagent.ratom/atom display-value)]
    (fn []
      [:span.unknown {:style {:margin "5px"}
                      :tab-index 1
                      :on-key-press #(process-key input (.-charCode  %1))} @input])))

(defn pprint-keyword [s]
  (let [display-value (get string-by-keyword s s)]
    (if (= :? s)
      [unknow-component display-value]
      [:span.known {:style {:margin "5px"}} display-value])))

(defn display [{:keys [op1 op2 operator res]}]
  [:div (map pprint-keyword [op1 operator op2 "=" res])])


(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn []
      [:div
       (display @operation)
       ])))
