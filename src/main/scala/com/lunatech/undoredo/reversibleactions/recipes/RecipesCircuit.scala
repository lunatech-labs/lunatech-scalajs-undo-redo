package com.lunatech.undoredo.reversibleactions.recipes

import diode._
import diode.ActionResult.ModelUpdate
import io.circe._
import io.circe.generic.semiauto._
import com.lunatech.undoredo.reversibleactions.recipes.handlers._

case class Recipe(id: String, name: String, ingredients: Seq[String], instructions: Seq[String])

case class UndoRedoHistory(pastActions: List[ReversibleRecipeAction], futureActions: List[ReversibleRecipeAction])

case class Persistence(toAdd: Set[Recipe], toDelete: Set[Recipe])

case class RootModel(
  recipes: Set[Recipe],
  undoRedoHistory: UndoRedoHistory,
  persistence: Persistence
)

case object Reset extends Action

object Recipe {
  implicit val recipeDecoder: Decoder[Recipe] = deriveDecoder[Recipe]
  implicit val recipeEncoder: Encoder[Recipe] = deriveEncoder[Recipe]
}

object RecipesCircuit extends Circuit[RootModel] {

  val initialModel = RootModel(Set(), UndoRedoHistory(Nil, Nil), Persistence(Set(), Set()))

  override val actionHandler: HandlerFunction = composeHandlers(
    new RecipesHandler(zoomTo(_.recipes)),
    new UndoRedoHandler(zoomTo(_.undoRedoHistory)),
    new PersistenceHandler(zoomTo(_.persistence)),
    {
      case (model, Reset) => Some(ModelUpdate(initialModel))
      case _ => None
    }
  )

}
