(ns user.garden.style.browser
  #?(:cljs
     (:require-macros
      [garden.def :refer [defcssfn defrule defstyles defkeyframes]]
      [user.garden.patch :refer [defstylesfn defstylesheetfn]]))
  (:require
   [garden.core :as garden]
   #?(:clj [garden.def :refer [defcssfn defrule defstyles defkeyframes]])
   [garden.selectors :as s]
   [garden.stylesheet :as stylesheet]
   [garden.color :as color :refer [hsl hsla rgb color-name->hex]]
   [garden.units :as u :refer [px pt em vw percent]]
   [garden.arithmetic :as arithmetic]
   #?(:clj [user.garden.patch :refer [defstylesfn defstylesheetfn]])
   ))


;; * issues


;; https://github.com/noprompt/garden/issues/115


;; * media queries


(defn at-min-width-1281
  [& args]
  ;; laptops, desktops
  ;; "screen = 1281px to higher resolution desktops"
  (stylesheet/at-media
    {:screen true :min-width (px 1281)}
    args))


(defn at-max-width-1200
  [& args]
  (stylesheet/at-media
    {:screen true :max-width (px 1200)}
    args))


(defn at-max-width-1024
  [& args]
  (stylesheet/at-media
    {:screen true :max-width (px 1024)}
    args))


(defn at-max-width-800
  [& args]
  (stylesheet/at-media
    {:screen true :max-width (px 800)}
    args))


(defn at-mobile
  [& args]
  (stylesheet/at-media
    {:screen true :min-device-width (px 320) :max-device-width (px 480)}
    args))


(defn at-iphone-x
  [& args]
  (stylesheet/at-media
    {:screen true :min-device-width (px 375) :max-device-width (px 667)}
    args))


(defn at-tablet
  [& args]
  ;; tablets portrait
  ;; B/w 768px to 1024px
  (stylesheet/at-media
    {:screen true :min-device-width (px 768) :max-device-width (px 1024)}
    args))


;; * fns


(defn properties-range
  [prefix properties unit range]
  (map
    (fn [n]
      [(keyword (str "." prefix "-" n))
       (zipmap
         properties
         (repeat [[(unit n) :!important]]))])
    range))


(defn property-range
  [prefix property unit range]
  (map
    (fn [n]
      [(keyword (str "." prefix "-" n))
       {property [[(unit n) :!important]]}])
    range))


(defn property-range--int
  [prefix property range]
  (property-range prefix property int range))


(defn property-range--px-unit
  "class prefix (str \".\" `prefix` \"-\") "
  [prefix property range]
  (property-range prefix property px range))


(defn property-range--percent-unit
  "class prefix (str \".\" `prefix` \"-\") "
  [prefix property]
  (property-range prefix property percent (range 0 101 1)))


;; * sugar


;; ** depth


(defstylesfn sugar--z-index
  []
  (property-range--int "z" :z-index (range 0 10 1)))


;; ** font


(defstylesfn sugar--font-size
  []
  (property-range--px-unit "font-size" :font-size (range 1 201 1)))


;; ** whitespace


(defstylesfn sugar--ws
  []
  [:.ws-normal {:white-space [[:normal :!important]]}]
  [:.ws-nowrap {:white-space [[:nowrap :!important]]}]
  [:.ws-pre {:white-space [[:pre :!important]]}])


(defstylesfn sugar--white-space
  []
  [:.white-space-normal {:white-space [[:normal :!important]]}]
  [:.white-space-nowrap {:white-space [[:nowrap :!important]]}]
  [:.white-space-pre {:white-space [[:pre :!important]]}])


;; ** display


(defstylesfn sugar--display
  []
  [:d-none {:display [[:none :!important]]}]
  [:d-block {:display [[:block :!important]]}]
  [:.d-flex {:display [[:flex :!important]]}]
  [:.d-inline-flex {:display [[:inline-flex :!important]]}])


;; ** flex


(defstylesfn sugar--flex-wrap
  []
  [:.flex-wrap
   {:flex-wrap [[:wrap :!important]]}]
  [:.flex-nowrap
   {:flex-wrap [[:nowrap :!important]]}]
  [:.flex-wrap-reverse
   {:flex-wrap [[:wrap-reverse :!important]]}])


(defstylesfn sugar--flex-justify-content
  []
  [:.justify-content-start
   :.justify-content-flex-start
   {:justify-content [[:flex-start :!important]]}]
  [:.justify-content-end
   :.justify-content-flex-end
   {:justify-content [[:flex-end :!important]]}]
  [:.justify-content-center
   {:justify-content [[:center :!important]]}]
  [:.justify-content-between
   :.justify-content-space-between
   {:justify-content [[:space-between :!important]]}]
  [:.justify-content-around
   :.justify-content-space-around
   {:justify-content [[:space-around :!important]]}]
  [:.justify-content-evenly
   :.justify-content-space-evenly
   {:justify-content [[:space-evenly :!important]]}])


(defstylesfn sugar--flex-align-items
  []
  [:.align-items-start
   :.align-items-flex-start
   {:align-items [[:flex-start :!important]]}]
  [:.align-items-end
   :.align-items-flex-end
   {:align-items [[:flex-end :!important]]}]
  [:.align-items-center
   {:align-items [[:center :!important]]}]
  [:.align-items-baseline
   {:align-items [[:baseline :!important]]}]
  [:.align-items-stretch
   {:align-items [[:stretch :!important]]}])


(defstylesfn sugar--flex-align-content
  []
  [:.align-content-start
   :.align-content-flex-start
   {:align-content [[:flex-start :!important]]}]
  [:.align-content-end
   :.align-content-flex-end
   {:align-content [[:flex-end :!important]]}]
  [:.align-content-center
   {:align-content [[:center :!important]]}]
  [:.align-content-stretch
   {:align-content [[:stretch :!important]]}]
  [:.align-content-between
   :.align-content-space-between
   {:align-content [[:space-between :!important]]}]
  [:.align-content-around
   :.align-content-space-around
   {:align-content [[:space-around :!important]]}])


(defstylesfn sugar--flex
  []
  ;; container-property
  [:.flex-row {:flex-direction [[:row :!important]]}]
  [:.flex-row-reverse {:flex-direction [[:row-reverse :!important]]}]
  [:.flex-column {:flex-direction [[:column :!important]]}]
  [:.flex-column-reverse {:flex-direction [[:column-reverse :!important]]}]
  ;;
  (sugar--flex-wrap)
  (sugar--flex-justify-content)
  (sugar--flex-align-items)
  (sugar--flex-align-content))


;;


(defstylesfn sugar--flex-align-self
  []
  [:.align-self-start
   :.align-self-flex-start
   {:align-self [[:flex-start :!important]]}]
  [:.align-self-end
   :.align-self-flex-end
   {:align-self [[:flex-end :!important]]}]
  [:.align-self-center
   {:align-self [[:center :!important]]}]
  [:.align-self-baseline
   {:align-self [[:baseline :!important]]}]
  [:.align-self-stretch
   {:align-self [[:stretch :!important]]}])


(defstylesfn sugar--flex-children
  []
  (property-range--int "order" :order (range 1 11 1))
  (property-range--int "flex-fill" :flex-fill (range 1 13 1))
  (sugar--flex-align-self))


;;


(defstylesfn sugar--font-weight
  []
  [:.font-weight-normal
   {:font-weight [[:normal :!important]]}])


(defstylesfn sugar--color
  []
  [:.white {:color [["white" :!important]]}]
  [:.orange {:color [["orange" :!important]]}]
  [:.red {:color [["red" :!important]]}]
  [:.green {:color [["green" :!important]]}]
  [:.blue {:color [["blue" :!important]]}]
  [:.yellowgreen {:color [["yellowgreen" :!important]]}])


;; ** box-model


(defstylesfn sugar--box-model-w
  []
  (property-range--percent-unit "w" :width))


(defstylesfn sugar--box-model-h
  []
  (property-range--percent-unit "h" :height))


(defstylesfn sugar--box-model-m
  []
  (property-range--px-unit "m" :margin (range 0 101 1))
  (properties-range "mx" [:margin-left :margin-right] px (range 0 101 1))
  (properties-range "my" [:margin-top :margin-bottom] px (range 0 101 1))
  (property-range--px-unit "m-top" :margin-top (range 0 101 1))
  (property-range--px-unit "m-left" :margin-left (range 0 101 1))
  (property-range--px-unit "m-right" :margin-right (range 0 101 1))
  (property-range--px-unit "m-bottom" :margin-bottom (range 0 101 1)))


(defstylesfn sugar--box-model-p
  []
  (property-range--px-unit "p" :padding (range 0 101 1))
  (properties-range "px" [:padding-left :padding-right] px (range 0 101 1))
  (properties-range "py" [:padding-top :padding-bottom] px (range 0 101 1))
  (property-range--px-unit "p-top" :padding-top (range 0 101 1))
  (property-range--px-unit "p-left" :padding-left (range 0 101 1))
  (property-range--px-unit "p-right" :padding-right (range 0 101 1))
  (property-range--px-unit "p-bottom" :padding-bottom (range 0 101 1)))


(defstylesfn sugar--box-model
  []
  (sugar--box-model-w)
  (sugar--box-model-h)
  [:.m-auto :.h-center
   {:margin [[:auto :!important]]}]
  (sugar--box-model-m)
  (sugar--box-model-p)
  (property-range--px-unit "br" :border-radius (range 0 101 1)))


;; ** text


(defstylesfn sugar--text
  []
  [:.text-center
   {:text-align [[:center :!important]]}]
  [:.text-left
   {:text-align [[:left :!important]]}]
  [:.text-right
   {:text-align [[:right :!important]]}]
  [:.text-justify
   {:text-align [[:justify :!important]]}])


;; ** cursor


(defstylesfn sugar--cursor
  []
  [:.cursor-pointer
   {:cursor [["pointer" :!important]]}]
  [:.cursor-wait
   {:cursor [["wait" :!important]]}])


;; ** letter-spacing


(defstylesfn sugar--ls
  []
  (property-range--px-unit "ls" :letter-spacing (range -5 6 1)))


(defstylesfn sugar--letter-spacing
  []
  (property-range--px-unit "letter-spacing" :letter-spacing (range -5 6 1)))


;; ** position


(defstylesfn sugar--position
  []
  [:.position-relative
   {:position [[:relative :!important]]}]
  [:.position-absolute
   {:position [[:absolute :!important]]}]
  [:.position-fixed
   {:position [[:fixed :!important]]}])


;; ** overflow


(defstylesfn sugar--overflow
  []
  [:.overflow-x-auto
   {:overflow {:x [[:auto :!important]]}}]
  [:.overflow-x-scroll
   {:overflow {:x [[:scroll :!important]]}}]
  [:.overflow-x-hidden
   {:overflow {:x [[:hidden :!important]]}}]
  [:.overflow-y-auto
   {:overflow {:y [[:auto :!important]]}}]
  [:.overflow-y-scroll
   {:overflow {:y [[:scroll :!important]]}}]
  [:.overflow-y-hidden
   {:overflow {:y [[:hidden :!important]]}}])


;; ** responsive


(defstylesfn sugar--responsive
  []
  [:.col-1 {:width (percent 8.33)}]
  [:.col-2 {:width (percent 16.66)}]
  [:.col-3 {:width (percent 25)}]
  [:.col-4 {:width (percent 33.33)}]
  [:.col-5 {:width (percent 41.66)}]
  [:.col-6 {:width (percent 50)}]
  [:.col-7 {:width (percent 58.33)}]
  [:.col-8 {:width (percent 66.66)}]
  [:.col-9 {:width (percent 75)}]
  [:.col-10 {:width (percent 83.33)}]
  [:.col-11 {:width (percent 91.66)}]
  [:.col-12 {:width (percent 100)}]
  [(s/attr= "class*" "col-")])
