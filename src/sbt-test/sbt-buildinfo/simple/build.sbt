name := "helloworld"

version := "0.1"

scalaVersion := "2.10.2"

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq(
  name,
  BuildInfoKey.map(version) { case (n, v) => "projectVersion" -> v.toDouble },
  scalaVersion,
  ivyXML,
  homepage,
  licenses,
  isSnapshot,
  "year" -> 2012,
  "sym" -> 'Foo,
  BuildInfoKey.action("buildTime") { 1234L },
  target
)

buildInfoPackage := "hello"

homepage := Some(url("http://example.com"))

licenses := Seq("MIT License" -> url("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE"))

val check = taskKey[Unit]("checks this plugin")

check := {
  val f = (sourceManaged in Compile).value / "sbt-buildinfo" / ("%s.scala" format "BuildInfo")
  val lines = scala.io.Source.fromFile(f).getLines.toList
  lines match {
    case """package hello""" ::
         """""" ::
         """/** This object was generated by sbt-buildinfo. */""" ::
         """case object BuildInfo {""" ::
         """  /** The value is "helloworld". */"""::
         """  val name = "helloworld"""" ::
         """  /** The value is 0.1. */"""::
         """  val projectVersion = 0.1""" ::
         """  /** The value is "2.10.2". */""" ::
         """  val scalaVersion = "2.10.2"""" ::
         """  /** The value is Seq(). */""" ::
         """  val ivyXml = Seq()""" ::
         """  /** The value is Some(new java.net.URL("http://example.com")). */""" ::
         """  val homepage: Option[java.net.URL] = Some(new java.net.URL("http://example.com"))""" ::
         """  /** The value is Seq(("MIT License" -> new java.net.URL("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE"))). */""" ::
         """  val licenses = Seq(("MIT License" -> new java.net.URL("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE")))""" ::
         """  /** The value is false. */""" ::
         """  val isSnapshot = false""" ::
         """  /** The value is 2012. */""" ::
         """  val year = 2012""" ::
         """  /** The value is 'Foo. */""" ::
         """  val sym = 'Foo""" ::
         """  /** The value is 1234L. */""" ::
         """  val buildTime = 1234L""" ::
         targetInfoComment ::
         targetInfo :: // """
         """  override val toString = "name: %s, projectVersion: %s, scalaVersion: %s, ivyXml: %s, homepage: %s, licenses: %s, isSnapshot: %s, year: %s, sym: %s, buildTime: %s, target: %s" format (name, projectVersion, scalaVersion, ivyXml, homepage, licenses, isSnapshot, year, sym, buildTime, target)""" ::
         "" ::
         """  val toMap = Map[String, Any](""" ::
         """    "name" -> name,""" ::
         """    "projectVersion" -> projectVersion,""" ::
         """    "scalaVersion" -> scalaVersion,""" ::
         """    "ivyXml" -> ivyXml,""" ::
         """    "homepage" -> homepage,""" ::
         """    "licenses" -> licenses,""" ::
         """    "isSnapshot" -> isSnapshot,""" ::
         """    "year" -> year,""" ::
         """    "sym" -> sym,""" ::
         """    "buildTime" -> buildTime,""" ::
         """    "target" -> target)""" ::
         """}""" :: Nil if (targetInfo contains "val target = new java.io.File(") =>
    case _ => sys.error("unexpected output: \n" + lines.mkString("\n"))
  }
  ()
}
