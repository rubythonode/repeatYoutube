import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "repeatYoutube"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    javaJpa,
    "mysql" % "mysql-connector-java" % "5.1.18",
    "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
    "org.json"%"org.json"%"chargebee-1.0",
    "com.googlecode.json-simple" % "json-simple" % "1.1.1" % "optional",
    "commons-io" % "commons-io" % "2.3"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
