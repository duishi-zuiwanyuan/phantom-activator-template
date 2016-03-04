name := """Next-Admin"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

val phantomVersion = "1.22.0"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.wisedu.next" % "core" % "1.0",
  "com.websudos"  %% "phantom-dsl"                   % phantomVersion,
  "com.websudos"  %% "util-parsers"                  % "0.9.11"
)

resolvers ++= Seq(
  "Typesafe repository snapshots"    at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases"     at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  "Websudos"                         at "https://dl.bintray.com/websudos/oss-releases/"
)

resolvers += Resolver.mavenLocal