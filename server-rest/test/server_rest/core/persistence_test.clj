(ns server-rest.core.persistence-test
  (:use midje.sweet)
  (:require [server-rest.core.persistence :as persistence]))

(fact :mongo "Save tims league"
      (let [result (persistence/save-league {:name "tims" :description "Now another one"})]
        (:name result) => "tims"))

(fact :mongo "Get tims league"
      (persistence/remove-all-leagues)
      (persistence/save-league {:name "tims" :description "Now another one"})
      (let [result (persistence/get-leagues "tims")]
        (:description (first result)) => "Now another one"))

(fact :mongo "Remove tims league"
      (persistence/remove-all-leagues)
      (persistence/save-league {:name "tims" :description "Now another one"})
      (persistence/remove-leagues "tims")
      (let [result (persistence/get-leagues "tims")]
        (empty? result) => true)
      )

