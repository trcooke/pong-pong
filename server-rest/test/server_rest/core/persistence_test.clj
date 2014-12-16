(ns server-rest.core.persistence-test
  (:use midje.sweet)
  (:require [server-rest.core.persistence :as persistence]))

(fact "Save something"
      (let [result (persistence/save-league {"name" "tims" "description" "Now another one"})]
        (get result "name") => "tims"))


