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
  .settings(libraryDependencies ++= Seq(
    "org.typelevel" %% "spire" % "0.16.0"
  ))

addCommandAlias("build", jackadull buildCommand)
addCommandAlias("ci", jackadull ciCommand)
