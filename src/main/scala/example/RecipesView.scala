package example

import diode._
import scalatags.JsDom.all._

class RecipesView(recipes: ModelRO[Recipes], dispatch: Dispatcher) {

  val r2 = Recipe("r2", "n2", Seq("ig12", "ig22"), Seq("is12", "is22"))
  val r3 = Recipe("r3", "n3", Seq("ig13", "ig23"), Seq("is13", "is23"))

  def render = {
    div(
      h3("Cookbook"),
      p("Recipes: "),
      ul(recipes().recipes.map { recipe => li(recipe.name) }),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Add(r2))), "Add r2"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Add(r3))), "Add r3"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Redo)), "Redo")
      )
    )
  }
}
