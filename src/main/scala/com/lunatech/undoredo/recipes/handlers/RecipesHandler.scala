package com.lunatech.undoredo.recipes.handlers

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import diode.{ ActionHandler, ActionResult, Effect, ModelRW }

import com.lunatech.undoredo.recipes.models.UndoRedoHistoryActions.AddActionToPast
import com.lunatech.undoredo.recipes.models._

class RecipesHandler(modelRW: ModelRW[RootModel, Set[Recipe]]) extends ActionHandler(modelRW) {

  override def handle: PartialFunction[Any, ActionResult[RootModel]] = {
    case action: ReversibleRecipeAction =>
      updated(
        action.update(value),
        Effect.action(AddActionToPast(action))
      )
    case RecipeActions.DirectUpdate(update) =>
      updated(update)
  }
}
