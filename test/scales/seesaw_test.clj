(ns scales.seesaw-test
  (:require [clojure.test :refer :all]
            [scales.seesaw :refer :all]))

(deftest third-major
  (testing ""
    (is (= (degrees-to-intervals major-degrees) '(1 2 5/2 7/2 9/2 11/2)))))
