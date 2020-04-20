(ns user.garden.style.browser-test
  (:require
   [clojure.test :as test]
   [user.garden.style.browser :as style.browser]
   ))


(test/deftest main
  (test/is (== 6 (count (style.browser/property-range--int ".prefix" :property [0 1 2 3 4 5]))))
  )
