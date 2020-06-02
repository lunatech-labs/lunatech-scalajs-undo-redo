package com.lunatech.undoredo.fullcopies

import diode._
import scalatags.JsDom.all._

class FullCopiesView(wholeModel: ModelRO[FullCopies], dispatch: Dispatcher) {
  def render = {
    div(
      p("Value = ", b(wholeModel().counter.value)),
      div(
        cls := "btn-group",
        role := "group",
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(FullCopies.Increase(2))), "Increase"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(FullCopies.Reset)), "Reset"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(FullCopies.Undo)), "Undo"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(FullCopies.Redo)), "Redo")
      ),
    )
  }
}
