(ns math-ops.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :operation
  (fn [state]
    (reaction (:operation @state))))

(re-frame/register-sub
 :number-input
 (fn [state]
   (reaction (:number-input @state))))

(re-frame/register-sub
 :current-level
 (fn [state]
   (reaction (:current-level @state))))
