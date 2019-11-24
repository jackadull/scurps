lazy val jackadull = net.jackadull.build.JackadullBuild.onTravis(name = "scurps", version = "0.1.1-SNAPSHOT",
  basePackage = "net.jackadull.scurps", capitalizedIdentifier = "Scurps")

lazy val scurpsBuild:Project = project.in(file(".")).configure(jackadull.project)
