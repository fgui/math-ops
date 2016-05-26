(ns math-ops.operations-guessing-test
  (:require
    [cljs.test :refer-macros [deftest is testing]]
    [math-ops.operations :as operations]
    [math-ops.operations-guessing :refer [process-input start-new-guessing]]
    [math-ops.clock :as clock]))

(def new-operation (operations/->Operation 15 + 3 :?))

;; extract to a generalized make-stub function or macro
(def times (atom [10 12]))
(defn stubbed-current-time-ms []
  (let [t (first @times)]
    (swap! times rest)
    t))

(deftest operation-guessing
  (with-redefs
    [operations/make (constantly new-operation)
     clock/current-time-ms stubbed-current-time-ms]

    (testing "start"
      (testing "operation guessing at start"
        (let [guessing (start-new-guessing :max-level)]
          (is (= guessing {:operation new-operation
                           :number-input "?"
                           :level :max-level
                           :init-time 10})))))

    (testing "processing user input"
      (testing "when not a number is pressed the state remains unaltered"
        (let [guessing {:number-input "?"
                        :operation :not-used-in-this-test
                        :level :not-used-in-this-test
                        :init-time :not-used-in-this-test}
              current-level :max-level
              key-code-for-some-arbitrary-not-number 103
              new-guessing (process-input
                             guessing
                             current-level
                             key-code-for-some-arbitrary-not-number)]
          (is (= new-guessing guessing))))


      (testing "when a number is pressed the number gets added to the number input"
        (let [key-code-for-3 51
              current-level :max-level]
          (let [new-guessing (process-input
                               {:number-input "?"
                                :operation :not-used-in-this-test
                                :level :not-used-in-this-test
                                :init-time :not-used-in-this-test}
                               current-level
                               key-code-for-3)]
            (is (= (get-in new-guessing [:number-input]) "3")))
          (let [new-guessing (process-input
                               {:number-input "56"
                                :operation :not-used-in-this-test
                                :level :not-used-in-this-test
                                :init-time :not-used-in-this-test}
                               current-level
                               key-code-for-3)]
            (is (= (get-in new-guessing [:number-input]) "563")))))

      (testing "when enter is pressed and the guess is wrong the guessing restarts keeping the same operation"
        (let [key-code-for-enter 13
              arbitrary-operation (operations/->Operation 14 - :? 9)
              wrong-guess "8"
              current-level :max-level
              new-guessing (process-input
                             {:number-input wrong-guess
                              :operation arbitrary-operation
                              :level :not-used-in-this-test
                              :init-time :not-used-in-this-test}
                             current-level
                             key-code-for-enter)]
          (is (= (get-in new-guessing [:operation]) arbitrary-operation))
          (is (get-in new-guessing [:number-input]) "?")))

      (testing "when enter is pressed and the guess is right the guessing restarts with a new operation"
        (let [key-code-for-enter 13
              arbitrary-operation (operations/->Operation 8 + :? 9)
              right-guess "1"
              current-level :max-level
              new-guessing (process-input
                             {:number-input right-guess
                              :operation arbitrary-operation
                              :level :max-level
                              :init-time :not-used-in-this-test}
                             current-level
                             key-code-for-enter)]
          (is (= (get-in new-guessing [:operation]) new-operation))
          (is (= (get-in new-guessing [:number-input]) "?"))
          (is (= (get-in new-guessing [:level]) :max-level)))))))