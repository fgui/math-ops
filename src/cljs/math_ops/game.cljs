(ns math-ops.game)

(defrecord Operation [op1 operator op2 res])

(defn hide-sth [operation] (assoc operation (rand-nth  [:op1 :op2 :res]) :?))

(defn random-0-9 [] (rand-int 10))

(defn make-operation []
  (let [op1 (random-0-9)
        op2 (random-0-9)
        res (* op1 op2)]
    (hide-sth (->Operation op1 :* op2 res))))
