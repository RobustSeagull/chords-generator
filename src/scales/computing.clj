(ns scales.computing)

;***************************************
;basic definitions for degrees computing
;**************************************

(def flat (char 9837))
(def sharp (char 9839))

(defn rotate [rank coll]
  "User function taking a collection and a rank as inputs. Rotate the collection on it-sefl rank times"
  (let [length (count coll)]
   (take length (drop (+ (mod rank length) length) (cycle coll)))))

(def major-scale
  "The major scale used for many tests and for the construction of major modes"
  '(1 1 1/2 1 1 1 1/2))

(def major-modes-names "Names of major modes"
  '(:ionian :dorian :phrygian :lydian :mixolydian :aeolian :locrian))

(def major-modes-values
  "Relative intervals values of major modes"
  (map #(rotate % major-scale) (range 0 7)))

(def major-modes
  "Map of the major-modes-names and major-modes-values"
  (zipmap major-modes-names major-modes-values))

(defn chord-notes [scale]
  "Yield the 3th, 5th and 7th note of the first permutation of a scale"
  (map #(reduce + (take (dec %) scale)) '(3 5 7)))

(defn third [chord]
  "Yield the quality of the third"
  (let [third-interval (first chord)]
    (if (= 2 third-interval ) "" "m")))

(defn fifth [chord]
  "Yield the quality of the fifth"
  (let [fifth-interval (nth chord 1)]
    (case fifth-interval
          7/2 ""
          3 (str flat "5")
          4 (str sharp "5"))))

(defn seventh [chord]
  (let [seventh-interval (nth chord 2)]
    (case seventh-interval
          9/2 (str "𝄫" "7")
          5 "7"
          11/2 "M7")))

(defn alterations [chord]
  "Compute the qualities of one chord and string the result. Intervals must be root relative"
  (str (third chord) (fifth chord) (seventh chord)))

(defn generate-chords [scale]
  "Combine alterations and rotate to compute all chords of the given scale"
    (map alterations (map chord-notes (map #(rotate % scale) (range 0 7)))))

;*******************************************************
;definitions and functions for root note implementations
;*******************************************************

(def whole-scale
  "Defines the chromatic scale as a string"
  (list "C" (str "C" sharp "/" "D" flat) "D" (str "D" sharp "/" "E" flat) "E" "F" (str "F" sharp "/" "G" flat) "G" (str "G" sharp "/" "A" flat) "A" (str "A" sharp "/" "B" flat) "B"))

(defn relative-to-root-intervals [relative-intervals]
  "Transforms relative intervals into root relative intervals. Ex: '(1 1 1/2 1 1 1 1/2) -> (1 2 5/2 7/2 9/2 11/2 6)"
  (let [adjusted-scale (cons 0 (drop-last relative-intervals))
        scale-list (map #(drop-last % adjusted-scale) (reverse (range 0 7)))]
    (map #(reduce + %) scale-list)))

;used in the root implementation
(defn notes-in-the-scale [root-intervals]
  "Yields the notes in the scale as a function of input intervals"
  (let [halfsteps-position (map #(* 2 %) root-intervals)]
    (map #(nth whole-scale %) halfsteps-position)))

(def whole-map-halfsteps
  "Maps whole-scale with its intervals values"
  (zipmap whole-scale (range 0 12)))

(defn get-note-value [note]
  "Shortcut value for getting the value of the note interval"
  (get whole-map-halfsteps note))
