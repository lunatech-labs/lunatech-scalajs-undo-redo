package com.lunatech.undoredo.fullcopies.views

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import com.lunatech.undoredo.fullcopies._
import com.lunatech.undoredo.fullcopies.models._
import com.lunatech.undoredo.fullcopies.handlers._

object FullCopiesView {
  case class Props(
      fullCopies: FullCopies
  )

  class Backend($ : BackendScope[Props, Unit]) {

    def render(props: Props) = <.div(
      <.p("Value = ", <.b(props.fullCopies.counter.value)),
      <.div(
        ^.cls := "btn-group",
        ^.role := "group",
        <.button(
          ^.`type` := "button",
          ^.cls := "btn btn-outline-secondary",
          ^.onClick --> Callback(FullCopiesCircuit.dispatch(FullCopies.Increase(2))),
          "Increase"
        ),
        <.button(
          ^.`type` := "button",
          ^.cls := "btn btn-outline-secondary",
          ^.onClick --> Callback(FullCopiesCircuit.dispatch(FullCopies.Reset)),
          "Reset"
        ),
        <.button(
          ^.`type` := "button",
          ^.cls := "btn btn-outline-secondary",
          ^.onClick --> Callback(FullCopiesCircuit.dispatch(FullCopies.Undo)),
          "Undo"
        ),
        <.button(
          ^.`type` := "button",
          ^.cls := "btn btn-outline-secondary",
          ^.onClick --> Callback(FullCopiesCircuit.dispatch(FullCopies.Redo)),
          "Redo"
        )
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props]("FullCopiesView")
    .renderBackend[Backend]
    .build

  def apply(): VdomElement = FullCopiesCircuit.connectFullCopies { fullCopies =>
    component(Props(fullCopies()))
  }
}
