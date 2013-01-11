(ns simple-geocoder.yahoo-free
  (:require [clj-http.client :as client])
  (:use [cheshire.core]))

;; Right now, we are going to use the free yql interface (2,000
;; queries per day limit, bizarre psuedo sql syntax)

(defn geocode [address source]
  (let [uri "http://query.yahooapis.com/v1/public/yql"
        params {:format "json"
                :q (str "select * from geo.placefinder where text=\"" address "\"")}
        response (client/get uri {:query-params params})
        geo-info (get-in (parse-string (response :body))
                         ["query" "results" "Result"] {})
        quality (Integer/parseInt (get geo-info "quality" "0"))]
    (when (>= quality 85)
      {:latitude (geo-info "latitude") :longitude (geo-info "longitude")})))