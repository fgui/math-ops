(ns math-ops.views
  (:require
    [re-frame.core :as re-frame]))

(def string-by-keyword {* "x" + "+" - "-" / "/" :? "?"})

(defn unknow-component []
  (let [number-input (re-frame/subscribe [:number-input])]
    (fn []
      [:span.unknown {:style {:margin "5px"}}
       @number-input])))

(defn pprint-keyword [s]
  (let [display-value (get string-by-keyword s s)]
    (if (= :? s)
      [unknow-component]
      [:span.known {:style {:margin "5px"}} display-value])))

(defn display [{:keys [op1 op2 operator res]}]
  [:div (map pprint-keyword [op1 operator op2 "=" res])])


(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn []
      [:div (display @operation)])))
