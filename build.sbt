lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.3"
    )),
    name := "scalatest-example"
  )

// scalacOptions += "-deprecation"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "org.mongodb.scala" % "mongo-scala-driver_2.13" % "4.2.0-beta1"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

