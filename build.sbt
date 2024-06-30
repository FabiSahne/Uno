val scala3Version = "3.4.1"

val setJavaFXVersion = settingKey[Seq[ModuleID]]("Set JavaFX version")

setJavaFXVersion := {
  val arch = sys.props("os.arch")
  val osName = sys.props("os.name").toLowerCase
  val javafxVersion = "22"
  val javafxClassifier = osName match {
    case os if os.contains("windows") => "win"
    case os if os.contains("linux") =>
      arch match {
        case "aarch64" => "linux-aarch64"
        case _         => "linux"
      }
    case _ => throw new UnsupportedOperationException("Unsupported OS")
  }

  Seq(
    "org.openjfx" % "javafx-base" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-controls" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-fxml" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-graphics" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-media" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-swing" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-web" % javafxVersion classifier javafxClassifier
  )
}

lazy val root = project
  .in(file("."))
  .settings(
    name := "Uno",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    libraryDependencies += "org.scalafx" %% "scalafx" % "22.0.0-R33" excludeAll ExclusionRule(
      organization = "org.openjfx"
    ),
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.3.0",
    libraryDependencies += "org.playframework" %% "play-json" % "3.0.4",
    libraryDependencies ++= (Seq() ++ setJavaFXVersion.value),
    assembly / mainClass := Some("uno.main"),
    assembly / assemblyJarName := "Uno.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _                        => MergeStrategy.first
    }
  )
