(ns server-rest.core.handler-test
  (:use midje.sweet)
  (:require [ring.mock.request :as mock]
            [server-rest.core.handler :as handler]))

(future-fact "Build a league table")

(fact "test '/' "
  (let [response (handler/app (mock/request :get "/"))]
    (:status response) => 200
    (:body response) => "Hello World"))
