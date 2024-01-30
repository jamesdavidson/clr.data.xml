rep --port 1667 '
(do
 (load-file "src/main/clojure/clojure/data/xml/protocols.cljc")
 (load-file "src/main/clojure/clojure/data/xml/impl.cljc")
 (load-file "src/main/clojure/clojure/data/xml/clr/name.cljr")
 (load-file "src/main/clojure/clojure/data/xml/name.cljc")
 :ok
)'
