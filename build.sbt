import net.jackadull.build.dependencies.JackadullDependencies._

lazy val jackadull = net.jackadull.build.JackadullBuild.onTravis(name = "scurps", version = "0.1.1-SNAPSHOT",
  basePackage = "net.jackadull.scurps", capitalizedIdentifier = "Scurps")

lazy val scurpsBuild:Project = project.in(file("."))
  .configure(jackadull.project, jackadull.dependencies(ScalaTest % Test))
  .settings(
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++= Seq("-Yrangepos", "-Ywarn-unused:imports")
  )
