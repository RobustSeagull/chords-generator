(ns scales.seesaw (:require [scales.computing :refer :all]
                            [clojure.string :refer :all]))

(use 'seesaw.core)
(use 'seesaw.font)

(native!)

(def main-frame
  "Definition of main window"
  (frame :title "Chords generator"
         :size [1800 :by 740]
         :on-close :exit
         :resizable? false))

(defn call-frame []
  "Call the frame"
  (show! main-frame))

(def chords-text
  "Dipslay chords from input scale"
  (text :text "Choose each degree of the scale and see the chords!" :halign :center :editable? false))

(def chords-display
  "Display chords from input scales in differents text boxes"
  (doall (map (fn [x] (text :text "" :halign :center :editable? false)) (range 0 7))))

(def roman-num-degrees '("I" "II" "III" "IV" "V" "VI" "VII"))

(defn roman-display []
  "Romans degrees screens"
  (doall (map (fn [rom-text] (text :text rom-text :halign :center :editable? false :background :grey)) roman-num-degrees)))

(def degrees
  "Main map"
  {:second {:minor "Minor second" :major "Major second"
              :third {:minor "Minor third" :major "Major third"}
              :fourth {:diminished "Diminished fourth" :perfect "Perfect fourth" :augmented "Augmented fourth"}
              :fifth {:diminished "Diminished fifth" :perfect "Perfect fifth" :augmented "Augmented fifth"}
              :sixth {:minor "Minor sixth" :major "Major sixth"}
              :seventh {:diminished "Diminished seventh" :minor "Minor seventh" :major "Major seventh"}}})

(def degrees-list
  '(["Minor second (♭2)", "Major second (2)"]
    ["Minor third (3m)", "Major third (3)"]
    ["Diminished fourth (♭4)", "Perfect fourth (4)", "Augmented fourth (♯4)"]
    ["Diminished fifth (♭5)", "Perfect fifth (5)", "Augmented fifth (♯5)"]
    ["Minor sixth (♭6)", "Major sixth (6)"]
    ["Diminished seventh (𝄫7)", "Minor seventh (♭7)", "Major seventh (7)"]))

(def major-degrees '("Major second (2)", "Major third (3)", "Perfect fourth (4)", "Perfect fifth (5)", "Major sixth (6)", "Major seventh (7)"))

(def degrees-intervals
  "Contains the intervals respect to the tonic in the same order as for degrees-list"
  '(1/2, 1, 3/2, 2, 2, 5/2, 3, 3, 7/2, 4, 4, 9/2, 9/2, 5, 11/2))

(def degrees-intervals-map
  "Defines the map used to change between degrees and intervals"
  (let [degrees-list (flatten degrees-list)] (zipmap degrees-list degrees-intervals)))

(defn make-listbox [map-degrees rank]
  "Building function fo comboboxes degrees"
  (combobox :model (nth map-degrees rank) :border [10 40]))

(def root-combobox
  "Comboboxes for the root note"
  (combobox :model chromatic-scale :border [10 "Root note" 40]))

(def notes-listboxes
  "Comboboxes for the scale degrees"
  (map #(make-listbox degrees-list %) (range 0 6)))

; initialize the comboboxes to the standards degrees values
; evaluation
(doall (map #(selection! %1 %2) notes-listboxes major-degrees))
; set up value
(selection root-combobox (first chromatic-scale))

(defn degrees-to-intervals [notes]
  "Convert map degrees into a computable list of intervals"
  (let [root-relative-intervals (concat '(0) (clojure.core/replace degrees-intervals-map notes) '(6))]
   (map #(reduce - %) (map clojure.core/reverse (partition 2 1 root-relative-intervals)))))

(defn update-chords-display [degree-list]
  "Update the value of the chord display screen"
  (doall (map #(config! %1 :text %2) chords-display degree-list)))

(defn- listen-&-update []
  "Listent to both root value and degrees values and update the chords display widget. Common to both root widget and degrees widget"
  (let ; let bloc for listening to the root note combobox
       [permutted-scale      (-> (get-note-value (selection root-combobox))
                                 (rotate chromatic-scale))
        current-rr-intervals (-> (map selection notes-listboxes)
                                 (degrees-to-intervals)
                                 (relative-to-root-intervals))
        notes-string         (notes-in-the-scale current-rr-intervals permutted-scale)
       ; let bloc for listening to the scale degrees comboboxes
        chords-quality       (-> (map selection notes-listboxes)
                                 (degrees-to-intervals)
                                 (generate-chords))
        complete-chords      (map #(clojure.core/str %1 %2) notes-string chords-quality)]
   ; update chords display widget
    (update-chords-display complete-chords)))

; when intervals are changed, recompute the sequence of chords
(listen notes-listboxes :selection
  (fn [e] (listen-&-update)))

;when root is changed, recompute the sequence of chords
(listen root-combobox :selection
  (fn [e] (listen-&-update)))

(def roman-grid
  "Room attribution for roman grid"
  (grid-panel :columns 7
              :rows 1
              :items (roman-display)))

(def chords-grid
  "Room attribution for chords grid"
  (grid-panel :columns 7
              :rows 1
              :items chords-display))

(def display-grid
  "Room attribution for chords text boxes and roman numerals boxes"
  (top-bottom-split chords-grid roman-grid :divider-location 5/6))

(def notes-grid
  "Room attribution for degrees comboboxes"
  (grid-panel :columns 3
              :rows 2
              :items notes-listboxes))

(def upper-grid
  "Room atribution for root combobox and chords display"
  (left-right-split root-combobox display-grid :divider-location 1/5))

(def main-grid
  "Bundle 'notes-grid' and 'upper-grid'"
  (top-bottom-split upper-grid notes-grid :divider-location 1/3))
