(ns math-ops.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(defn react-to
  ([react-key] (react-to react-key react-key))
  ([react-key state-key]
   (re-frame/register-sub
    react-key
    (fn [state]
      (reaction (state-key @state))))))

(react-to :operation)
(react-to :number-input)
(react-to :current-level)
