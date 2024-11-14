ThisBuild / organization := "io.nary"
ThisBuild / version := "0.0.1"
ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "espresso-graph",

    resolvers ++= Resolver.sonatypeOssRepos("releases") ++
      Resolver.sonatypeOssRepos("snapshots") ++
      Seq("jitpack" at "https://jitpack.io"),

    // Modern compiler plugins
    addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),

    // Updated dependencies
    libraryDependencies ++= Seq(
      "com.github.ryanonsrc" %% "espresso" % "v1.0.0",
      // Test dependencies
      "org.scalatest" %% "scalatest" % "3.2.17" % Test
    ),

    Test / run / mainClass := Some("io.nary.espresso.sample.Main"),
    Test / fork := true,

    // Compiler options
    scalacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-feature",
      "-deprecation",
      "-unchecked",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-Wunused:imports",
      "-Wvalue-discard"
    )
  )