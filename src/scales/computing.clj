(ns scales.computing)

(def flat (char 9837))
(def sharp (char 9839))

; user function to rotate the scale
(defn- rotate [rank coll]
  (let [length (count coll)]
   (take length (drop (+ (mod rank length) length) (cycle coll)))))

(def major-scale '(1 1 1/2 1 1 1 1/2))

(def major-modes-names '(:ionian :dorian :phrygian :lydian :mixolydian :aeolian :locrian))

(def major-modes-values
  (map #(rotate % major-scale) (range 0 7)))

(def major-modes
  (zipmap major-modes-names major-modes-values))

;isolate the third fifth and seventh notes of the current position of the scale
(defn chord-notes [scale]
  (map #(reduce + (take (dec %) scale)) '(3 5 7)))

;yields the quality of the third
(defn third [chord]
  (if (= 2 (first chord) ) "" "m"))

;yields the quality of the fifth
(defn fifth [chord]
  (let [fifth-interval (nth chord 1)]
   (if (= 7/2 fifth-interval) ""
       (if (= 3 fifth-interval) (str flat "5") (str sharp "5")))))

;yields the quality of the seventh
(defn seventh [chord]
  (let [seventh-interval (nth chord 2)]
   (if (= 5 seventh-interval) "7"
       (if (= 11/2 seventh-interval) "M7"))))

;builds the chord quality
(defn alterations [chord]
  (str (third chord) (fifth chord) (seventh chord)))

;generate the chords progression associated to the scale, based on the intervals relatively to the root
(defn generate-chords [scale]
    (map alterations (map chord-notes (map #(rotate % scale) (range 0 7)))))
