package example

import diode._
import diode.ActionResult.ModelUpdate

case class Recipe(id: String, name: String, ingredients: Seq[String], instructions: Seq[String])

case class Recipes(recipes: Seq[Recipe], pastActions: List[ReversibleRecipeAction], futureActions: List[ReversibleRecipeAction])

trait ReversibleRecipeAction extends Action {
  def update(model: Recipes): Recipes
  def undo(model: Recipes): Recipes
}

object ReversibleRecipeActions {

  case class Add(recipe: Recipe) extends ReversibleRecipeAction {
    def update(model: Recipes): Recipes = model.copy(recipes = recipe +: model.recipes)
    def undo(model: Recipes): Recipes = model.copy(recipes = model.recipes.filterNot(_.id == recipe.id))
  }

  case class Delete(recipe: Recipe) extends ReversibleRecipeAction {
    def update(model: Recipes): Recipes = model.copy(recipes = model.recipes.filterNot(_.id == recipe.id))
    def undo(model: Recipes): Recipes = model.copy(recipes = recipe +: model.recipes)
  }

  case object Undo extends Action
  case object Redo extends Action
  case object Reset extends Action
}

object RecipesCircuit extends Circuit[Recipes] {

  //val initialModel = Recipes(Nil, Nil, Nil)
  val initialRecipe = Recipe("r1", "n1", Seq("ig1", "ig2"), Seq("is1", "is2"))
  val initialModel = Recipes(Seq(initialRecipe), Nil, Nil)

  override val actionHandler: HandlerFunction =
    (model, action) => action match {

      case ReversibleRecipeActions.Reset => Some(ModelUpdate(initialModel))

      case ReversibleRecipeActions.Undo => {
        val actionToUndo = model.pastActions.head
        val newModel = actionToUndo.undo(model).copy(
          pastActions = model.pastActions.tail,
          futureActions = actionToUndo +: model.futureActions
        )
        Some(ModelUpdate(newModel))
      }

      case ReversibleRecipeActions.Redo => {
        val actionToRedo = model.futureActions.head
        val newModel = actionToRedo.update(model).copy(
          pastActions = actionToRedo +: model.pastActions,
          futureActions = model.futureActions.tail
        )
        Some(ModelUpdate(newModel))
      }

      case action: ReversibleRecipeAction => {
        val newModel = action.update(model).copy(
          pastActions = action +: model.pastActions
        )
        Some(ModelUpdate(newModel))
      }

      case _ => None
    }
}
