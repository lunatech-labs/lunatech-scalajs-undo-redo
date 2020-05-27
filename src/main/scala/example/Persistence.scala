package example

import diode._
import org.scalajs.dom.ext.Ajax
import io.circe.parser._
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

  def persist: Unit = ???

}
