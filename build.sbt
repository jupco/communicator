tutSettings

description := "An OkHttp wrapper for Scala built with Android in mind"

githubProject := "communicator"

javacOptions ++=
    "-source" :: "1.7" ::
    "-target" :: "1.7" ::
    Nil

libraryDependencies ++=
    "com.squareup.okhttp3" % "okhttp" % "3.3.1" ::
    "io.monix" %% "monix" % "2.0-RC7" ::
    "com.squareup.okhttp3" % "mockwebserver" % "3.3.1" % "test" ::
    "org.scalatest" %% "scalatest" % "2.2.6" % "test" ::
    Nil

name := "Communicator"

organization := "io.taig"

scalacOptions ++=
    "-deprecation" ::
    "-feature" ::
    Nil

scalaVersion := "2.11.8"

startYear := Some( 2013 )

tutTargetDirectory := baseDirectory.value