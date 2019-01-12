import sbt._
object Dependencies {
  // for all category goodness
  val cats = "org.typelevel" %% "cats" % "0.9.0"

  // light-weight actor based http server library (not a framework)
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.9"

  // declarative configuration that's auto parsed
  val pureconfig = "com.github.pureconfig" %% "pureconfig" % "0.7.1"

  // graphql library
  val sangria = "org.sangria-graphql" %% "sangria" % "1.2.2"

  // grakn types are used in graphql schema
  lazy val grakn = "ai.grakn" % "grakn-graph" % "0.13.0"

  // graphql queries are resolved using graql
  lazy val grakn_titan =
    "ai.grakn" % "titan-factory" % "0.13.0"

  val allDeps = Seq(cats, akkaHttp, pureconfig, sangria)

}
