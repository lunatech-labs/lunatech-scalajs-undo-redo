package com.lunatech.undoredo.reversibleactions.recipes.handlers

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import diode.ActionHandler
import diode.ActionResult
import diode.Effect
import diode.ModelRW

import com.lunatech.undoredo.reversibleactions.recipes._
import com.lunatech.undoredo.reversibleactions.recipes.UndoRedoHistoryActions.AddActionToPast

class RecipesHandler(modelRW: ModelRW[RootModel, Set[Recipe]]) extends ActionHandler(modelRW){

  override def handle: PartialFunction[Any,ActionResult[RootModel]] = {
    case action: ReversibleRecipeAction =>
      val newModel = action.update(value)
      updated(newModel, Effect.action(AddActionToPast(action)))
    case RecipeActions.DirectUpdate(update) =>
      updated(update)
  }
}
