Global / onChangedBuildSource := ReloadOnSourceChanges

// loads the server project at sbt startup
Global / onLoad := (Global / onLoad).value andThen { s: State =>
  "project server" :: s
}

lazy val commonSettings = Seq(
  scalaVersion := "2.13.6",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation"
  )
)

lazy val client = project
  .enablePlugins(ScalaJSBundlerPlugin)
  .settings(commonSettings)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js"                      %%% "scalajs-dom"   % "1.0.0",
      "io.suzaku"                         %%% "diode-core"    % "1.1.13",
      "io.suzaku"                         %%% "diode-react"   % "1.1.13",
      "io.circe"                          %%% "circe-core"    % "0.13.0",
      "io.circe"                          %%% "circe-generic" % "0.13.0",
      "io.circe"                          %%% "circe-parser"  % "0.13.0",
      "com.github.japgolly.scalajs-react" %%% "core"          % "1.7.7",
      "com.github.japgolly.scalajs-react" %%% "extra"         % "1.7.7"
    ),
    npmDependencies in Compile ++= Seq("react" -> "16.13.1", "react-dom" -> "16.13.1"),
    (fastOptJS / webpackBundlingMode) := BundlingMode.LibraryAndApplication(),
    Compile / fastOptJS / artifactPath := ((Compile / fastOptJS / crossTarget).value /
    ((fastOptJS / moduleName).value + "-opt.js"))
  )

val AkkaVersion = "2.6.15"

lazy val server = project
  .enablePlugins(SbtTwirl, WebScalaJSBundlerPlugin)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                 % "10.2.4",
      "de.heikoseeberger" %% "akka-http-circe"           % "1.36.0",
      "com.typesafe.akka" %% "akka-stream"               % AkkaVersion,
      "com.typesafe.akka" %% "akka-actor-typed"          % AkkaVersion,
      "org.mdedetrich"    %% "sbt-digest-reverse-router" % "0.2.0",
      "com.vmunier"       %% "scalajs-scripts"           % "1.1.4"
    ),
    scalaJSProjects := Seq(client),
    Assets / WebKeys.packagePrefix := "public/",
    Runtime / managedClasspath += (Assets / packageBin).value,
    Assets / pipelineStages := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest)
  )

lazy val root = (project in file("."))
  .aggregate(server)
  .settings(scalaVersion := "2.13.6")
