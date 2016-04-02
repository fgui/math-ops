(ns math-ops.game-levels)

(def ^:private levels
  {:init-level {:operators [+]
                :inverse-operators {}}
   :max-level {:operators [+ *]
               :inverse-operators {+ - * /}}})

(defn- operators [level]
  (get-in levels [level :operators]))

(defn- inverse-operators [level]
  (get-in levels [level :inverse-operators]))

(defn inverse-operator [level operator]
  ((inverse-operators level) operator))

(defn random-operator [level]
  (rand-nth (operators level)))

(defn allowed-operator? [level operator]
  (contains? (inverse-operators level) operator))