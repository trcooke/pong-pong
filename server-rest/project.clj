(defproject server-rest "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.novemberain/monger "2.0.0"]
                 [compojure "1.2.0"]
                 [cheshire "5.3.1"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-midje "3.1.3"]]
  :ring {:handler server-rest.core.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]
                        [midje "1.6.3"]]}})
