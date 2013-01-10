(ns simple-geocoder.ruby
  (:require [clj-http.client :as client])
  (:use [cheshire.core]))

(defn geocode [address source]
  "Geocodes using the ruby geocoding software, with host information defined
  in source. Successful if the confidence score is more than 0.5 and the
  precision is either 'intersection' or 'range'"
  (let [uri (str "http://" (source :host) ":" (source :port) "/geocode.json")
        response (client/get uri {:query-params {:q address}})
        geo-info (first (parse-string (response :body)))
        score (get geo-info "score" 0)
        precision (geo-info "precision")]
    (when (and (> score 0.5)
               (or (= "intersection" precision) (= "range" precision)))
      {:latitude (geo-info "lat") :longitude (geo-info "lon")})))