(ns math-ops.views
  (:require
    [math-ops.game-levels :as game-levels]
    [re-frame.core :as re-frame]
    [math-ops.operations :refer [symbols-description correct-guess?]])
  )

(defn with-key [k component]
  (with-meta component {:key k}))

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
  (with-key k [:a {:on-click #(re-frame/dispatch [:select-level k])} description]))

(defn seq-join [separator coll]
  (drop 1 (interleave (repeat separator) coll)))

(defn level-selection-component []
  [:div (seq-join \space (map level-selector (game-levels/available-levels)))])

(defn display-known-symbol [k display-value]
  (with-key k [:span.known {:style {:margin "5px"}} display-value]))

(defn display-unknown-symbol [_]
  (with-key :unknown-symbol [unknow-component]))

(defn make-display-answer-symbol [answer]
  (fn [k]
    (with-key k [:span.known {:style {:margin "5px"
                                      :font-weight :bold}} answer])))

(defn display-symbol [k s f-display-unknown-symbol]
  (let [display-value (get symbols-description s s)]
    (if (= :? s)
      (f-display-unknown-symbol k)
      (display-known-symbol k display-value))))

(defn display-operation [{:keys [op1 op2 operator res]}
                         f-display-unknown-symbol]
  [:div
   (map #(display-symbol %1 %2 f-display-unknown-symbol)
        [:op1 :operator :op2 :equal-symbol :res]
        [op1 operator op2 "=" res])])

(defn display-guessing-result [{:keys [operation number-input]}]
  (let [correct? (correct-guess? operation number-input)]
    ;; TODO color with css
    [:div {:style {:color (if correct? :green :red)}}
     (display-operation operation
                        (make-display-answer-symbol number-input))]))

(defn history-display-component []
  (let [history (re-frame/subscribe [:history])]
    (fn []
      (println "history")
      [:div (map display-guessing-result @history)])))

(defn main-panel []
  (let [operation (re-frame/subscribe [:operation])]
    (fn []
      [:div
       (display-operation @operation display-unknown-symbol)
       [level-component]
       [level-selection-component]
       [history-display-component]])))
