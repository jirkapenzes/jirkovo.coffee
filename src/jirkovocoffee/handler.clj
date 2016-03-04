(ns jirkovocoffee.handler
  (:gen-class :main true)
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clj-rss.core :as rss]
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

(defn load-gallery-page []
  (->> (view/gallery-page (list))
       (layout/render config)))

(defn rss []
  (rss/channel-xml {:title (:title config) :link (:url config) :description (:subtitle config)}
                   (map #(hash-map :title (:title %)
                                   :link (:absolute-url %)
                                   :description (:body %)) (post/find-all))))

(defroutes app-routes
           (GET "/" [] (load-default-page))
           (GET "/gallery" [] (load-gallery-page))
           (GET "/post/:post-name" [post-name] (load-post-page post-name))
           (GET "/rss" [] (rss))
           (route/resources "/" {:root "public"})
           (route/resources "/images" {:root "posts/images"})
           (route/resources "/post/images" {:root "posts/images"})
           (route/not-found " Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8082"))]
    (run-server app {:port port})
    (println (str "Listening on port " port))))
