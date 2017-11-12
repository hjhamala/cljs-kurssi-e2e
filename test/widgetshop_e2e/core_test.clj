(ns widgetshop-e2e.core-test
  (:require [clojure.test :refer :all]
            [widgetshop-e2e.core :refer :all]
            [limo.api :as api]
            [limo.driver :as driver]
            [clojure.data.json :as json]))



(defn open-index
  []
  (api/to "http://localhost:3000"))

(defn get-cart-size
  []
  (is (api/visible? "#cart-size"))
  (api/text (api/element "#cart-size")))

(defn add-to-cart
  [item-click-id]
  (api/visible? item-click-id)
  (api/click item-click-id))


(defn category-selector-loaded-and-empty-cart?
  []
  (is (api/visible? "#app-bar"))
  (is (= "0" (get-cart-size)))
  (is (api/visible? "#select-product-category")))

(deftest test-cart
  ;; sets the implicit driver to use.
  ;; Alternatively you can pass the driver in as the first argument
  (api/set-driver! (driver/create-chrome))
  ;; tells selenium webdriver to implicitly wait 1s, for up the *default-timeout* (explicit wait) of 15 seconds
  ;; see http://www.seleniumhq.org/docs/04_webdriver_advanced.jsp for more details
  (api/implicit-wait 1000)
  (open-index)
  (category-selector-loaded-and-empty-cart?)
  (Thread/sleep 5000)
  (api/click "#select-product-category")
  (is (api/visible? "#menu-item-id-1"))
  (Thread/sleep 5000)
  (api/click "#menu-item-id-1")
  (Thread/sleep 5000)
  (add-to-cart "#add-to-cart-button-2")
  (is (= "1" (get-cart-size))))


