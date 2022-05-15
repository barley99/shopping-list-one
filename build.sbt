ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.13.8"

val CatsVersion       = "2.6.1"
val CatsEffectVersion = "2.5.4"
val Fs2Version        = "3.2.7"
val DoobieVersion     = "1.0.0-RC2"
val CirceVersion      = "0.14.1"
val AkkaVersion       = "2.6.18"
val AkkaHttpVersion   = "10.2.9"
val TapirVersion      = "0.20.1"

lazy val root = (project in file("."))
  .settings(
    name := "shopping-list-one"
  )

libraryDependencies ++= Seq(
  "org.tpolecat"                %% "doobie-core"              % DoobieVersion,
  "org.tpolecat"                %% "doobie-hikari"            % DoobieVersion,
  "org.tpolecat"                %% "doobie-postgres"          % DoobieVersion,
  "io.circe"                    %% "circe-core"               % CirceVersion,
  "io.circe"                    %% "circe-generic"            % CirceVersion,
  "io.circe"                    %% "circe-parser"             % CirceVersion,
  "com.typesafe.akka"           %% "akka-actor-typed"         % AkkaVersion,
  "com.typesafe.akka"           %% "akka-stream"              % AkkaVersion,
  "com.typesafe.akka"           %% "akka-http"                % AkkaHttpVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server"   % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"         % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle"  % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs"       % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % TapirVersion,
  "de.heikoseeberger"           %% "akka-http-circe"          % "1.39.2",
  "com.typesafe.scala-logging"  %% "scala-logging"            % "3.9.2",
  "org.slf4j"                    % "slf4j-api"                % "1.7.25",
  "ch.qos.logback"               % "logback-classic"          % "1.2.3",
)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds",
)
