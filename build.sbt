ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val CatsVersion       = "2.6.1"
val CatsEffectVersion = "2.5.4"
val CirceVersion      = "0.14.1"
val AkkaVersion       = "2.6.8"
val AkkaHttpVersion   = "10.2.9"

lazy val root = (project in file("."))
  .settings(
    name := "shopping-list-one"
  )

libraryDependencies ++= Seq(
  "org.typelevel"     %% "cats-core"        % CatsVersion,
  "org.typelevel"     %% "cats-effect"      % CatsEffectVersion,
  "io.circe"          %% "circe-core"       % CirceVersion,
  "io.circe"          %% "circe-generic"    % CirceVersion,
  "io.circe"          %% "circe-parser"     % CirceVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream"      % AkkaVersion,
  "com.typesafe.akka" %% "akka-http"        % AkkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-circe"  % "1.39.2",
)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds",
  "-Ypartial-unification"
)
