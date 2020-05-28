enablePlugins(ScalaJSPlugin)

name := "Undo Redo experiments"

scalaVersion := "2.13.2"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom"   % "1.0.0",
  "com.lihaoyi"  %%% "scalatags"     % "0.9.1",
  "io.suzaku"    %%% "diode-core"    % "1.1.8",
  "io.circe"     %%% "circe-core"    % "0.13.0",
  "io.circe"     %%% "circe-generic" % "0.13.0",
  "io.circe"     %%% "circe-parser"  % "0.13.0"
)
