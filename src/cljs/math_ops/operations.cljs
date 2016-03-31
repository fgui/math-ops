(ns math-ops.operations
  (:require [math-ops.game-config :as config]))

(def ^:private operator-description
  {+ "+"
   - "-"
   * "*"
   / "/"})

(defrecord Operation [op1 operator op2 res]
  Object
  (toString [_]
    (clojure.string/join " " [op1 (operator-description operator) op2 "=" res])))

(defn operators [level] (get-in config/levels [level :operators]))

(defn hide-sth [operation]
  (assoc operation (rand-nth [:op1 :op2 :res]) :?))

(defn random-0-9 [] (rand-int 10))

(defn random-operation [level]
  (rand-nth (operators level)))

(defn inverse-operator [level operator]
  (let [inverse-operators (get-in config/levels [level :inverse-operators])]
    (inverse-operators operator)))

(defn invertible? [level {:keys [op1 operator]}]
  (and (contains? (get-in config/levels [level :inverse-operators]) operator)
       (not (and (= operator *) (= op1 0)))))

(defn invert [level operation]
  (if (invertible? level operation)
    (->Operation (:res operation)
                 (inverse-operator level (:operator operation))
                 (:op1 operation)
                 (:op2 operation))
    operation))

(defn invert-may-be [level operation]
  (if (rand-nth [true false])
    (invert level operation)
    operation))

(defn make [level]
  (let [op1 (random-0-9)
        op2 (random-0-9)
        operator (random-operation level)
        res (operator op1 op2)]
    (->> (->Operation op1 operator op2 res)
        (invert-may-be level)
        (hide-sth))))

(defn find-unknown [operation]
  (ffirst
    (filter #(= :? (second %))
            operation)))

(defn substitute-unknown [operation guess]
  (assoc operation (find-unknown operation) guess))

(defn correct? [{:keys [res op1 op2 operator]}]
  (= res (operator op1 op2)))

(defn correct-guess? [operation guess]
  (correct? (substitute-unknown operation guess)))
