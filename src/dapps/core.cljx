(ns dapps.core
  (:require [rum :include-macros true]
            [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; example one (bad)

(defn list-c%1 [{:keys [data]}]
  [:section
   (reduce (fn [ul title]
             (conj ul ^{:key (gensym)}
                   [:dt  {:on-click #(println title)}
                    [:p [:strong title]]]))
           [:dl] data)])

;; Let's give it some stuff to render so we can inspect the output
(defn example-list%1 []
  (list-c%1 {:data ["some" "stuff" "to" "render"]}))

(reagent/render-component [example-list%1]
                          (.getElementById js/document "list-example1"))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; example 2 (with feature expressions)

(defn list-c%2
  "A list of the items"
  [{:keys [data]}]
  (chenex/in-case!
   [:firefoxos] [:section {:data-type "list"}
                 (reduce (fn [ul title]
                           (conj ul ^{:key (gensym)}
                                 [:li [:a {:on-click #(println title)}
                                       [:aside {:class "pack-end"
                                                :data-icon "forward"}]
                                       [:p [:strong title]]]]))
                         [:ul] data)]
   [:else] [:section
            (reduce (fn [ul title]
                      (conj ul ^{:key (gensym)}
                            [:dt  {:on-click #(println title)}
                             [:p [:strong title]]]))
                    [:dl] data)]))


(defn example-list%2 []
  (list-c%2 {:data ["some" "stuff" "to" "render"]}))

(reagent/render-component [example-list%2]
                          (.getElementById js/document "list-example2"))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; example three (styling)

;;;; ShadowDOM mixin

(defn attach-css
  "Bind the css sheets to the DOM"
  [css el]
  (let [style-el (.createElement js/document "style")
        sheets (reduce #(str % (str "@import url(\"" %2 "\");\n")) "" css)]
    (set! (.-innerHTML style-el) sheets)
    (.appendChild el style-el)))

;;; FIXME: need to get css/rendering stuff worked out, then do event things
(defn intercept-events
  [])

(defn shadow-dom
  "An implementation of ShadowDOM for component styling.
  Takes a seq of css files and attaches them to the root of a ShadowDOM.
  Inspired by: https://github.com/Wildhoney/ReactShadow"
  [css]
  {:did-mount
   (fn [state]
     (let [root (-> (:rum/react-component state)
                    (.getDOMNode))
           react-id (.getAttribute root "data-reactid")
           shadow-root (.parentNode.createShadowRoot root)]
       (attach-css css shadow-root)
       (.appendChild shadow-root root)))})

;;;; rendering code

(defn header-c%3 [{:keys [title]}]
  [:section {:role "region"}
   [:header
    [:menu
     [:button {:data-icon "search"}
      [:span {:class "icon icon-search"}]]
     [:button
      [:span {:class "icon icon-compose"}]]]
    [:h1 title]]])

(defn list-c%3 [{:keys [data]}]
  [:section {:data-type "list"}
   (reduce (fn [ul item]
             (conj ul
                   [:li {:key (gensym)} ;doesn't do :key prop correctly :(
                    [:a {:on-click #(js/console.log item)}
                     [:aside {:class "pack-end"
                              :data-icon "forward"}]
                     [:p [:strong item]]]]))
           [:ul] data)])

(rum/defc example-list%3 < (shadow-dom ["css/dapps/index.css"]) []
  [:div
   (header-c%3 {:title "Hello Style!"})
   (list-c%3 {:data ["some" "stuff" "to" "render"]})])

(rum/mount (example-list%3)
           (.getElementById js/document "list-example3"))
