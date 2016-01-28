(ns jirkovocoffee.post
  (:use markdown.core)
  (:require [clojure.java.io :as io]
            [clj-time.format :as time]
            [clojure.string :as str]
            [jirkovocoffee.parser :as parser]))

(def posts-directory (io/file "resources/posts/"))
(def posts-files (filter #(not (or (.isDirectory %) (= (.getName %) ".DS_Store")))
                         (file-seq posts-directory)))

(defn- find-file [file-name]
  (first (filter #(= (.getName %) file-name) posts-files)))

(defn convert-date [post]
  (update-in post [:publish-date] #(time/parse (time/formatter "dd.MM.yyyy") %)))

(defn add-author [post]
  (update-in post [:author] (fn [_] "Jirka Pénzeš")))

(defn split-tags [post]
  (update-in post [:tags] #(map str/trim (str/split % #","))))

(defn body->html [post]
  (update-in post [:body] #(md-to-html-string %)))

(defn published->bool [post]
  (update-in post [:published] #(if (read-string (str/trim %)) true false)))

(defn load-post [file-name]
  (-> (slurp file-name)
      (parser/parse)
      (convert-date)
      (add-author)
      (split-tags)
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