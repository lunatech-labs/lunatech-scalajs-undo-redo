package com.lunatech.undoredo.reversibleactions.recipes

import diode.Action

trait ReversibleRecipeAction extends Action {
  def update(model: Set[Recipe]): Set[Recipe]
  def undo(model: Set[Recipe]): Set[Recipe]
}

object RecipeActions {
  
  case class DirectUpdate(recipes: Set[Recipe]) extends Action
  
  case class Add(recipe: Recipe) extends ReversibleRecipeAction {

    def update(recipes: Set[Recipe]): Set[Recipe] =
      recipes + recipe

    def undo(recipes: Set[Recipe]): Set[Recipe] =
      recipes.filterNot(_.id == recipe.id)
  }

  case class Delete(recipe: Recipe) extends ReversibleRecipeAction {
    def update(recipes: Set[Recipe]): Set[Recipe] =
      recipes.filterNot(_.id == recipe.id)

    def undo(recipes: Set[Recipe]): Set[Recipe] =
      recipes + recipe
  }
  
}

object UndoRedoHistoryActions {
  case object Undo extends Action
  case object Redo extends Action
  
  case class AddActionToPast(action: ReversibleRecipeAction) extends Action
  
}

object PersistenceActions {
  case class AddedToPersistence(recipe: Recipe) extends Action
  case class DeletedFromPersistence(recipe: Recipe) extends Action

}
