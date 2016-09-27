import com.typesafe.tools.mima.core._
import com.typesafe.tools.mima.core.ProblemFilters._

object BinaryIncompatibilities {
  val IR = Seq(
      // Breaking changes
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.ir.Trees#ClassDef.jsName")
  )

  val Tools = Seq(
      // Breaking changes
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.LinkedClass.jsName"),

      // private, not an issue
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.analyzer.Analyzer#ClassInfo.isAnyRawJSType"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.analyzer.Analyzer#ClassInfo.isStaticModule"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.checker.IRChecker#CheckedClass.jsName"),

      // private[emitter], not an issue
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.JSDesugaring#JSDesugar.this"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.JSDesugaring.genRawJSClassConstructor"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.JSDesugaring.desugarTree"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.JSDesugaring.desugarToFunction"),
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.linker.backend.emitter.JSDesugaring.desugarToFunction"),

      // private[emitter], not an issue
      ProblemFilters.exclude[IncompatibleMethTypeProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.this"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genDefaultMethods"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.isInterface"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genConstructor"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genDefaultMethod"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genClass"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genConstructorExportDef"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genTypeData"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genProperty"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genES6Class"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.linkedClassByName"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genMethod"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genClassDef"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genClassExports"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genExportedMembers"),
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.core.tools.linker.backend.emitter.ScalaJSClassEmitter.genStaticMembers")
  )

  val JSEnvs = Seq(
  )

  val JSEnvsTestKit = Seq(
  )

  val SbtPlugin = Seq(
      // private[sbtplugin], not an issue
      ProblemFilters.exclude[MissingMethodProblem](
          "org.scalajs.sbtplugin.FrameworkDetector.detect")
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
