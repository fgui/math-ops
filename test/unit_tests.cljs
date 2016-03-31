(ns math-ops.unit-tests
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [math-ops.operations-test]
    [math-ops.operations-guessing-test]))

(doo-tests
  'math-ops.operations-test
  'math-ops.operations-guessing-test)