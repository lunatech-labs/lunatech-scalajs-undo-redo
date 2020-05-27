package example

import diode._
import org.scalajs.dom.ext.Ajax
import io.circe.parser._
import io.circe.syntax._
import scala.concurrent.ExecutionContext.Implicits.global

object Persistence {

  val baseUrl = "http://localhost:5000"

  def fetchRecipes(dispatch: Dispatcher): Unit = Ajax.get(s"$baseUrl/recipes").onSuccess {
    case xhr =>
      decode[List[Recipe]](xhr.responseText).map {
        recipes => {
          recipes.map { recipe =>
            dispatch(RecipeActions.Add(recipe))
          }
        }
      }
  }

  def persist(recipes: Recipes, dispatch: Dispatcher): Unit = recipes.toAdd.map { recipe =>
    print("toto")
    Ajax.post(
      url = s"$baseUrl/recipes",
      data = recipe.asJson.noSpaces,
      headers = Map("Content-Type" -> "application/json")
    ).onSuccess {
      case xhr =>
        if (xhr.status == 200) {
          dispatch(RecipeActions.AddedToPersistence(recipe))
        }
    }
  }
}
