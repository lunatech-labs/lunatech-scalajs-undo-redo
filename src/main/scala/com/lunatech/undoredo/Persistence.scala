package com.lunatech.undoredo

import diode._
import org.scalajs.dom.ext.Ajax
import io.circe.parser._
import io.circe.syntax._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Persistence {

  val baseUrl = "http://localhost:5000"

  def fetchRecipes(dispatch: Dispatcher): Unit = Ajax.get(s"$baseUrl/recipes").onComplete {
    case Success(xhr) =>
      decode[List[Recipe]](xhr.responseText).map {
        recipes => {
          recipes.map { recipe =>
            dispatch(RecipeActions.Add(recipe))
          }
        }
      }
    case Failure(t) => println("An error has occurred: " + t.getMessage)

  }

  def persist(recipes: Recipes, dispatch: Dispatcher): Unit = {
    recipes.toAdd.map { recipe =>
      Ajax.post(
        url = s"$baseUrl/recipes",
        data = recipe.asJson.noSpaces,
        headers = Map("Content-Type" -> "application/json")
      ).onComplete {
        case Success(xhr) =>
          if (xhr.status == 200) {
            dispatch(RecipeActions.AddedToPersistence(recipe))
          }
        case Failure(t) => println("An error has occurred: " + t.getMessage)
      }
    }
    recipes.toDelete.map { recipe =>
      Ajax.delete(
        url = s"$baseUrl/recipes/${recipe.id}"
      ).onComplete {
        case Success(xhr) =>
          if (xhr.status == 200) {
            dispatch(RecipeActions.DeletedFromPersistence(recipe))
          }
        case Failure(t) => println("An error has occurred: " + t.getMessage)
      }
    }
  }
}
