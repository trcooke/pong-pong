(ns server-rest.core.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [cheshire.core :as cheshire]))

(defn json-response [status data]
  {:status (or status 200)
   :headers {"Content-Type" "application/json" "Access-Control-Allow-Origin" "*"}
   :body (cheshire/generate-string data)})


(defn create-league [req]
  (let [body (cheshire/parse-string (slurp(:body req)) true)]
    {:name (:name body) :description (:description body)}))

(defroutes app-routes
  (GET "/league" [] "[]")
  (PUT "/league" request (json-response 201 (create-league request)))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
