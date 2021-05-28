package com.lunatech.undoredo.recipes.handlers

import diode.{ ActionHandler, ActionResult, ModelRW }

import com.lunatech.undoredo.recipes._
import com.lunatech.undoredo.recipes.models._

class PersistenceHandler(modelRW: ModelRW[RootModel, Persistence]) extends ActionHandler(modelRW) {

  override def handle: PartialFunction[Any, ActionResult[RootModel]] = {
    case PersistenceActions.AddedToPersistence(recipe) =>
      updated(
        value.copy(
          toAdd = value.toAdd.filterNot(_.id == recipe.id)
        )
      )
    case PersistenceActions.DeletedFromPersistence(recipe) =>
      updated(
        value.copy(
          toDelete = value.toDelete.filterNot(_.id == recipe.id)
        )
      )
  }

}
