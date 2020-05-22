enablePlugins(ScalaJSPlugin, WorkbenchPlugin)

name := "Undo Redo experiments"

scalaVersion := "2.12.10"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "1.0.0",
  "com.lihaoyi"  %%% "scalatags"   % "0.6.7",
  "io.suzaku"    %%% "diode-core"  % "1.1.5"
)

workbenchDefaultRootObject := Some(("target/scala-2.12/classes/index.html", "target/scala-2.12"))
