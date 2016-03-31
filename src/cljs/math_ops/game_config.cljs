(ns math-ops.game-config)

(def levels {:init-level {:operators [+] :inverse-operators {}}
             :max-level {:operators [+ *]
                         :inverse-operators {+ - * /}}})
