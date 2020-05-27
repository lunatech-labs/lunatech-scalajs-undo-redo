package example

import diode._
import scalatags.JsDom.all._
import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.{Ajax, KeyCode}
import org.scalajs.dom.raw.Event
import io.circe.parser._
import io.circe.syntax._
import io.circe.{ Decoder, Encoder }
import Recipes._
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

class RecipesView(recipes: ModelRO[Recipes], dispatch: Dispatcher) {

  val r2 = Recipe("r2", "Couscous", Seq("ig12", "ig22"), Seq("is12", "is22"))
  val r3 = Recipe("r3", "Creme Brulee", Seq("ig13", "ig23"), Seq("is13", "is23"))

  def fetchRecipes = Ajax.get("http://localhost:5000/recipes").onSuccess {
    case xhr =>
      decode[List[Recipe]](xhr.responseText).map {
        recipes => {
          recipes.map { recipe =>
            dispatch(RecipeActions.Add(recipe))
          }
        }
      }
  }

  def render = {
    div(
      h3("Cookbook"),
      p("Recipes: "),
      ul(recipes().recipes.map { recipe => li(
        span(recipe.name),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Delete(recipe))), "Delete"),
      )}),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => fetchRecipes), "Fetch"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Add(r2))), "Add Couscous"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Add(r3))), "Add Creme Brulee"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(RecipeActions.Redo)), "Redo")
      )
    )
  }
}
