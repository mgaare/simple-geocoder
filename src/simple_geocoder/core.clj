(ns simple-geocoder.core
  (:require [simple-geocoder.ruby :as ruby]
            [simple-geocoder.mapquest :as mapquest]
            [simple-geocoder.yahoo :as yahoo]))

(def geocode-fns
  {:ruby ruby/geocode
   :mapquest mapquest/geocode
   :yahoo yahoo/geocode})

(defn geocode [address sources]
  "Attempts to geocode an address, falling back to each of the sources in order
  if it fails"
  (let [s (seq sources)]
    (when-let [source (first s)]
      (let [result ((geocoder-fns (source :type)) address source)]
        (if result
          result
          (geocode address (rest sources)))))))