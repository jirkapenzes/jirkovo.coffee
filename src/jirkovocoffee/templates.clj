(ns jirkovocoffee.templates
  (:require [net.cgrand.enlive-html :refer :all]))

(defsnippet header "template/header.html"
            [:body :header] [replacements]
            [any-node] (replace-vars replacements))

(defsnippet footer "template/footer.html"
            [:body :footer] [replacements]
            [any-node] (replace-vars replacements))

(deftemplate main-template "template/main.html"
             [env nested]
             [:head :title] (content (:title env))
             [:body] (do-> (append (header env))
                           (append nested)
                           (append (footer env))))

(defsnippet post "template/post.html"
            [:body :div.post] [post]
            [any-node] (replace-vars post))