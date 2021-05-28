package com.lunatech.undoredo.reversibleactions.views

import diode._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import com.lunatech.undoredo.reversibleactions.models._
import com.lunatech.undoredo.reversibleactions.handlers.ReversibleActionsCircuit

object ReversibleActionsView {
  case class Props(
      reversibleActions: ReversibleActions
  )

  private val component = ScalaComponent
    .builder[Props]("reversibleActionsView")
    .render_P { props =>
      <.div(
        <.p(
          "Value = ",
          <.b(props.reversibleActions.counter.value)
        ),
        <.div(
          ^.cls := "btn-group",
          ^.role := "group",
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(ReversibleActionsCircuit.dispatch(ReversibleActions.Increase(3))),
            "Increase"
          ),
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(ReversibleActionsCircuit.dispatch(ReversibleActions.Reset)),
            "Reset"
          ),
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(ReversibleActionsCircuit.dispatch(ReversibleActions.Undo)),
            "Undo"
          ),
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(ReversibleActionsCircuit.dispatch(ReversibleActions.Redo)),
            "Redo"
          )
        )
      )
    }
    .build

  def apply() = ReversibleActionsCircuit.connectReversibleActions { reversibleActions =>
    component(Props(reversibleActions()))
  }
}
