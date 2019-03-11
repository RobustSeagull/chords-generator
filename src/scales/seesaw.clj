(ns scales.seesaw)

(use 'seesaw.core)
(use 'seesaw.font)

(native!)

(def main-frame (frame :title "Chords generator"
                       :size [1800 :by 740]
                       :on-close :exit
                       :resizable? false))

(defn call-frame [frame] (-> frame show!))

(def chords-text (text :text "Choose each degree of the scale and see the chords!" :halign :center :editable? false))

(comment (def degrees {:second {:minor "Minor second" :major "Major second"}}
              :third {:minor "Minor third" :major "Major third"}
              :fourth {:diminished "Diminished fourth" :perfect "Perfect fourth" :augmented "Augmented fourth"}
              :fifth {:diminished "Diminished fifth" :perfect "Perfect fifth" :augmented "Augmented fifth"}
              :sixth {:minor "Minor sixth" :major "Major sixth"}
              :seventh {:diminished "Diminished seventh" :minor "Minor seventh" :major "Major seventh"}))

(def degrees-list '(["Minor second (♭2)", "Major second (2)"]
                    ["Minor third (3m)", "Major third (3)"]
                    ["Diminished fourth (♭4)", "Perfect fourth (4)", "Augmented fourth (♯4)"]
                    ["Diminished fifth (♭5)", "Perfect fifth (5)", "Augmented fifth (♯5)"]
                    ["Minor sixth (♭6)", "Major sixth (6)"]
                    ["Diminished seventh (𝄫7)", "Minor seventh (♭7)", "Major seventh (7)"]))

(def degrees-intervals "contains the intervals respect to the tonic in the same order as for degrees-list"
  '(1/2, 1, 3/2, 2, 2, 5/2, 3, 3, 7/2, 4, 4, 9/2, 9/2, 5, 11/2))

(def degrees-intervals-map
  (let [degrees-list (flatten degrees-list)] (zipmap degrees-list degrees-intervals)))

(defn make-listbox [map-degrees rank]
    (combobox :model (nth map-degrees rank)))

(def notes-listboxes (map #(make-listbox degrees-list %) (range 0 6)))

(map scrollable notes-listboxes)

(def notes-grid (grid-panel :columns 3
                            :rows 2
                            :items notes-listboxes))

(def main-grid (top-bottom-split chords-text notes-grid :divider-location 1/3))
