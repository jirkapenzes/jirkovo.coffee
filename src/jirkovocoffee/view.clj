(ns jirkovocoffee.view
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [clojure.string :as str]
            [clj-time.format :refer [formatter unparse with-locale formatters]])
  (:import (java.util Locale)))

(def short-date-format (with-locale (formatter "d. MMMM yyyy") (Locale. "cs")))
(defn- short-date [date] (unparse short-date-format date))
(defn- link [href name] [:a {:href href} name])


(defn tweet-button [post]
  [:a {:class         "twitter-share-button"
       :data-url      (:absolute-url post)
       :data-hashtags "jirkovocoffee"
       :data-text     (:title post)
       :href          (:absolute-url post)} "Tweet"])

(defn facebook-button [post]
  [:a {:class           "fb-like"
       :data-href       (:absolute-url post)
       :data-layout     "button_count"
       :data-action     "like"
       :data-show-faces "true"
       :data-share      "true"}])

(defn disqus [post]
  [:div {:id "disqus_thread"}])

(defn- social-networks [post]
  [:div {:class "social-share"}
   (tweet-button post)
   (facebook-button post)])

(defn render-post [post & is-active]
  [:div {:class "post"}
   (if is-active
     [:h2 (:title post)]
     [:h2 (link (:relative-url post) (:title post))])
   [:div {:class "info"}
    (str (short-date (:publish-date post)) " / "
         (str/join " " (map #(str "#" %) (:tags post))))]
   [:div
    (:body post)]])

(defn short-post [post]
  [:div
   (render-post post)
   (link (:relative-url post) "Pokračovat ve čtení článku")
   (social-networks post)])

(defn full-post [post]
  [:section
   (render-post post false)
   (social-networks post)
   (disqus post)])


(defn home-page [posts]
  (let [[top-post & recent-posts] posts]
    [:section
     (short-post top-post)
     [:hr]
     [:div {:class "posts-list"}
      [:h2 "Starší články"]
      [:ul
       (map #(vector
              :li [:a {:href (:relative-url %)} (:title %)])
            recent-posts)]]]))

(defn post-page [post]
  (full-post post))

(defn gallery-page [images]
  [:div
   [:h2
    [:a {:href "https://www.instagram.com/explore/tags/jirkovocoffee/"} "#jirkovocoffee"] " na instagramu"]
   [:div {:id "instafeed"}]])