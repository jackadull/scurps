import net.jackadull.build.dependencies.JackadullDependencies._

lazy val jackadull = net.jackadull.build.JackadullBuild.onTravis(name = "scurps", version = "0.1.1-SNAPSHOT",
  basePackage = "net.jackadull.scurps", capitalizedIdentifier = "Scurps")

lazy val scurpsBuild:Project = project.in(file("."))
  .configure(jackadull.project, jackadull.dependencies(ScalaTest % Test))
  .settings(
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++= Seq("-Yrangepos", "-Ywarn-unused:imports")
  )

scalafixDependencies in ThisBuild += "org.scalalint" %% "rules" % "0.2.1"

wartremoverErrors ++= Seq(Wart.ArrayEquals, Wart.EitherProjectionPartial, Wart.Enumeration, Wart.ExplicitImplicitTypes,
  Wart.FinalCaseClass, Wart.FinalVal, Wart.MutableDataStructures, Wart.Null, Wart.Option2Iterable, Wart.OptionPartial,
  Wart.Return, Wart.TraversableOps, Wart.TryPartial, Wart.Var, Wart.While)

addCommandAlias("fix", "scalafix; test:scalafix")
