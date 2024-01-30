;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.impl
  "Shared private code for data.xml namespaces"
  {:author "Herwig Hochleitner"}
  #?@(:clj [(:import
              [java.util Base64]
              [java.nio.charset StandardCharsets])]
      :cljr [(:import
               [System.Text Encoding])]))

(defn- var-form? [form]
  (and (seq? form) (= 'var (first form))))

(defn- export-form [var-name]
  (let [is-var (var-form? var-name)
        vsym (symbol (name (if is-var (second var-name) var-name)))]
    `[(def ~vsym ~var-name)
      (alter-meta! (var ~vsym)
                   (constantly (assoc (meta ~(if is-var
                                               var-name
                                               `(var ~var-name)))
                                      :wrapped-by (var ~vsym))))]))

(defmacro export-api
  "This creates vars, that take their (local) name, value and metadata from another var"
  [& names]
  (cons 'do (mapcat export-form names)))

(defmacro static-case
  "Variant of case where keys are evaluated at compile-time"
  [val & cases]
  `(case ~val
     ~@(mapcat (fn [[field thunk]]
                 [(eval field) thunk])
               (partition 2 cases))
     ~@(when (odd? (count cases))
         [(last cases)])))

(defmacro extend-protocol-fns
  "Helper to many types to a protocol with a method map, similar to extend"
  [proto & types+mmaps]
  (assert (zero? (mod (count types+mmaps) 2)))
  (let [gen-extend (fn [type mmap] (list `extend type proto mmap))]
    `(do ~@(for [[type mmap] (partition 2 types+mmaps)]
             (if (coll? type)
               (let [mm (gensym "mm-")]
                 `(let [~mm ~mmap]
                    ~@(map gen-extend type (repeat mm))))
               (gen-extend type mmap))))))

(defmacro compile-if
  "Evaluate `exp` and if it returns logical true and doesn't error, expand to
  `then`.  Else expand to `else`.

  see clojure.core.reducers"
  [exp then else]
  (if (try (eval exp)
           (catch #?(:clj Throwable :cljr Exception) _ false))
    `(do ~then)
    `(do ~else)))

#?(:clj
   (defn b64-encode [^bytes ba]
     (let [encoder (Base64/getEncoder)]
       (String. (.encode encoder ba) StandardCharsets/ISO_8859_1)))

   :cljr
   ; we can use ASCII on CLR because using ISO-8859-1 is just a JVM perf improvement
   ; https://stackoverflow.com/questions/29916494/why-jdk8s-base64-uses-iso-8859-1
   (defn b64-encode
    "bytes to base64 string"
    ([^|System.Byte[]| ba]
               (.GetString Encoding/ASCII ba))))
