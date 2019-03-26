(defproject chords-generator "0.1.0-SNAPSHOT"
  :description "Simple chords generator for seven notes scales"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [proto-repl "0.3.1"]
                 [seesaw "1.5.0"]]
  :main ^:skip-aot scales.core)
