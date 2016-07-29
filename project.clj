(defproject math-ops "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.5.1"]
                 [re-frame "0.6.0"]
                 [hodgepodge "0.1.3"]
                 [com.cemerick/piggieback "0.2.1"]
                 [figwheel-sidecar "0.5.0-4"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"
                 "script"
                 ]

  :plugins [[lein-cljsbuild "1.1.1"]
            ;;[lein-figwheel "0.5.0-2"]
            [lein-doo "0.1.6"]
            ]

  :profiles {
             :deployment {:clean-targets ^{:protect false} ["resources/deploy"]}
             :dev {:clean-targets ^{:protect false} ["resources/public/js" "target" "out"]}}

  :figwheel {:css-dirs ["resources/public/css"]
             :open-file-command "open-in-editor"
             }

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "math-ops.core/mount-root"}
                        :compiler {:main math-ops.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main math-ops.core
                                   :output-to "resources/deploy/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false}}

                       {:id "unit-tests"
                        :source-paths ["src/cljs" "test"]
                        :compiler {:output-to "out/unit_tests.js"
                                   :main 'math-ops.unit-tests
                                   :target :nodejs
                                   :optimizations :none}}
                       ]
              }

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
