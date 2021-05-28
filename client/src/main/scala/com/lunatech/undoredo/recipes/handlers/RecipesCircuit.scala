package com.lunatech.undoredo.recipes.handlers

import diode._
import diode.react.{ ReactConnectProxy, ReactConnector }
import diode.ActionResult.ModelUpdate

import com.lunatech.undoredo.recipes.models._

object RecipesCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {

  val initialModel = RootModel(
    Set(),
    UndoRedoHistory(Nil, Nil),
    Persistence(Set(), Set())
  )

  override val actionHandler: HandlerFunction = composeHandlers(
    new RecipesHandler(zoomTo(_.recipes)),
    new UndoRedoHandler(zoomTo(_.undoRedoHistory)),
    new PersistenceHandler(zoomTo(_.persistence)),
    {
      case (model, Reset) => Some(ModelUpdate(initialModel))
      case _ => None
    }
  )

  val connectRootModel: ReactConnectProxy[RootModel] = RecipesCircuit.connect(identity(_))

}
