(ns jirkovocoffee.layout
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [clj-time.format :as time]))

(defn- head [env]
  [:head
   [:title (:page-title env)]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   (include-css "https://fonts.googleapis.com/css?family=Amatic+SC"
                "https://fonts.googleapis.com/css?family=Quando"
                "https://fonts.googleapis.com/css?family=Libre+Baskerville:400,700"
                "https://fonts.googleapis.com/css?family=PT+Sans"
                "../bower_components/pure/pure-min.css"
                "../bower_components/pure/grids-responsive-min.css"
                "../site.css")

   (include-js "../js/twitter-share-button.js"
               "../js/facebook-share-button.js"
               "../js/disqus.js")])

(defn- render-header [env]
  [:div {:id "header"}
   [:a {:href "/"}
    [:h1 (:title env)]]
   [:p (:subtitle env)]
   [:div {:class "header-line"}]])

(defn- render-footer [env]
  [:div {:id "footer"}
   [:a {:href (:url env)} (:title env)]
   "&nbsp;&nbsp;|&nbsp;&nbsp;"
   [:a {:href "/archiv"} "Archív"]
   "&nbsp;&nbsp;|&nbsp;&nbsp;"
   [:a {:href "/rss"} "RSS"]
   "&nbsp;&nbsp;|&nbsp;&nbsp;"
   "(c) 2016 " [:a {:href (:author-web env)} (:author env)]])

(defn- body [env & content]
  [:body
   [:div {:class "content-wrapper"}
    (render-header env)
    [:div {:id "fb-root"}]
    content
    (render-footer env)]])

(defn render [env & content]
  (html5 {:lang "en"}
         (head env)
         (body env content)))