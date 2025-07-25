rep --port 1667 '
(do
 (load-file "src/main/clojure/clojure/data/xml/protocols.cljc")
 (load-file "src/main/clojure/clojure/data/xml/impl.cljc")
 (load-file "src/main/clojure/clojure/data/xml/clr/name.cljr")
 (load-file "src/main/clojure/clojure/data/xml/name.cljc")
 (load-file "src/main/clojure/clojure/data/xml/pu_map.cljc")
 (load-file "src/main/clojure/clojure/data/xml/node.cljc")
 (load-file "src/main/clojure/clojure/data/xml/prxml.cljc")
 (load-file "src/main/clojure/clojure/data/xml/event.cljc")
 (assembly-load "Microsoft.Extensions.Logging")
 (assembly-load "Microsoft.Extensions.Logging.Console")
 (assembly-load "Microsoft.Extensions.Logging.Abstractions")
 (load-file "src/main/clojure/clojure/data/xml/clr/emit.cljr")
 :ok
)'
