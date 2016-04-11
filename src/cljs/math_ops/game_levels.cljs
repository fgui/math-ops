(ns math-ops.game-levels
  (:refer-clojure :exclude [name]))

(def ^:private levels
  {:init-level {:name "1"
                :operators [+]
                :inverse-operators {}}
   :max-level {:name "max"
               :operators [+ *]
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

(defn name [level]
  (get-in levels [level :name]))

(defn available-levels []
  (map (juxt #(first %) #(-> % second :name)) levels))
