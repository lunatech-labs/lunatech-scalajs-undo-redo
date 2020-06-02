package com.lunatech.undoredo.example1

import diode._
import diode.ActionResult.ModelUpdate
import scala.math.{ max, min }

case class Counter(value: Int)

trait ReversibleAction extends Action {
  def update(model: ReversibleActions): ReversibleActions
  def undo(model: ReversibleActions): ReversibleActions
}

case class ReversibleActions(counter: Counter, pastActions: List[ReversibleAction], futureActions: List[ReversibleAction])

object ReversibleActions {

  case class Increase(amount: Int) extends ReversibleAction {
    def update(model: ReversibleActions): ReversibleActions = model.copy(counter = Counter(model.counter.value + amount))
    def undo(model: ReversibleActions): ReversibleActions = model.copy(counter = Counter(model.counter.value - amount))
  }

  case class Decrease(amount: Int) extends ReversibleAction {
    def update(model: ReversibleActions): ReversibleActions = model.copy(counter = Counter(model.counter.value - amount))
    def undo(model: ReversibleActions): ReversibleActions = model.copy(counter = Counter(model.counter.value + amount))
  }

  case object Reset extends Action
  case object Undo extends Action
  case object Redo extends Action
}

object ReversibleActionsCircuit extends Circuit[ReversibleActions] {

  val initialModel = ReversibleActions(Counter(0), Nil, Nil)

  override val actionHandler: HandlerFunction =
    (model, action) => action match {

      case ReversibleActions.Reset => Some(ModelUpdate(initialModel))

      case ReversibleActions.Undo => {
        val actionToUndo = model.pastActions.head
        val newModel = actionToUndo.undo(model).copy(
          pastActions = model.pastActions.tail,
          futureActions = actionToUndo +: model.futureActions
        )
        Some(ModelUpdate(newModel))
      }

      case ReversibleActions.Redo => {
        val actionToRedo = model.futureActions.head
        val newModel = actionToRedo.update(model).copy(
          pastActions = actionToRedo +: model.pastActions,
          futureActions = model.futureActions.tail
        )
        Some(ModelUpdate(newModel))
      }

      case action: ReversibleAction => {
        val newModel = action.update(model).copy(
          pastActions = action +: model.pastActions
        )
        Some(ModelUpdate(newModel))
      }

      case _ => None
    }
}
