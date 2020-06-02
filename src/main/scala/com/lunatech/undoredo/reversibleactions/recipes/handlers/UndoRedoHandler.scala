package com.lunatech.undoredo.reversibleactions.recipes.handlers

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import diode.ActionHandler
import diode.ModelRW
import diode.ActionResult
import diode.Effect

import com.lunatech.undoredo.reversibleactions.recipes._

class UndoRedoHandler(
  modelRW: ModelRW[RootModel, UndoRedoHistory]
) extends ActionHandler(modelRW) {

  override def handle: PartialFunction[Any,ActionResult[RootModel]] = {
    case UndoRedoHistoryActions.Undo =>
      value.pastActions.headOption match {
        case Some(actionToUndo) => 
          updated(
            value.copy(
              pastActions = value.pastActions.tail,
              futureActions = actionToUndo +: value.futureActions
            ),
            Effect.action(RecipeActions.DirectUpdate(actionToUndo.undo(modelRW.root.value.recipes)))
          )
        case None => noChange
      }
    
    case UndoRedoHistoryActions.Redo =>
      value.futureActions.headOption match {
        case Some(actionToRedo) => 
        updated(
          value.copy(
            pastActions = actionToRedo +: value.pastActions,
            futureActions = value.futureActions.tail
          ),
          Effect.action(RecipeActions.DirectUpdate(actionToRedo.update(modelRW.root.value.recipes)))
        )
        case None => noChange
      }

    case UndoRedoHistoryActions.AddActionToPast(action) =>
      updated(value.copy(
        pastActions = action +: value.pastActions
      ))
  }

  
}
