enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)

name := "Undo Redo experiments"

scalaVersion := "2.13.6"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js"                      %%% "scalajs-dom"   % "1.0.0",
  "io.suzaku"                         %%% "diode-core"    % "1.1.13",
  "io.suzaku"                         %%% "diode-react"   % "1.1.13",
  "io.circe"                          %%% "circe-core"    % "0.13.0",
  "io.circe"                          %%% "circe-generic" % "0.13.0",
  "io.circe"                          %%% "circe-parser"  % "0.13.0",
  "com.github.japgolly.scalajs-react" %%% "core"          % "1.7.7",
  "com.github.japgolly.scalajs-react" %%% "extra"         % "1.7.7"
)

npmDependencies in Compile ++= Seq("react" -> "16.13.1", "react-dom" -> "16.13.1")
