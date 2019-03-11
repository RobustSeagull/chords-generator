(ns scales.core
  (:require [scales.computing :refer :all]
            [scales.seesaw :refer :all]) (:gen-class))

(use 'seesaw.core)

;The display function allows to add content to the main-frame!
(defn- display [frame content]
  (config! frame :content content))

(def lb (listbox :model (-> 'seesaw.core ns-publics keys sort)))

(defn -main [& args]
  (display main-frame main-grid)
  (call-frame main-frame))
