(ns math-ops.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :operation
 (fn [state]
   (reaction (:operation @state))))
