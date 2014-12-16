(ns server-rest.core.persistence
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn get-database  []
  (mg/get-db (mg/connect (read-string "connection.properties")) "pong-pong-db")
  )

(defn save-league [league]
  (let [database (get-database)]
    (mc/insert-and-return database "leagues" league))
  )
