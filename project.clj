(defproject untangled-template "0.1.0-SNAPSHOT"
  :description "A clonable & upstream untangled template"
  :license {:name "MIT" :url "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.671"]

                 [awkay/untangled "1.0.0-beta1"]
                 [untangled/om-css "1.0.0"]
                 [org.omcljs/om "1.0.0-beta1"]

                 [awkay/untangled-spec "1.0.0-beta1" :scope "test"]
                 [lein-doo "0.1.7" :scope "test"]
                 [org.clojure/core.async "0.3.443"]
                 [http-kit "2.2.0"]
                 [com.taoensso/timbre "4.10.0"]]

  :plugins [[lein-cljsbuild "1.1.6"]
            [lein-doo "0.1.7"]
            [com.jakemccrary/lein-test-refresh "0.17.0"]]

  :doo {:build "automated-tests"
        :paths {:karma "node_modules/karma/bin/karma"}}

  :uberjar-name "untangled_template.jar"

  :test-refresh {:report       untangled-spec.reporters.terminal/untangled-report
                 :with-repl    true
                 :changes-only true}

  :source-paths ["src/server"]
  :test-paths ["specs" "specs/server" "specs/config"]
  :clean-targets ^{:protect false} ["target" "resources/public/js" "resources/private"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :cljsbuild {:builds [{:id           "production"
                        :source-paths ["src/client"]
                        :jar          true
                        :compiler     {:asset-path    "js/prod"
                                       :main          untangled-template.main
                                       :optimizations :simple
                                       :output-dir    "resources/public/js/prod"
                                       :output-to     "resources/public/js/untangled_template.min.js"}}
                       {:id           "dev"
                        :figwheel     true
                        :source-paths ["src/client" "dev/client"]
                        :compiler     {:asset-path           "js/dev"
                                       :external-config
                                                             {:devtools/config
                                                              ;;github.com/binaryage/cljs-devtools/blob/master/docs/configuration.md
                                                              {:print-config-overrides true}}
                                       :main                 cljs.user
                                       :optimizations        :none
                                       :output-dir           "resources/public/js/dev"
                                       :output-to            "resources/public/js/untangled_template.js"
                                       :preloads             [devtools.preload]
                                       :source-map-timestamp true}}
                       {:id           "test"
                        :source-paths ["specs/client" "src/client"]
                        :figwheel     true
                        :compiler     {:asset-path    "js/specs"
                                       :main          untangled-template.spec-main
                                       :optimizations :none
                                       :output-dir    "resources/public/js/specs"
                                       :output-to     "resources/public/js/specs.js"
                                       :preloads      [devtools.preload]}}
                       {:id           "automated-tests"
                        :source-paths ["specs/client" "src/client"]
                        :compiler     {:asset-path    "js/ci"
                                       :main          untangled-template.all-tests
                                       :optimizations :none
                                       :output-dir    "resources/private/js/ci"
                                       :output-to     "resources/private/js/unit-tests.js"}}
                       {:id           "cards"
                        :figwheel     {:devcards true}
                        :source-paths ["src/client" "src/cards"]
                        :compiler     {:asset-path           "js/cards"
                                       :main                 untangled-template.cards
                                       :optimizations        :none
                                       :output-dir           "resources/public/js/cards"
                                       :output-to            "resources/public/js/cards.js"
                                       :preloads             [devtools.preload]
                                       :source-map-timestamp true}}]}

  :profiles {:uberjar {:main       untangled-template.core
                       :aot        :all
                       :prep-tasks ["compile"
                                    ["cljsbuild" "once" "production"]]}
             :dev     {:source-paths ["dev/client" "dev/server" "src/client" "src/server"]
                       :dependencies [[binaryage/devtools "0.9.2"]
                                      [org.clojure/tools.namespace "0.3.0-alpha3"]
                                      [com.cemerick/piggieback "0.2.1"]
                                      [figwheel-sidecar "0.5.10" :exclusions [org.clojure/tools.reader]]
                                      [devcards "0.2.3"]]
                       :repl-options {:init-ns          user
                                      :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
