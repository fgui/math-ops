(ns math-ops.history-test
  (:require
    [cljs.test :refer-macros [deftest is testing]]
    [math-ops.history :as history]))

(deftest recording-guessings
  (testing "recording a guessing"
    (testing "in case of failure"
      (let [current-guessing {:operation :not-used-in-test
                              :number-input "10"
                              :level :max-level
                              :init-time 10}
            next-guessing {:operation :not-used-in-test
                           :number-input "?"
                           :level :max-level
                           :init-time 10}]
        (is (= [current-guessing]
               (history/record
                 current-guessing
                 next-guessing
                 [])))))

    (testing "in case of success"
      (let [current-guessing {:operation :not-used-in-test
                              :number-input "40"
                              :level :max-level
                              :init-time 10}
            next-guessing {:operation :not-used-in-test
                           :number-input "?"
                           :level :max-level
                           :init-time 11}]
        (is (= [current-guessing]
               (history/record
                 current-guessing
                 next-guessing
                 []))))))

  (testing "not recording a guessing"
    (testing "in any other case case of success"
      (let [initial-history []
            current-guessing {:operation :not-used-in-test
                              :number-input "40"
                              :level :max-level
                              :init-time 10}
            next-guessing {:operation :not-used-in-test
                           :number-input "41"
                           :level :max-level
                           :init-time 10}]
        (is (= initial-history
               (history/record
                 current-guessing
                 next-guessing
                 initial-history)))))))
