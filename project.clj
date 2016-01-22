(defproject com.greenyouse/blog "0.1.0-SNAPSHOT"
  :description "My blog"
  :url "https://github.com/greenyouse/gblog"
  :license "GPL Version 2"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "0.0-2850"]
                 [org.clojure/core.logic "0.8.8"]
                 [clj-time "0.11.0"]
                 [com.greenyouse/chenex "0.1.0"]
                 [reagent "0.5.0"]
                 [rum "0.2.6"]]

  :plugins [[lein-cljsbuild "1.0.3"]
            [com.greenyouse/chenex "0.1.0"]
            [lein-auto "0.1.1"]]

  :source-paths ["src"]

  :profiles {:default [:base :system :user :provided :dev :plugin.chenex/default]
             :dev {:dependencies [[weasel "0.6.0"]]}}

  :chenex {:builds [{:source-paths ["src"],
                     :output-path "target/intermediate/firefoxos",
                     :rules {:filetype "cljs",
                             :features #{:m :firefoxos},
                             :inner-transforms [],
                             :outer-transforms []}}]}

  ;; include one build for each blog page with code to output
  ;; drop a #+HTML: <script> into the target org file
  :cljsbuild {
              :builds [{:id "dapps-dev"
                        :source-paths ["dev" "target/intermediate"]
                        :compiler {:output-to "resources/public/js/dapps/dapps.js"
                                   :output-dir "resources/public/js/dapps/out"
                                   :source-map true
                                   :optimizations :none
                                   :cache-analysis true
                                   :asset-path "js/dapps/out"
                                   :main dapps.core}}
                       {:id "dapps"
                        :source-paths ["target/intermediate"]
                        :compiler {:output-to "resources/public/js/dapps/dapps.js"
                                   :output-dir "resources/public/js/dapps/dapps"
                                   :source-map "resources/public/js/dapps/dapps.js.map"
                                   :optimizations :advanced
                                   :main dapps.core}}]})
