package com.lunatech.undoredo.recipes.models

import diode.Action

import com.lunatech.undoredo.shared.model.Recipe

case class RootModel(
    recipes: Set[Recipe],
    undoRedoHistory: UndoRedoHistory,
    persistence: Persistence
)

case class UndoRedoHistory(pastActions: List[ReversibleRecipeAction], futureActions: List[ReversibleRecipeAction])

case class Persistence(toAdd: Set[Recipe], toDelete: Set[Recipe])

case object Reset extends Action

object UndoRedoHistoryActions {
  case object Undo extends Action
  case object Redo extends Action

  case class AddActionToPast(action: ReversibleRecipeAction) extends Action

}

object PersistenceActions {
  case class AddedToPersistence(recipe: Recipe) extends Action
  case class DeletedFromPersistence(recipe: Recipe) extends Action

}
