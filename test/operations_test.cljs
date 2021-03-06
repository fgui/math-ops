(ns math-ops.operations-test
  (:require
    [cljs.test :refer-macros [deftest is testing]]
    [math-ops.operations :refer [->Operation correct-guess? invert]]))

(def operation ->Operation)

(deftest operations-tests
  (testing "a guess for the unknown in an operation is correct"
    (is (true? (correct-guess? (operation 1 + 3 :?) 4)))
    (is (false? (correct-guess? (operation 1 * 3 :?) 4)))
    (is (true? (correct-guess? (operation :? * 3 9) 3)))
    (is (true? (correct-guess? (operation 2 * :? 16) 8))))

  (testing "invertible operations are inverted correctly"
    (is (= (operation 4 - 1 3) (invert :max-level (operation 1 + 3 4))))
    (is (= (operation 15 / 5 3) (invert :max-level (operation 5 * 3 15))))
    (is (= (operation 0 / 3 0) (invert :max-level (operation 3 * 0 0)))))

  (testing "not invertible operations are not inverted"
    (let [some-non-invertible-operation (operation 0 * 0 0)
          another-non-invertible-operation (operation 0 * 3 0)]
      (is (= some-non-invertible-operation (invert :max-level some-non-invertible-operation)))
      (is (= another-non-invertible-operation (invert :max-level another-non-invertible-operation))))))
