package example

import diode._
import scalatags.JsDom.all._

class WholeModelView(wholeModel: ModelRO[WholeModel], dispatch: Dispatcher) {
  def render = {
    div(
      h3("Counter"),
      p("Value = ", b(wholeModel().counter.value)),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(WholeModel.Increase(2))), "Increase"),
        button(cls := "btn btn-default", onclick := (() => dispatch(WholeModel.Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(WholeModel.Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(WholeModel.Redo)), "Redo")
      ),
    )
  }
}
