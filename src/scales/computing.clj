(ns scales.computing)

(def flat (char 9837))
(def sharp (char 9839))

(defn- rotate [rank coll]
  (let [length (count coll)]
   (take length (drop (+ (mod rank length) length) (cycle coll)))))

(def major-scale '(1 1 1/2 1 1 1 1/2))

(def major-modes-names '(:ionian :dorian :phrygian :lydian :mixolydian :aeolian :locrian))

(def major-modes-values
  (map #(rotate % major-scale) (range 0 7)))

(def major-modes
  (zipmap major-modes-names major-modes-values))

(defn chord-notes [scale]
  (map #(reduce + (take (dec %) scale)) '(3 5 7)))

(defn third [chord]
  (if (= 2 (first chord) ) "" "m"))

(defn fifth [chord]
  (let [fifth-interval (nth chord 1)]
   (if (= 7/2 fifth-interval) ""
       (if (= 3 fifth-interval) (str flat "5") (str sharp "5")))))

(defn seventh [chord]
  (let [seventh-interval (nth chord 2)]
   (if (= 5 seventh-interval) "7"
       (if (= 11/2 seventh-interval) "M7"))))

(defn alterations [chord]
  (str (third chord) (fifth chord) (seventh chord)))

(defn generate-chords [scale]
    (map alterations (map chord-notes (map #(rotate % scale) (range 0 7)))))
