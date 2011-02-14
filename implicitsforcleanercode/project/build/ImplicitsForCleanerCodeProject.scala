import sbt._

class ImplicitsForCleanerCode(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
  lazy val specs = "org.scala-tools.testing" %% "specs" % "1.6.6" % "test"
  lazy val junit = "junit" % "junit" % "4.8.2" % "test"
  lazy val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test"
}
