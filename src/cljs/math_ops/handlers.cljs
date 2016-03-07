(ns math-ops.handlers
  (:require [re-frame.core :as re-frame]
            [math-ops.game :as game]
              ))

(re-frame/register-handler
 :initialize-state
 (fn  [_ _]
   {:operation
    (game/make-operation)
    }))
