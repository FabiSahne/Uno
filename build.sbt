val scala3Version = "3.4.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Uno",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    libraryDependencies += "org.scalafx" %% "scalafx" % "22.0.0-R33",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",

    ThisBuild / coverageExcludedFiles := """.*uno/Main;.*/uno/views/.*"""
  )
