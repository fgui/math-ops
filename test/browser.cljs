(ns math-ops.browser
  (:require [doo.runner :refer-macros [doo-tests]]
            [math-ops.operations-test]))

(doo-tests
  'math-ops.operations-test)