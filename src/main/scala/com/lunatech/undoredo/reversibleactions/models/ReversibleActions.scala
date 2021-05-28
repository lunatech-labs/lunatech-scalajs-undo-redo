package com.lunatech.undoredo.reversibleactions.models

import diode.Action
case class ReversibleActions(counter: Counter, pastActions: List[ReversibleAction], futureActions: List[ReversibleAction])

object ReversibleActions {

  case class Increase(amount: Int) extends ReversibleAction {
    def update(model: ReversibleActions): ReversibleActions = model.copy(counter = Counter(model.counter.value + amount))
    def undo(model: ReversibleActions): ReversibleActions   = model.copy(counter = Counter(model.counter.value - amount))
  }

  case class Decrease(amount: Int) extends ReversibleAction {
    def update(model: ReversibleActions): ReversibleActions = model.copy(counter = Counter(model.counter.value - amount))
    def undo(model: ReversibleActions): ReversibleActions   = model.copy(counter = Counter(model.counter.value + amount))
  }

  case object Reset extends Action
  case object Undo extends Action
  case object Redo extends Action
}

case class Counter(value: Int)

trait ReversibleAction extends Action {
  def update(model: ReversibleActions): ReversibleActions
  def undo(model: ReversibleActions): ReversibleActions
}
