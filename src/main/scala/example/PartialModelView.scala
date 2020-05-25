package example

import diode._
import scalatags.JsDom.all._

class PartialModelView(partialModel: ModelRO[PartialModel], dispatch: Dispatcher) {
  def render = {
    div(
      h3("Partial Model"),
      p("Value = ", b(partialModel().counter.value)),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Increase(3))), "Increase"),
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Redo)), "Redo")
      )
    )
  }
}
