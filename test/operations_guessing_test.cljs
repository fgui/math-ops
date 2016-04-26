(ns math-ops.operations-guessing-test
  (:require
   [cljs.test :refer-macros [deftest is testing]]
   [math-ops.operations :as operations]
   [math-ops.operations-guessing :refer [start process-input]]))

(def new-operation (operations/->Operation 15 + 3 :?))

(deftest operation-guessing

  (with-redefs
    [operations/make (constantly new-operation)]

    (testing "start"
      (testing "operation guessing at start"
        (let [new-state (start :max-level)]
          (is (= (:operation new-state) new-operation))
          (is (= (:number-input new-state) "?")))))

    (testing "processing user input"
      (testing "when not a number is pressed the state remains unaltered"
        (let [arbitrary-state {:operation :not-used-in-this-test :number-input "?"}
              key-code-for-some-arbitrary-not-number 103
              new-state (process-input arbitrary-state key-code-for-some-arbitrary-not-number)]
          (is (= new-state arbitrary-state))))

      (testing "when a number is pressed the number gets added to the number input"
        (let [key-code-for-3 51]
          (let [new-state (process-input {:operation :not-used-in-this-test :number-input "?"} key-code-for-3)]
            (is (= (:number-input new-state) "3")))
          (let [new-state (process-input {:operation :not-used-in-this-test :number-input "56"} key-code-for-3)]
            (is (= (:number-input new-state) "563")))))

      (testing "when enter is pressed and the guess is wrong the guessing restarts keeping the same operation"
        (let [key-code-for-enter 13
              arbitrary-operation (operations/->Operation 14 - :? 9)
              wrong-guess "8"
              new-state (process-input {:operation arbitrary-operation :number-input wrong-guess} key-code-for-enter)]
          (is (= (:operation new-state) arbitrary-operation))
          (is (= (:number-input new-state) "?"))))

      (testing "when enter is pressed and the guess is right the guessing restarts with a new operation"
        (let [key-code-for-enter 13
              arbitrary-operation (operations/->Operation 8 + :? 9)
              right-guess "1"
              new-state (process-input {:operation arbitrary-operation :number-input right-guess} key-code-for-enter)]
          (is (= (:operation new-state) new-operation))
          (is (= (:number-input new-state) "?")))))))
