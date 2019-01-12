import Dependencies._

name := "grakn-kbc-poc-ui"

version := "1.0"

scalaVersion := "2.12.3"

resolvers ++= Seq(
  "grakn-development-snapshots" at "https://maven.grakn.ai/content/repositories/snapshots/",
  Resolver.sonatypeRepo("releases"))



libraryDependencies ++= allDeps

scalacOptions := Seq(
  "-Ypartial-unification",
  "-Ywarn-extra-implicit",
  "-Ywarn-infer-any",
  "-Xlint:adapted-args",
  "-Xlint:by-name-right-associative",
  "-Xlint:constant",
  "-Xlint:delayedinit-select",
  "-Xlint:doc-detached",
  "-Xlint:inaccessible",
  "-Xlint:infer-any",
  "-Xlint:missing-interpolator",
  "-Xlint:nullary-override",
  "-Xlint:nullary-unit",
  "-Xlint:option-implicit",
  "-Xlint:package-object-classes",
  "-Xlint:poly-implicit-overload",
  "-Xlint:private-shadow",
  "-Xlint:stars-align",
  "-Xlint:type-parameter-shadow",
  "-Xlint:unsound-match",
  "-unchecked"
)
