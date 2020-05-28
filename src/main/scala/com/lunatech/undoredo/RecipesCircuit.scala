package com.lunatech.undoredo

import diode._
import diode.ActionResult.ModelUpdate
import io.circe._
import io.circe.generic.semiauto._

case class Recipe(id: String, name: String, ingredients: Seq[String], instructions: Seq[String])

case class Recipes(
  recipes: Set[Recipe],
  pastActions: List[ReversibleRecipeAction],
  futureActions: List[ReversibleRecipeAction],
  toAdd: Set[Recipe],
  toDelete: Set[Recipe]
)

object Recipe {
  implicit val recipeDecoder: Decoder[Recipe] = deriveDecoder[Recipe]
  implicit val recipeEncoder: Encoder[Recipe] = deriveEncoder[Recipe]
}

trait ReversibleRecipeAction extends Action {
  def update(model: Recipes): Recipes
  def undo(model: Recipes): Recipes
}

object RecipeActions {

  case class Add(recipe: Recipe) extends ReversibleRecipeAction {

    def update(model: Recipes): Recipes = model.copy(
      recipes = model.recipes + recipe,
      toAdd = model.toAdd + recipe
    )

    def undo(model: Recipes): Recipes =
      if (model.toAdd.contains(recipe)) {
        model.copy(
          recipes = model.recipes.filterNot(_.id == recipe.id),
          toAdd = model.toAdd.filterNot(_.id == recipe.id)
        )
      } else {
        model.copy(
          recipes = model.recipes.filterNot(_.id == recipe.id),
          toDelete = model.toDelete + recipe
        )
      }
  }

  case class Delete(recipe: Recipe) extends ReversibleRecipeAction {
    def update(model: Recipes): Recipes = model.copy(
      recipes = model.recipes.filterNot(_.id == recipe.id),
      toDelete = model.toDelete + recipe
    )

    def undo(model: Recipes): Recipes =
      if (model.toDelete.contains(recipe)) {
        model.copy(
          recipes = model.recipes + recipe,
          toDelete = model.toDelete.filterNot(_.id == recipe.id)
        )
      } else {
        model.copy(
          recipes = model.recipes + recipe,
          toAdd = model.toAdd + recipe
        )
      }
  }

  case object Undo extends Action
  case object Redo extends Action
  case object Reset extends Action

  case class AddedToPersistence(recipe: Recipe) extends Action
  case class DeletedFromPersistence(recipe: Recipe) extends Action

}

object RecipesCircuit extends Circuit[Recipes] {

  val initialModel = Recipes(Set(), Nil, Nil, Set(), Set())

  override val actionHandler: HandlerFunction =
    (model, action) => action match {

      case RecipeActions.Reset => Some(ModelUpdate(initialModel))

      case RecipeActions.Undo => {
        val actionToUndo = model.pastActions.head
        val newModel = actionToUndo.undo(model).copy(
          pastActions = model.pastActions.tail,
          futureActions = actionToUndo +: model.futureActions
        )
        Some(ModelUpdate(newModel))
      }

      case RecipeActions.Redo => {
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

      case RecipeActions.AddedToPersistence(recipe) => {
        val newModel = model.copy(toAdd = model.toAdd.filterNot(_.id == recipe.id))
        Some(ModelUpdate(newModel))
      }

      case RecipeActions.DeletedFromPersistence(recipe) => {
        val newModel = model.copy(toDelete = model.toDelete.filterNot(_.id == recipe.id))
        Some(ModelUpdate(newModel))
      }

      case _ => None
    }
}
