import sbt._
import Keys._
import com.linkedin.sbt.restli._

object ApplicationBuild extends Build with All {

  // We declare project variables as "lazy val" so their initializations can forward-reference each other.

  /**
   * This project contains your handwritten Rest.li "resource" implementations.  See rest.li documentation for detail
   * on how to write resource classes.
   */
  lazy val PlaySkeletonMidTier = Project("play-skeleton-mt", file("example"))
    .dependsOn(dataTemplate)
    .compileRestspec(
      apiName = "play-skeleton",
      apiProject = api,
      resourcePackages = List("resources"),
      dataTemplateProject = dataTemplate,
      compatMode = "backwards"
    )


  lazy val dataTemplate = Project("data-template", file("data-template"))
    .compilePegasus()

  lazy val api = Project("api", file("api"))
    .dependsOn(dataTemplate)
    .generateRequestBuilders(
      dataTemplateProject = dataTemplate
    )
}

