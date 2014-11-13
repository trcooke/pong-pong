(ns server-rest.core.handler-test
  (:use midje.sweet)
  (:require [ring.mock.request :as mock]
            [server-rest.core.handler :as handler]))

(fact "List all leagues, then return empty list"
  (let [response (handler/app (mock/request :get "/league"))]
    (:status response) => 200
    (:body response) => "[]"))

(fact "Build a league, given a name and description"
  (let [response (handler/app (mock/request :put "/league" "{\"name\": \"pongpong\",\"description\":\"Only Tim Knows\"}"))]
    (:status response) => 201
    (:body response) => "{\"name\":\"pongpong\",\"description\":\"Only Tim Knows\"}"))