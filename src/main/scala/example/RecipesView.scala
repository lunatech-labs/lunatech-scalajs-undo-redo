package example

import diode._
import scalatags.JsDom.all._

class RecipesView(recipes: ModelRO[Recipes], dispatch: Dispatcher) {

  def render = {
    div(
      h3("Cookbook"),
      recipes().recipes.map { recipe => 
        p("Recipe: ", b(recipe.name)),
      },
      div(
        cls := "btn-group",
//        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Add(3))), "Increase"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleRecipeActions.Redo)), "Redo")
      )
    )
  }
}
