(ns math-ops.game)

(defrecord Operation [op1 operator op2 res])

(defn hide-sth [operation]
  (assoc operation (rand-nth [:op1 :op2 :res]) :?))

(defn random-0-9 [] (rand-int 10))

(defn make-operation []
  (let [op1 (random-0-9)
        op2 (random-0-9)
        res (* op1 op2)]
    (hide-sth (->Operation op1 :* op2 res))))

(defn find-unknown [operation]
  (ffirst
    (filter #(= :? (second %))
            operation)))

(defn substitute-unknown [operation guess]
  (assoc operation (find-unknown operation) guess))

(defn apply-operator [operator & ops]
  (let [operators {:* *}]
    (apply (operators operator) ops)))

(defn correct? [{:keys [res op1 op2 operator]}]
  (= res (apply-operator operator op1 op2)))

(defn correct-guess? [operation guess]
  (correct? (substitute-unknown operation guess)))
