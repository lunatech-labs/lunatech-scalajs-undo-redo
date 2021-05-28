package com.lunatech.undoredo

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom._
import japgolly.scalajs.react.vdom.html_<^._

import com.lunatech.undoredo.recipes.handlers.{ RecipesCircuit, RecipesPersistenceProcessor }
import com.lunatech.undoredo.routes.AppRouter
import com.lunatech.undoredo.fullcopies.handlers.FullCopiesCircuit
import com.lunatech.undoredo.fullcopies.models.FullCopies
import com.lunatech.undoredo.recipes.models.Reset
import com.lunatech.undoredo.reversibleactions.handlers.ReversibleActionsCircuit
import com.lunatech.undoredo.reversibleactions.models.ReversibleActions

object SimpleApp {

  @JSExport
  def main(args: Array[String]): Unit = {
    FullCopiesCircuit(FullCopies.Reset)
    RecipesCircuit(Reset)
    RecipesCircuit.addProcessor(new RecipesPersistenceProcessor())
    ReversibleActionsCircuit(ReversibleActions.Reset)

    val app = dom.document.getElementById("app")
    AppRouter.router().renderIntoDOM(app)
  }
}
