(ns sudoku.core
  (:require [reagent.core :as reagent])  )

(enable-console-print!)

(println "This text is printed from src/sudoku/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state
  (atom {:text "Hello ClojureScript world!"
         :x 0
         :y 0
         :board
         (into [] (take 9 (repeat (into [] (range 1 10)))))}))

;; (js/alert "Arrrgh")

#_(take 3
 (flatten (get @app-state :board)))


(defn three-by-three [grid x y]
  (let [xx (* 3 x)
        yy (* 3 y)
        rows (take 3 (drop yy grid))]
    (into [] (map (fn [row] (into [] (take 3 (drop xx row)))) rows))
    ))

#_(three-by-three (get @app-state :board) 1 0)

(defn unique-list? [list]
  (let [stripped (remove #(= 0 %) list)
        c (count stripped)
        d (count (set stripped))
        ]
    (= c d )))

(defn unique-row? [grid row]
  (unique-list? (nth grid row)))

(defn unique-col? [grid col]
  (unique-list? (nth (apply map list grid) col)))

(defn unique-block? [grid x y]
  (unique-list? (flatten (three-by-three grid x y))))

(apply map list (:board @app-state))
(unique-col? (:board @app-state) 5)
(unique-block? (:board @app-state) 2 2)

(defn sudoku-board []
  [:div
   [:p "hello, debugging"]
   [:table {:style {:border "1px solid black"}}
    (map (fn [row] [:tr
                    (map (fn [col] [:td col]) row)])
         (get @app-state :board) )]])


(defn welcome []
  [:div
   [:h1 (get @app-state :text)]
   [:div (sudoku-board)]])


(defn render [component root]
  (when-let [root-element (. js/document getElementById root)]
    (reagent/render [component] root-element)))

(render welcome "app")

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
