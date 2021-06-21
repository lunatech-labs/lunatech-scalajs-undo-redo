package com.lunatech.undoredo.recipes

import diode._
import org.scalajs.dom.ext.Ajax
import io.circe.parser._
import io.circe.syntax._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }

import com.lunatech.undoredo.recipes.models._
import com.lunatech.undoredo.shared.model

object Client {

  def fetchRecipes(dispatch: Dispatcher): Unit = Ajax.get("/recipes").onComplete {
    case Success(xhr) =>
      decode[List[model.Recipe]](xhr.responseText).map { recipes =>
        recipes.map { recipe =>
          dispatch(RecipeActions.Add(recipe))
        }
      }
    case Failure(t) => println("An error has occurred: " + t.getMessage)

  }

  def persist(persistence: Persistence, dispatch: Dispatcher): Unit = {
    persistence.toAdd.map { recipe =>
      Ajax
        .post(
          url     = "/recipes",
          data    = recipe.asJson.noSpaces,
          headers = Map("Content-Type" -> "application/json")
        )
        .onComplete {
          case Success(xhr) =>
            if (xhr.status == 200) {
              dispatch(PersistenceActions.AddedToPersistence(recipe))
            }
          case Failure(t) => println("An error has occurred: " + t.getMessage)
        }
    }
    persistence.toDelete.map { recipe =>
      Ajax
        .delete(
          url = s"/recipes/${recipe.id}"
        )
        .onComplete {
          case Success(xhr) =>
            if (xhr.status == 200) {
              dispatch(PersistenceActions.DeletedFromPersistence(recipe))
            }
          case Failure(t) => println("An error has occurred: " + t.getMessage)
        }
    }
  }
}
