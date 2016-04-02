(ns math-ops.game-levels-config)

(def levels
  {:init-level {:operators [+]
                :inverse-operators {}}
   :max-level {:operators [+ *]
               :inverse-operators {+ - * /}}})
