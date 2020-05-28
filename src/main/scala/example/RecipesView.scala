package example

import diode._
import scalatags.JsDom.all._
import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.{Ajax, KeyCode}
import org.scalajs.dom.raw.Event
import scala.scalajs.js
import io.circe.parser._
import io.circe.syntax._
import io.circe.{ Decoder, Encoder }
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

class RecipesView(recipes: ModelRO[Recipes], dispatch: Dispatcher) {

  val couscous = Recipe("couscous", "Couscous", Seq("ig12", "ig22"), Seq("is12", "is22"))
  val cremeBrulee = Recipe("creme-brulee", "Creme Brulee", Seq("ig13", "ig23"), Seq("is13", "is23"))

  js.timers.setInterval(5000) {
    Persistence.persist(recipes(), dispatch)
  }

  def render = {
    div(
      h3("Cookbook"),
      p("Recipes: "),
      ul(recipes().recipes.toSeq.map { recipe => li(
        span(recipe.name),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Delete(recipe))), "Delete"),
      )}),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Add(couscous))), "Add Couscous"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Add(cremeBrulee))), "Add Creme Brulee")
      ),
      p(),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Redo)), "Redo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Reset)), "Reset")
      ),
      p(),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => Persistence.fetchRecipes(dispatch)), "Fetch"),
        button(cls := "btn btn-default", onclick := (() => Persistence.persist(recipes(), dispatch)), "Persist")
      )
    )
  }
}
