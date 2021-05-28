package com.lunatech.undoredo.reversibleactions.handlers

import diode._
import diode.ActionResult.ModelUpdate
import diode.react.{ ReactConnectProxy, ReactConnector }
import com.lunatech.undoredo.reversibleactions.models._

object ReversibleActionsCircuit extends Circuit[ReversibleActions] with ReactConnector[ReversibleActions] {

  val initialModel = ReversibleActions(Counter(0), Nil, Nil)

  override val actionHandler: HandlerFunction = (model, action) =>
    action match {
      case ReversibleActions.Reset => Some(ModelUpdate(initialModel))
      case ReversibleActions.Undo =>
        val actionToUndo = model.pastActions.headOption
        val newModel = actionToUndo.map { action =>
          action
            .undo(model)
            .copy(
              pastActions   = model.pastActions.tail,
              futureActions = action +: model.futureActions
            )
        }
        newModel.map(ModelUpdate(_)).orElse(Some(ActionResult.NoChange))

      case ReversibleActions.Redo =>
        val actionToRedo = model.futureActions.headOption
        val newModel = actionToRedo.map { action =>
          action
            .update(model)
            .copy(
              pastActions   = action +: model.pastActions,
              futureActions = model.futureActions.tail
            )
        }
        newModel.map(ModelUpdate(_)).orElse(Some(ActionResult.NoChange))

      case action: ReversibleAction =>
        val newModel = action
          .update(model)
          .copy(
            pastActions = action +: model.pastActions
          )
        Some(ModelUpdate(newModel))

      case _ => None
    }

  val connectReversibleActions: ReactConnectProxy[ReversibleActions] = ReversibleActionsCircuit.connect(identity(_))
}
