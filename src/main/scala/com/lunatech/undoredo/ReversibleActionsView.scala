package com.lunatech.undoredo

import diode._
import scalatags.JsDom.all._

class ReversibleActionsView(partialModel: ModelRO[ReversibleActions], dispatch: Dispatcher) {
  def render = {
    div(
      h3("Reversible Actions"),
      p("Value = ", b(partialModel().counter.value)),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleActions.Increase(3))), "Increase"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleActions.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleActions.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(ReversibleActions.Redo)), "Redo")
      )
    )
  }
}
