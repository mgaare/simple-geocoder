(ns simple-geocoder
  (:use [simple-geocoder.config])
  (:require [simple-geocoder.ruby :as ruby]
            [simple-geocoder.mapquest-open :as mapquest-open]
            [simple-geocoder.yahoo-free :as yahoo-free]))

(def geocode-fns
  {:ruby ruby/geocode
   :mapquest mapquest-open/geocode
   :yahoo-free yahoo-free/geocode})

(defn geocode [address sources]
  "Attempts to geocode an address, falling back to each of the sources in order
  if it fails"
  (let [s (seq sources)]
    (when-let [source (first s)]
      (let [result ((geocode-fns (source :type)) address source)]
        (if result
          result
          (geocode address (rest sources)))))))