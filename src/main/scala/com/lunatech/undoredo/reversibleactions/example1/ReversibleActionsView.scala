package com.lunatech.undoredo.example1

import diode._
import scalatags.JsDom.all._

class ReversibleActionsView(partialModel: ModelRO[ReversibleActions], dispatch: Dispatcher) {
  def render = {
    div(
      p("Value = ", b(partialModel().counter.value)),
      div(
        cls := "btn-group",
        role := "group",
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(ReversibleActions.Increase(3))), "Increase"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(ReversibleActions.Reset)), "Reset"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(ReversibleActions.Undo)), "Undo"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(ReversibleActions.Redo)), "Redo")
      )
    )
  }
}
