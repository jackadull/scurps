scalaVersion := "2.12.7"

import net.jackadull.build.dependencies.JackadullDependencies._
import net.jackadull.build.{JackadullBuild, ProjectInfo}

import scala.language.postfixOps

lazy val jackadull = JackadullBuild onTravis ProjectInfo(
  name = "scurps",
  version = "0.1.0-SNAPSHOT",
  basePackage = "net.jackadull.scurps",
  capitalizedIdentifier = "Scurps"
)

lazy val scurpsBuild = (project in file (".")).configure(jackadull project)
  .configure(jackadull dependencies (ScalaTest % Test))
  .settings(resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  .settings(libraryDependencies ++= Seq(
    "net.jackadull" %% "gurps-specification" % "0.0.1-SNAPSHOT" % Test,
    "net.jackadull" %% "spec-driven-scalatest" % "1.0.2" % Test,
    "org.typelevel" %% "spire" % "0.16.0"
  ))

addCommandAlias("build", jackadull buildCommand)
addCommandAlias("ci", jackadull ciCommand)
