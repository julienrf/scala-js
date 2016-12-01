import com.typesafe.tools.mima.core._
import com.typesafe.tools.mima.core.ProblemFilters._

object BinaryIncompatibilities {
  val IR = Seq(
      // private, not an issue
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.ir.Infos#GenInfoTraverser.generateExportedConstructorsInfo")
  )

  val Tools = Seq(
      // private[emitter], not an issue
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genDeclareModule"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genDeclareTypeData"),

      // private, not an issue
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.JSDesugaring#Env.this"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.JSDesugaring#JSDesugar.this"),

      // Breaking (remove js.EmptyTree)
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#Let.apply"),
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#Let.copy"),
      ProblemFilters.exclude[IncompatibleResultTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#Let.rhs"),
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#Let.this"),
      ProblemFilters.exclude[MissingClassProblem](
          "org.scalajs.core.tools.javascript.Trees$Try"),
      ProblemFilters.exclude[MissingClassProblem](
          "org.scalajs.core.tools.javascript.Trees$Try$"),
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#VarDef.apply"),
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#VarDef.copy"),
      ProblemFilters.exclude[IncompatibleResultTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#VarDef.rhs"),
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.javascript.Trees#VarDef.this")
  )

  val JSEnvs = Seq(
    ProblemFilters.exclude[MissingMethodProblem](
      "org.scalajs.jsenv.nodejs.JSDOMNodeJSEnv#AbstractDOMNodeRunner.org$scalajs$jsenv$nodejs$JSDOMNodeJSEnv$AbstractDOMNodeRunner$$super$logger")
  )

  val JSEnvsTestKit = Seq(
  )

  val SbtPlugin = Seq(
  )

  val TestAdapter = Seq(
  )

  val CLI = Seq(
  )

  val Library = Seq(
  )

  val TestInterface = Seq(
  )
}
