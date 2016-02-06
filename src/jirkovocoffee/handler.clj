(ns jirkovocoffee.handler
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [jirkovocoffee.layout :as layout]
            [jirkovocoffee.view :as view]
            [jirkovocoffee.post :as post]))

(def config {:title      "jirkovocoffee.cz"
             :subtitle   "káva, kavárny a život v nich."
             :page-title "jirkovo coffee | Káva, kavárny a život v nich."
             :url        "http://www.jirkovocoffee.cz"
             :author     "jirkapenzes"
             :author-web "http://www.twitter.com/jirkapenzes"})

(defn load-post-page [post-name]
  (let [post (post/find-by-name post-name)
        env (update-in config [:page-title]
                       (fn [_] (str (:title config) " | " (:title post))))]
    (->> (view/post-page post)
         (layout/render env))))

(defn load-default-page []
  (->> (post/find-all)
       (view/home-page)
       (layout/render config)))

(defroutes app-routes
           (GET "/" [] (load-default-page))
           (GET "/post/:post-name" [post-name] (load-post-page post-name))
           (route/resources "/" {:root "posts"})
           (route/resources "/" {:root "public"})
           (route/not-found " Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))