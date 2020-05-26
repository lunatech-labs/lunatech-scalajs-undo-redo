enablePlugins(ScalaJSPlugin, WorkbenchPlugin)

name := "Undo Redo experiments"

scalaVersion := "2.12.10"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom"   % "1.0.0",
  "com.lihaoyi"  %%% "scalatags"     % "0.9.1",
  "io.suzaku"    %%% "diode-core"    % "1.1.5",
  "io.circe"     %%% "circe-core"    % "0.13.0",
  "io.circe"     %%% "circe-generic" % "0.13.0",
  "io.circe"     %%% "circe-parser"  % "0.13.0"
)

workbenchDefaultRootObject := Some(("target/scala-2.12/classes/index.html", "target/scala-2.12"))
