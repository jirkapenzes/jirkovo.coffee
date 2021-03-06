(defproject jirkovocoffee "0.1.0-SNAPSHOT"
  :description "jirkovo coffee | Káva, kavárny a život v nich."
  :url "http://jirkovocoffee.cz"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [clj-time "0.11.0"]
                 [environ "1.0.2"]
                 [ring/ring-defaults "0.1.5"]
                 [markdown-clj "0.9.66"]
                 [clj-rss "0.2.3"]
                 [instagram-api "0.2.0"]]
  :main jirkovocoffee.handler
  :plugins [[lein-ring "0.9.7"]
            [lein-environ "1.0.2"]]
  :ring {:handler jirkovocoffee.handler/app}
  :profiles
  {:dev     {:dependencies [[javax.servlet/servlet-api "2.5"]
                            [ring/ring-mock "0.3.0"]]}
   :uberjar {:aot  :all
             :main jirkovocoffee.handler
             :env  {:production true}}})
