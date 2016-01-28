(ns jirkovocoffee.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [jirkovocoffee.templates :as template]
            [jirkovocoffee.post :as post]))

(def env {:title    "jirkovo.coffee"
          :subtitle "káva, kavárny a život v nich."
          :url      "www.jirkovocoffee.cz"
          :footer   "jirkapenzes (c) 2016"})

(defn index-page []
  (template/main-template env (first (post/find-all))))

(defroutes app-routes
           (GET "/" [] (index-page))
           (route/not-found " Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))