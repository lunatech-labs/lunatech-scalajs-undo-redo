package example

import diode._
import scalatags.JsDom.all._

class PartialModelView(world: ModelRO[PartialModel], dispatch: Dispatcher) {
  def render = {
    div(
      h3("Counter"),
      p("Value = ", b(world().counter.value)),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Increase(2))), "Increase"),
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(PartialModel.Redo)), "Redo")
      )
    )
  }
}
