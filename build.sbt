name := "scala-mutual-information"
inThisBuild(
  List(
    version := "0.0.2",
    organization := "konradmalik",
    scalaVersion := "2.12.13",
    crossScalaVersions := Seq("2.11.12", "2.12.13", "2.13.5"),
    fork := true,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalafixScalaBinaryVersion := scalaBinaryVersion.value,
    scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0",
  )
)

scalacOptions += {
    CrossVersion.partialVersion(scalaBinaryVersion.value) match {
        case Some((2, n)) if n <= 12 => "-Ywarn-unused-import"
        case _ => "-Ywarn-unused:imports"
    }
}
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
