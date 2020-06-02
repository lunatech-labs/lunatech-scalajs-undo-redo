package com.lunatech.undoredo.reversibleactions.recipes.handlers

import diode.ActionHandler
import diode.ActionResult
import diode.ModelRW

import com.lunatech.undoredo.reversibleactions.recipes.Persistence
import com.lunatech.undoredo.reversibleactions.recipes.PersistenceActions
import com.lunatech.undoredo.reversibleactions.recipes.RootModel

class PersistenceHandler(modelRW: ModelRW[RootModel, Persistence]) extends ActionHandler(modelRW) {

  override def handle: PartialFunction[Any,ActionResult[RootModel]] = {
    case PersistenceActions.AddedToPersistence(recipe) =>
      updated(value.copy(
        toAdd = value.toAdd.filterNot(_.id == recipe.id)
      ))
    case PersistenceActions.DeletedFromPersistence(recipe) =>
      updated(
        value.copy(
          toDelete = value.toDelete.filterNot(_.id == recipe.id)
        )
      )
  }

  
}
