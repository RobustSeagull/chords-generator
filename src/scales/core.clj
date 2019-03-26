(ns scales.core
  (:require [scales.computing :refer :all]
            [scales.seesaw :refer :all]) (:gen-class))

(use 'seesaw.core)

(defn- display [frame content]
  "Adds content to frame"
  (config! frame :content content))

(defn -main [& args]
  (display main-frame main-grid)
  (call-frame))
