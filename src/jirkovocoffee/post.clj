(ns jirkovocoffee.post
  (:use markdown.core)
  (:require [clojure.java.io :as io]
            [clj-time.format :as time]
            [clojure.string :as str]
            [jirkovocoffee.parser :as parser]))

(def posts-directory (io/file "resources/posts/"))
(def posts-files (filter #(not (or (.isDirectory %) (= (.getName %) ".DS_Store" "images")))
                         (.listFiles posts-directory)))

(file-seq posts-directory)
(map #(.getName %) (.listFiles posts-directory))

(defn- find-file [file-name]
  (first (filter #(= (.getName %) file-name) posts-files)))

(defn- convert-date [post]
  (update-in post [:publish-date] #(time/parse (time/formatter "dd.MM.yyyy") %)))

(defn- add-author [post]
  (update-in post [:author] (fn [_] "Jirka Pénzeš")))

(defn- split-tags [post]
  (update-in post [:tags] #(map str/trim (str/split % #","))))

(defn- body->html [post]
  (update-in post [:body] #(md-to-html-string %)))

(defn- published->bool [post]
  (update-in post [:published] #(if (read-string (str/trim %)) true false)))

(defn- add-file-name [file-name post]
  (update-in post [:file-name] (fn [_] file-name)))

(defn- add-relative-url [file-name post]
  (update-in post [:relative-url] (fn [_] (str "post/" (str/replace file-name ".md" "")))))

(defn- add-absolute-url [file-name post]
  (update-in post [:absolute-url] (fn [_] (str "http://jirkovocoffee.cz/post/" (str/replace file-name ".md" "")))))

(defn- load-post [file]
  (->> (slurp file)
       (parser/parse)
       (convert-date)
       (split-tags)
       (add-author)
       (add-file-name (.getName file))
       (add-relative-url (.getName file))
       (add-absolute-url (.getName file))
       (published->bool)
       (body->html)))

(defn find-all []
  (map load-post posts-files))

(defn find-by-tag [tag]
  (->> (find-all)
       (filter #(contains? (:tags %) tag))))

(defn find-by-name [post-name]
  (let [real-name (str post-name ".md")]
    (-> (find-file real-name)
        (load-post))))