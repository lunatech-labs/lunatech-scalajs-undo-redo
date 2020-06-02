package com.lunatech.undoredo.reversibleactions.recipes

import diode.ActionProcessor
import diode.ActionResult
import diode.ActionResult.NoChange
import diode.Dispatcher
import diode.ActionResult.ModelUpdate
import diode.ActionResult.ModelUpdateEffect

class RecipesPersistenceProcessor extends ActionProcessor[RootModel] {
  def process(dispatch: Dispatcher, action: Any, next: Any => ActionResult[RootModel], currentModel: RootModel): ActionResult[RootModel] = {
    val result = next(action)
    result.newModelOpt match {
      case Some(newModel) =>
        val addedRecipes = newModel.recipes.diff(currentModel.recipes)
        val deletedRecipes = currentModel.recipes.diff(newModel.recipes)
        val update = newModel.copy(
          persistence = newModel.persistence.copy(
            toAdd = newModel.persistence.toAdd ++ addedRecipes,
            toDelete = newModel.persistence.toDelete ++ deletedRecipes
          )
        )
        result.effectOpt.fold[ActionResult[RootModel]](ModelUpdate(update)) { effect =>
          ModelUpdateEffect(update, effect)
        }
      case None =>
        next(action)
    }
  }

}
