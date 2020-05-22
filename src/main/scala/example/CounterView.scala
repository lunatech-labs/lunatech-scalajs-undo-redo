package example

import diode._
import scalatags.JsDom.all._

/**
  * Counter view renders the counter value and provides interaction through
  * various buttons affecting the counter value.
  *
  * @param counter  Model reader for the counter value
  * @param dispatch Dispatcher
  */
class CounterView(world: ModelRO[World], dispatch: Dispatcher) {
  def render = {
    div(
      h3("Counter"),
      p("Value = ", b(world().counter.value)),
      div(
        cls := "btn-group",
        button(cls := "btn btn-default", onclick := (() => dispatch(Increase(2))), "Increase"),
        button(cls := "btn btn-default", onclick := (() => dispatch(Reset)), "Reset"),
        button(cls := "btn btn-default", onclick := (() => dispatch(Undo)), "Undo"),
        button(cls := "btn btn-default", onclick := (() => dispatch(Redo)), "Redo")
      ),
      p("Position: ", b(world().position))
    )
  }
}
