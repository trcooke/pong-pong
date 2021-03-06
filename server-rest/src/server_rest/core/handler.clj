(ns server-rest.core.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [cheshire.core :as cheshire]))

(def leagues (ref {:default {:name :default}}))

(defn json-response [status data]
  {:status (or status 200)
   :headers {"Content-Type" "application/json", "Access-Control-Allow-Origin" "*"}
   :body (cheshire/generate-string data)})

(defn persist-league [data]
  (let [add-league (fn [x] (assoc x (:name data) data))]
    (dosync
       (alter leagues add-league))
    )
    data
  )

(defn persist-player [player league]
  (let [add-player (fn [x] (let [my-league (get x league) my-players (get my-league :players)]
                             (assoc x league (assoc my-league :players (assoc my-players (player :name) player)))  ))]
    (dosync
      (alter leagues add-player)
      )
    )
    player
  )


(defn extract-league [req]
  (let [body (cheshire/parse-string (slurp(:body req)) true)]
    {:name (:name body)
     :description (:description body)
     :players {}
     }
    )
  )

(defn extract-player [req]
  (let [body (cheshire/parse-string (slurp(:body req)) true)]
    {:name (:name body)
     :score (:score body)
     }
    )
  )

(defn create-league [req]
  (-> req
      (extract-league)
      (persist-league)
      )
  )

(defn create-player [name request]
  (-> request
      (extract-player)
      (persist-player name)
      )
  )

(defroutes app-routes
  (GET "/league" [] (json-response 200 (vals @leagues) ))
  (PUT "/league" request (json-response 201 (create-league request)))
  (GET "/league/:name" [name] (json-response 201 (get @leagues name)))
  (context "/league/:name" [name]
       (PUT "/player" request (json-response 201 (create-player name request))))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
