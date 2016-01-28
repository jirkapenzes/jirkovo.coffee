(ns jirkovocoffee.parser
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- split-lines
  [lines delim]
  (let [x (take-while #(not= delim %) lines)]
    (list x (drop (+ 1 (count x)) lines))))

(defn- parse-metadata [frontmatter]
  (into {}
        (map #(let [[_ k v] (re-find #"(\S+):([\S\s]+)" %)]
               {(keyword k) (str/trim v)}) frontmatter)))

(defn parse [content]
  (let [[first-line & rest-lines] (str/split-lines content)
        [frontmatter body] (split-lines rest-lines first-line)]
    (into {:body (str/join "\n" body)}
          (parse-metadata frontmatter))))