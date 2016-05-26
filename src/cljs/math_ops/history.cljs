(ns math-ops.history)

(defn- success-or-failure?
  [{current-guessing-input :number-input} {new-guessing-input :number-input}]
  (and (not= current-guessing-input "?")
       (= new-guessing-input "?")))

(defn record [current-guessing new-guessing history]
  (if (success-or-failure? current-guessing new-guessing)
    (conj history current-guessing)
    history))
