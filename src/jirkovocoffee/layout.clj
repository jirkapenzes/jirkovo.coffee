(ns jirkovocoffee.layout
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [clj-time.format :as time]))

(defn- head [env]
  [:head
   [:title (:title env)]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   (include-css "https://fonts.googleapis.com/css?family=Amatic+SC"
                "bower_components/pure/pure-min.css"
                "bower_components/pure/grids-responsive-min.css"
                "site.css")])

(defn- render-header [env]
  [:div {:id "header"}
   [:a {:href "/"}
    [:h1 (:title env)]]
   [:p (:subtitle env)]
   [:div {:class "header-line"}]])

(defn- render-footer [env]
  [:div {:id "footer"}
   [:span (str "by ")
    [:a {:href (:author-web env)}
     (:author env)]
    (str " 2016 (c)")]])

(defn- body [env & content]
  [:body
   [:div {:class "content-wrapper"}
    (render-header env)
    (identity content)
    (render-footer env)]])

(defn render [env & content]
  (html5 {:lang "en"}
         (head env)
         (body env content)))

