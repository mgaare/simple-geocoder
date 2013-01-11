(ns simple-geocoder.config)

<<<<<<< Updated upstream
(def initial-order [:ruby :mapquest :yahoo])
=======
(comment
  "The config has two parts - the config for the geocoding providers, and a
vector for the order that they should be tried. The vector uses the keys
of the providers as the references.")3
>>>>>>> Stashed changes

(def initial-sources
  {:ruby  {:type :ruby
           :host "localhost"
           :port "5000"}
   :mapquest-open {:type :mapquest-open}
   :yahoo-free {:type :yahoo-free}})

(def config
  (atom  {:order initial-order
          :sources initial-sources}))

(defn set-order "changes the order that geocoders are used - takes a vector of
 geocoder source keys and sets the config to use them in that order"
  [new-order]
  (swap! config #(assoc %1 :order %2) new-order))

(defn set-source "sets up a geocoder source" [source conf]
  (swap! config #(assoc-in %1 [:sources source] %2) conf))