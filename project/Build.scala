import sbt._
import sbt.Keys._
import android.Keys._

object Build extends android.AutoBuild
{
	lazy val main = Project( "communicator", file( "." ) )
		.settings(
			autoScalaLibrary := false,
			libraryDependencies += "com.google.android" % "android" % "4.4" % "provided" from ( "file://" + System.getenv( "ANDROID_HOME" ) + "/platforms/android-19/android.jar" ),
			name := "Communicator",
			organization := "com.taig.android",
			scalaVersion := "2.11.2",
			version := "1.0.6"
	)
}
