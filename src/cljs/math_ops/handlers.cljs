(ns math-ops.handlers
    (:require [re-frame.core :as re-frame]
              ))

(re-frame/register-handler
 :initialize-state
 (fn  [_ _]
   {:operation
    {:op1 5 :op2 3 :operator :* :res :?}}))
