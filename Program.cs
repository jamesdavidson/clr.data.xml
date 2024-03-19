using clojure.lang;
using System.Reflection;

// start up Clojure runtime including compiler
RT.Init();

// make .cljr file resources available from DLL
Assembly.Load("clojure.tools.nrepl");
Assembly.Load("clojure.tools.reader");

// compile nREPL lib
var ns = "clojure.tools.nrepl";
RT.var("clojure.core", "require").invoke(Symbol.intern(ns));

// create nREPL server (default port is 1667)
var startServer = RT.var(ns, "start-server!");
var server = startServer.invoke();

// allow exit on Ctrl-C by shutting down server
var stopServer = RT.var(ns, "stop-server!");
Console.CancelKeyPress += (_, _) => stopServer.invoke(server);
