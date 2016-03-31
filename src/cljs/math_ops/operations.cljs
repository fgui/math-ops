(ns math-ops.operations)

(def ^:private operator-description
  {+ "+"
   - "-"
   * "*"
   / "/"})

(defrecord Operation [op1 operator op2 res]
  Object
  (toString [_]
    (clojure.string/join " " [op1 (operator-description operator) op2 "=" res])))

(def operators [* +])

(defn hide-sth [operation]
  (assoc operation (rand-nth [:op1 :op2 :res]) :?))

(defn random-0-9 [] (rand-int 10))

(defn random-operation []
  (rand-nth operators))

(defn inverse-operator [operator]
  (let [inverse-operators {+ - * /}]
    (inverse-operators operator)))

(defn invertible? [{:keys [op1 operator]}]
  (not (and (= operator *) (= op1 0))))

(defn invert [operation]
  (if (invertible? operation)
    (->Operation (:res operation)
                 (inverse-operator (:operator operation))
                 (:op1 operation)
                 (:op2 operation))
    operation))

(defn invert-may-be [operation]
  (if (rand-nth [true false])
    (invert operation)
    operation))

(defn make []
  (let [op1 (random-0-9)
        op2 (random-0-9)
        operator (random-operation)
        res (operator op1 op2)]
    (-> (->Operation op1 operator op2 res)
        (invert-may-be)
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
