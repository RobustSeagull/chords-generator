(ns scales.core-test
  (:require [clojure.test :refer :all]
            [scales.computing :refer :all]))

(deftest third-major
  (testing ""
    (is (= (third  '(2 7/2 11/2)) ""))))

(deftest third-minor
  (testing ""
    (is (= (third  '(3/2 7/2 5)) "m"))))

(deftest perfect-fifth
  (testing ""
    (is (= (fifth '(2 7/2 11/2)) ""))))

(deftest flat-fifth
  (testing ""
    (is (= (fifth '(2 3 11/2)) (str flat "5")))))

(deftest sharp-fifth
  (testing ""
    (is (= (fifth '(2 4 11/2)) (str sharp "5")))))

(deftest major-seventh
  (testing ""
    (is (= (seventh '(2 7/2 11/2)) "M7"))))

(deftest minor-seventh
  (testing ""
    (is (= (seventh '(3/2 7/2 5)) "7"))))

(deftest alterate-major7
  (testing ""
    (is (= (alterations '(2 7/2 11/2)) "M7"))))

(deftest alterate-diminished5-major7
  (testing ""
    (is (= (alterations '(2 3 11/2)) (str flat "5" "M7")))))

(deftest generate-major-scale-chords
  (testing ""
    (is (= (generate-chords [1 1 1/2 1 1 1 1/2]) ["M7" "m7" "m7" "M7" "7" "m7" "m♭57"]))))

(deftest root-relative-and-notes-in-the-scale
  (testing ""
    (is (= (notes-in-the-scale (relative-to-root-intervals major-scale)) '("C" "D" "E" "F" "G" "A" "B")))))
