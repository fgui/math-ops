(ns math-ops.operations-test
  (:require
    [cljs.test :refer-macros [deftest is testing]]
    [math-ops.operations :refer [->Operation correct-guess? invert]]))

(def operation ->Operation)

(deftest checking-that-a-guess-for-the-unknown-in-an-operation-is-correct
  (is (true? (correct-guess?
               (operation 1 + 3 :?) 4)))

  (is (false? (correct-guess?
               (operation 1 * 3 :?) 4)))

  (is (true? (correct-guess?
                (operation :? * 3 9) 3)))

  (is (true? (correct-guess?
               (operation 2 * :? 16) 8))))

(deftest inverting-operations
  (is (= (operation 4 - 1 3) (invert (operation 1 + 3 4))))
  (is (= (operation 15 / 5 3) (invert (operation 5 * 3 15)))))