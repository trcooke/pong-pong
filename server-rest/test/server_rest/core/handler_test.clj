(ns server-rest.core.handler-test
  (:use midje.sweet)
  (:require [ring.mock.request :as mock]
            [server-rest.core.handler :as handler]
            [server-rest.core.handler :as handler]
            [cheshire.core :as cheshire]))

(defn to-json [data]
  (cheshire/generate-string data))

(fact "json-response returns map containing 
      :status value
      :body with some-data as json string
      :header as map with Content-Type=application/json, Access-Control-Allow-Origin=*"
  (let [a-status 201
        some-data {:name "blah"}]
    (:status (handler/json-response a-status some-data)) => 201
    (:headers (handler/json-response a-status some-data)) => {"Content-Type" "application/json", "Access-Control-Allow-Origin", "*"}
    (:body (handler/json-response a-status some-data)) => (to-json some-data)))

(fact "List all leagues, then return empty list"
  (let [response (handler/app (mock/request :get "/league"))]
    (:status response) => 200
    (:body response) => (to-json [{:name "default"}])))

(fact "Build a league, given a name and description"
  (let [response (handler/app (mock/request :put "/league" (to-json {:name "pongpong" :description "Only Tim Knows"})))]
    (:status response) => 201
    (:body response) => (to-json {:name "pongpong" :description "Only Tim Knows" :players {}})))

(fact "Get a league, given a name"
      (let [response (handler/app (mock/request :get "/league/pongpong"))]
        (:status response) => 201
        (:body response) => (to-json {:name "pongpong" :description "Only Tim Knows" :players {}})))
