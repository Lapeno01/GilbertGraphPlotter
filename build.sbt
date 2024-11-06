ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "GilbertGraphPlotter",
    libraryDependencies += "org.jfree" % "jfreechart" % "1.5.5"
  )
