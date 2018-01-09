name := "IpaneraSparky"

version := "0.1"

scalaVersion := "2.11.8"


libraryDependencies ++= {
  val sparkVer = "2.2.0"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer % "provided" withSources(),
    "org.mongodb.spark" %% "mongo-spark-connector" % "2.2.0",
    "org.apache.spark" %% "spark-sql" % "2.2.0"

  )
}
