(ns simple-geocoder.mapquest-open
  (:require [clj-http.client :as client])
  (:use [cheshire.core]))

(defn geocode [address source]
  "Geocodes using mapquest open geocoding API. source needs to contain
   API key."
  (let [uri "http://open.mapquestapi.com/nominatim/v1/search.php"
        response (client/get uri {:query-params
                                  {:format "json"
                                   :addressdetails 0
                                   :limit 1
                                   :q address}})
        geo-info (first (parse-string (response :body)))
        type (geo-info "osm_type")]
    (when (= type "node")
      {:latitude (geo-info "lat") :longitude (geo-info "lon")})))