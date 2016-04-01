(ns math-ops.views
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations :refer [symbols-description]]))

(defn unknow-component []
  (let [number-input (re-frame/subscribe [:number-input])]
    (fn []
      [:span.unknown {:style {:margin "5px"}}
       @number-input])))

(defn display-symbol [s]
  (let [display-value (get symbols-description s s)]
    (if (= :? s)
      [unknow-component]
      [:span.known {:style {:margin "5px"}} display-value])))

(defn display [{:keys [op1 op2 operator res]}]
  [:div (map display-symbol [op1 operator op2 "=" res])])

(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn [] [:div (display @operation)])))
