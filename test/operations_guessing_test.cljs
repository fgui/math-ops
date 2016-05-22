(ns math-ops.operations-guessing-test
  (:require
    [cljs.test :refer-macros [deftest is testing]]
    [math-ops.operations :as operations]
    [math-ops.operations-guessing :refer [process-input-new start-new-guessing-new start-new-guessing process-input]]
    [math-ops.clock :as clock]))

(def new-operation (operations/->Operation 15 + 3 :?))

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
        (let [new-state (start-new-guessing {:current-level :max-level})]
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
          (is (= (:number-input new-state) "?"))))


      ;(testing "when enter is pressed the guessing is recorded in the history"
      ;  (let [key-code-for-enter 13
      ;        arbitrary-operation (operations/->Operation 8 + :? 9)
      ;        guess "1"
      ;        current-state {:operation arbitrary-operation
      ;                       :number-input guess
      ;                       :current-level :max-level}
      ;        new-state (process-input current-state key-code-for-enter)]
      ;    (is (= (:history new-state) [{:current-level :max-level
      ;                                  :operation arbitrary-operation
      ;                                  :number-input guess}])))))

      )

    (testing "start"
      (testing "operation guessing at start"
        (let [guessing (start-new-guessing-new {:current-level :max-level})]
          (is (= guessing {:current-level :max-level
                           :guessing {:operation new-operation
                                      :number-input "?"
                                      :level :max-level
                                      :init-time 10}})))))

    (testing "processing user input"
      (testing "when not a number is pressed the state remains unaltered"
        (let [arbitrary-state {:guessing {:number-input "?"
                                          :operation :not-used-in-this-test
                                          :level :not-used-in-this-test
                                          :init-time :not-used-in-this-test}
                               :history :not-used-in-this-test}
              key-code-for-some-arbitrary-not-number 103
              new-state (process-input-new arbitrary-state key-code-for-some-arbitrary-not-number)]
          (is (= new-state arbitrary-state))))


      (testing "when a number is pressed the number gets added to the number input"
        (let [key-code-for-3 51]
          (let [new-state (process-input-new
                            {:guessing {:number-input "?"
                                        :operation :not-used-in-this-test
                                        :level :not-used-in-this-test
                                        :init-time :not-used-in-this-test}
                             :history :not-used-in-this-test}
                            key-code-for-3)]
            (is (= (get-in new-state [:guessing :number-input]) "3")))
          (let [new-state (process-input-new
                            {:guessing {:number-input "56"
                                        :operation :not-used-in-this-test
                                        :level :not-used-in-this-test
                                        :init-time :not-used-in-this-test}
                             :history :not-used-in-this-test}
                            key-code-for-3)]
            (is (= (get-in new-state [:guessing :number-input]) "563")))))

      (testing "when enter is pressed and the guess is wrong the guessing restarts keeping the same operation"
        (let [key-code-for-enter 13
              arbitrary-operation (operations/->Operation 14 - :? 9)
              wrong-guess "8"
              new-state (process-input-new
                          {:guessing {:number-input wrong-guess
                                      :operation arbitrary-operation
                                      :level :not-used-in-this-test
                                      :init-time :not-used-in-this-test}
                           :history :not-used-in-this-test}
                          key-code-for-enter)]
          (is (= (get-in new-state [:guessing :operation]) arbitrary-operation))
          (is (get-in new-state [:guessing :number-input]) "?")))

      (testing "when enter is pressed and the guess is right the guessing restarts with a new operation"
        (let [key-code-for-enter 13
              arbitrary-operation (operations/->Operation 8 + :? 9)
              right-guess "1"
              new-state (process-input-new
                          {:guessing {:number-input right-guess
                                      :operation arbitrary-operation
                                      :level :max-level
                                      :init-time :not-used-in-this-test}
                           :current-level :max-level
                           :history :not-used-in-this-test}
                          key-code-for-enter)]
          (is (= (get-in new-state [:guessing :operation]) new-operation))
          (is (= (get-in new-state [:guessing :number-input]) "?"))
          (is (= (get-in new-state [:guessing :level]) :max-level))
          (is (= (get-in new-state [:current-level]) :max-level)))))))