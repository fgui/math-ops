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
  ^{:key k} [:a {:on-click #(re-frame/dispatch [:select-level k])} description])

(defn seq-join [separator coll]
  (drop 1 (interleave (repeat separator) coll)))

(defn level-selection-component []
  [:div (seq-join \space (map level-selector (game-levels/available-levels)))])

(defn display-known-symbol [k display-value]
  ^{:key k} [:span.known {:style {:margin "5px"}} display-value])

(defn display-unknown-symbol []
  ^{:key :unknown-symbol} [unknow-component])

(defn display-symbol [k s]
  (let [display-value (get symbols-description s s)]
    (if (= :? s)
      (display-unknown-symbol)
      (display-known-symbol k display-value))))

(defn display-operation [{:keys [op1 op2 operator res]}]
  [:div
   (map display-symbol
        [:op1 :operator :op2 :equal-symbol :res]
        [op1 operator op2 "=" res])])

(defn history-display-component []
  (let [history (re-frame/subscribe [:history])]
    (fn []
      (println "history")
      [:div (map #(display-operation (:operation %)) @history)])))

(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn []
      [:div
       (display-operation @operation)
       [level-component]
       [level-selection-component]
       [history-display-component]])))
