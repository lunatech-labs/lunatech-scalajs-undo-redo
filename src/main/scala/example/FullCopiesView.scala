package example

import diode._
import scalatags.JsDom.all._

class FullCopiesView(wholeModel: ModelRO[FullCopies], dispatch: Dispatcher) {
  def render = {
    div(
      h3("Full Copies"),
      p("Value = ", b(wholeModel().counter.value)),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(FullCopies.Increase(2))), "Increase"),
        button(cls := "btn btn-default", onclick := (() => dispatch(FullCopies.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(FullCopies.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(FullCopies.Redo)), "Redo")
      ),
    )
  }
}
