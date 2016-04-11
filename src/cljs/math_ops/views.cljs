(ns math-ops.views
  (:require
    [math-ops.game-levels :as game-levels]
    [re-frame.core :as re-frame]
    [math-ops.operations :refer [symbols-description]]))

(defn unknow-component []
  (let [number-input (re-frame/subscribe [:number-input])]
    (fn []
      [:span.unknown {:style {:margin "5px"}}
       @number-input])))

(defn level-component []
  (let [current-level (re-frame/subscribe [:current-level])]
    (fn []
      [:div (str "level: " (game-levels/name @current-level))])))

(defn level-selector [[k description]]
  [:a {:key k
       :on-click #(re-frame/dispatch [:select-level k])}
   description])

(defn level-selection-component []
  [:div (map level-selector (game-levels/available-levels))])

(defn display-symbol [s]
  (let [display-value (get symbols-description s s)]
    (if (= :? s)
      [unknow-component]
      [:span.known {:style {:margin "5px"}} display-value])))

(defn display [{:keys [op1 op2 operator res]}]
  [:div (map display-symbol [op1 operator op2 "=" res])])

(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn [] [:div (display @operation)
            [level-component]
            [level-selection-component]])))
