package com.lunatech.undoredo.home

import japgolly.scalajs.react._
import japgolly.scalajs.react.feature.ReactFragment
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.html_<^._

import com.lunatech.undoredo.reversibleactions.views.ReversibleActionsView
import com.lunatech.undoredo.fullcopies._
import com.lunatech.undoredo.recipes._
import japgolly.scalajs.react.extra.router.RouterCtl
import com.lunatech.undoredo.routes.AppRouter

object Home {
  case class Props(
      ctl: RouterCtl[AppRouter.AppPage]
  )

  private val component = ScalaComponent
    .builder[Props]("Home")
    .render_P { props =>
      <.div(
        ^.display := "flex",
        ^.flexDirection := "column",
        props.ctl.link(AppRouter.AppPage.FullCopies)(
          "full copies"
        ),
        props.ctl.link(AppRouter.AppPage.ReversibleActions)(
          "reversible actions"
        ),
        props.ctl.link(AppRouter.AppPage.Recipes)(
          "recipes"
        )
      )
    }
    .build

  def apply(ctl: RouterCtl[AppRouter.AppPage]): VdomElement = component(Props(ctl))
}
