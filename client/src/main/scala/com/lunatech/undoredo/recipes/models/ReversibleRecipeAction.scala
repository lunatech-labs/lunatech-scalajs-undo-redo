package com.lunatech.undoredo.recipes.models

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
