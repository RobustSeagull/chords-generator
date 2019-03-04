(ns scales.core-test
  (:require [clojure.test :refer :all]
            [scales.core :refer :all]))

(comment
(deftest a-test
  (testing ""
    (is (= (generate-chords [1 2 5/2 7/2 9/2 11/2]) ["M" "m" "m" "M" "M" "m" "°"] )))) )

(deftest third-major
  (testing ""
    (is (= (third  '(2 7/2 11/2)) ""))) )

(deftest third-minor
  (testing ""
    (is (= (third  '(3/2 7/2 5)) "m"))) )

(deftest perfect-fifth
  (testing ""
    (is (= (fifth '(2 7/2 11/2)) "" ))) )

(deftest flat-fifth
  (testing ""
    (is (= (fifth '(2 3 11/2)) (str flat "5")))) )

(deftest sharp-fifth
  (testing ""
    (is (= (fifth '(2 4 11/2)) (str sharp "5" )))) )

(deftest major-seventh
  (testing ""
    (is (= (seventh '(2 7/2 11/2)) "M7"))) )

(deftest minor-seventh
  (testing ""
    (is (= (seventh '(3/2 7/2 5)) "7" ))) )

(deftest alterate-major7
  (testing ""
    (is (= (alterations '(2 7/2 11/2)) "M7"))) )

(deftest alterate-diminished5-major7
  (testing ""
    (is (= (alterations '(2 3 11/2)) (str flat "5" "M7")))) )
