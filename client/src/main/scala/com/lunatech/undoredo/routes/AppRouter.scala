package com.lunatech.undoredo.routes

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.extra.router._

import com.lunatech.undoredo.home.{ Home => HomePage }
import com.lunatech.undoredo.fullcopies.views.FullCopiesView
import com.lunatech.undoredo.reversibleactions.views.ReversibleActionsView
import com.lunatech.undoredo.recipes.views.RecipesView

object AppRouter {
  sealed trait AppPage
  object AppPage {
    case object Home extends AppPage
    case object NotFound extends AppPage
    case object FullCopies extends AppPage
    case object ReversibleActions extends AppPage
    case object Recipes extends AppPage
  }

  val config: RouterConfig[AppPage] = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._
    val homeRoute              = staticRoute(root, AppPage.Home) ~> renderR(ctl => HomePage(ctl))
    val fullCopiesRoute        = staticRoute("#full-copies", AppPage.FullCopies) ~> render(FullCopiesView())
    val reversibleActionsRoute = staticRoute("#reversible-actions", AppPage.ReversibleActions) ~> render(ReversibleActionsView())
    val recipesRoute           = staticRoute("#recipes", AppPage.Recipes) ~> render(RecipesView())

    val notFound = staticRoute("#notfound", AppPage.NotFound) ~> render(<.h2("NOT FOUND"))
    (homeRoute | fullCopiesRoute | reversibleActionsRoute | recipesRoute).notFound { _ =>
      redirectToPage(AppPage.NotFound)(SetRouteVia.HistoryReplace)
    }
  }

  def router: Router[AppPage] =
    Router(BaseUrl.until_#, config)

}
