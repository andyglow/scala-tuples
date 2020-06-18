import sbt._
import sbt.Keys._
import xerial.sbt.Sonatype._
import ReleaseTransformations._
import spray.boilerplate.BoilerplatePlugin

// https://github.com/xerial/sbt-sonatype/issues/71
publishTo in ThisBuild := sonatypePublishTo.value

enablePlugins(BoilerplatePlugin)

lazy val scala213 = "2.13.2"
lazy val scala212 = "2.12.11"
lazy val scala211 = "2.11.12"

organization        := "com.github.andyglow"
homepage            := Some(new URL("http://github.com/andyglow/scala-tuples"))
startYear           := Some(2020)
organizationName    := "andyglow"
name                := "scala-tuples"
version             := "0.1.0"
scalaVersion        := scala211
crossScalaVersions  := List(scala213, scala212, scala211)
publishMavenStyle   := true
publishArtifact     := true

scalacOptions ++= {
  val options = Seq(
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    "-deprecation",
    "-language:experimental.macros",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Xfuture")

  // WORKAROUND https://github.com/scala/scala/pull/5402
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 12)) => options.map {
      case "-Xlint"               => "-Xlint:-unused,_"
      case "-Ywarn-unused-import" => "-Ywarn-unused:imports,-patvars,-privates,-locals,-params,-implicits"
      case other                  => other
    }
    case _             => options
  }
}

scalacOptions in (Compile,doc) ++= Seq(
  "-groups",
  "-implicits",
  "-no-link-warnings")

licenses := Seq(("LGPL-3.0", url("http://opensource.org/licenses/LGPL-3.0")))
sonatypeProfileName := "com.github.andyglow"
sonatypeProjectHosting := Some(
  GitHubHosting(
    "andyglow",
    "scala-patch",
    "andyglow@gmail.com"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/andyglow/scala-tuples"),
    "scm:git@github.com:andyglow/scala-tuples.git"))

developers := List(
  Developer(
    id    = "andyglow",
    name  = "Andriy Onyshchuk",
    email = "andyglow@gmail.com",
    url   = url("https://ua.linkedin.com/in/andyglow")))

releasePublishArtifactsAction := PgpKeys.publishSigned.value

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publishSigned", _), enableCrossBuild = true),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _), enableCrossBuild = true),
  pushChanges)

libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"  % "3.1.1" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.1" % Test) ++ Seq(
  (scalaVersion apply ("org.scala-lang" % "scala-reflect" % _ % Compile)).value.withSources.withJavadoc)

sourceGenerators in Compile += Def.task {
  val v   = (scalaVersion in Compile).value
  val s   = (sourceManaged in Compile).value
  val log = Keys.streams.value.log

  Boiler.gen(s, v, log)
}

mappings in (Compile, packageSrc) ++= {
  val base = (sourceManaged in Compile).value
  (managedSources in Compile).value.map { file =>
    file -> file.relativeTo(base).get.getPath
  }
}