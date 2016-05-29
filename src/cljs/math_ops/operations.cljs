(ns math-ops.operations
  (:require
    [math-ops.game-levels :as levels]
    [clojure.string :as string]
    [clojure.set :refer [map-invert]]))

(def ^:private symbols-keyword
  {+ :+
   - :-
   * :x
   / :/
   :? :?})

(def symbols-description
  (into {} (map (fn [[k v]] [k (name v)]) symbols-keyword)))

(def ^:private keyword-symbols (map-invert symbols-keyword))

(defn- operation->string [{:keys [op1 operator op2 res]}]
  (str "#operation \""
       (pr-str {:op1 op1
                :operator (symbols-keyword operator)
                :op2 op2
                :res res})
       "\""))

(defrecord Operation [op1 operator op2 res]
  Object
  (toString [this] (operation->string this)))

(extend-protocol IPrintWithWriter
  Operation
  (-pr-writer [this writer _]
    (-write writer (operation->string this))))

(defn read-operation
  [{:keys [op1 operator op2 res]}]
  (->Operation op1 (keyword-symbols operator) op2 res))

(defn- hide-sth [operation]
  (assoc operation (rand-nth [:op1 :op2 :res]) :?))

(defn- random-0-9 []
  (rand-int 10))

(defn invertible? [level {:keys [op1 operator]}]
  (and (levels/allowed-operator? level operator)
       (not (and (= operator *) (= op1 0)))))

(defn- invert [level operation]
  (if (invertible? level operation)
    (->Operation (:res operation)
                 (levels/inverse-operator level (:operator operation))
                 (:op1 operation)
                 (:op2 operation))
    operation))

(defn- invert-may-be [level operation]
  (if (rand-nth [true false])
    (invert level operation)
    operation))

(defn make [level]
  (let [op1 (random-0-9)
        op2 (random-0-9)
        operator (levels/random-operator level)
        res (operator op1 op2)]
    (->> (->Operation op1 operator op2 res)
         (invert-may-be level)
         (hide-sth))))

(defn- find-unknown [operation]
  (ffirst (filter #(= :? (second %)) operation)))

(defn- substitute-unknown [operation guess]
  (assoc operation (find-unknown operation) guess))

(defn- correct? [{:keys [res op1 op2 operator]}]
  (= res (operator op1 op2)))

(defn correct-guess? [operation guess]
  (correct? (substitute-unknown operation guess)))
