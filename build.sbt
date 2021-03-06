import scalariform.formatter.preferences._

val scalatest = "org.scalatest" %% "scalatest" % "2.2.1" % "test"
val scalamock = "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1" % "test"
val scalacheck = "org.scalacheck" %% "scalacheck" % "1.12.1" % "test"
val nscalaTime = "com.github.nscala-time" %% "nscala-time" % "1.6.0"
val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.0"
val akka = "com.typesafe.akka" %% "akka-actor" % "2.3.8"
val socko = "org.mashupbots.socko" %% "socko-webserver" % "0.6.0"
val argonaut = "io.argonaut" %% "argonaut" % "6.1-M5"

val commonSettings = scalariformSettings ++ Seq(
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.11.4",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-encoding", "utf8",
    "-Xfuture",
    "-Xlint"
  ),
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(DoubleIndentClassDeclaration, true)
    .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)
    .setPreference(AlignSingleLineCaseStatements, true),
  wartremoverErrors += Wart.Any2StringAdd,
  wartremoverWarnings += Wart.AsInstanceOf,
  wartremoverWarnings += Wart.EitherProjectionPartial,
  wartremoverWarnings += Wart.IsInstanceOf,
  wartremoverWarnings += Wart.JavaConversions,
  wartremoverWarnings += Wart.NonUnitStatements,
  wartremoverWarnings += Wart.Nothing,
  wartremoverWarnings += Wart.Null,
  wartremoverWarnings += Wart.OptionPartial,
  wartremoverWarnings += Wart.Product,
  wartremoverWarnings += Wart.Return,
  wartremoverWarnings += Wart.Serializable,
  wartremoverWarnings += Wart.TryPartial,
  libraryDependencies ++= Seq(
    scalatest,
    scalamock,
    scalacheck,
    scalaz
  )
)

lazy val paperEngine = project.in(file("./modules/paper-engine"))
  .settings(commonSettings: _*)
  .settings(name := "paper-engine")

lazy val akkaNetwork = project.in(file("./modules/akka-network"))
  .settings(commonSettings: _*)
  .settings(name := "akka-network")
  .settings(libraryDependencies += akka)

lazy val webServer = project.in(file("./modules/web-server"))
  .settings(commonSettings: _*)
  .settings(name := "web-server")
  .settings(libraryDependencies ++= Seq(socko, argonaut))

// dummy module to aggregate other modules
lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .aggregate(paperEngine, akkaNetwork, webServer)
