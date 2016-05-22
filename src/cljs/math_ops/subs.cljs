(ns math-ops.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(defn react-to
  ([react-key] (react-to react-key [react-key]))
  ([react-key state-keys]
   (re-frame/register-sub
     react-key
     (fn [state]
       (reaction (get-in @state state-keys))))))

(react-to :operation [:guessing :operation])
(react-to :number-input [:guessing :number-input])
(react-to :current-level)
