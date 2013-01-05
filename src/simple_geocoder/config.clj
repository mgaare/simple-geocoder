(ns simple-geocoder.config)

(comment
  ok, we want to somehow un-combine the config of geocoders with the order
  in which they are tried... map - list - map? maybe should use an atom)

(defn- initial-order "default order that geocoders are used" []
  [:ruby :mapquest :yahoo])

(defn- initial-sources "default config of geocoders" []
  {:ruby  {:type :ruby
           :host "localhost"
           :port "5000"}
   :mapquest {:type :mapquest
              :api-key "fill-in"}
   :yahoo {:type :yahoo
           :api-key "fill-in"}})

(def config
  (atom  {:order initial-order
          :sources initial-sources}))

(defn set-order "changes the order that geocoders are used - takes a vector of
 geocoder source keys and sets the config to use them in that order"
  [new-order]
  (swap! config #(assoc %1 :order %2) new-order))

(defn set-source "sets up a geocoder source" [source conf]
  (swap! config #(assoc-in %1 [:sources source] %2) conf))