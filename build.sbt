name := "scala-mutual-information"
inThisBuild(
  List(
    version := "0.0.1",
    organization := "konradmalik",
    scalaVersion := "2.12.13",
    fork := true,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions += "-Ywarn-unused-import", // required by `RemoveUnused` rule
    scalafixScalaBinaryVersion := scalaBinaryVersion.value,
    scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0",
  )
)

githubOwner := "konradmalik"
githubRepository := "ScalaMI"

shellPrompt := (_ => fancyPrompt(name.value))

def fancyPrompt(projectName: String): String =
  s"""|
      |[info] Welcome to the ${cyan(projectName)} project!
      |sbt> """.stripMargin

def cyan(projectName: String): String =
  scala.Console.CYAN + projectName + scala.Console.RESET

addCommandAlias("lint", "scalafixAll; scalafmtAll")
