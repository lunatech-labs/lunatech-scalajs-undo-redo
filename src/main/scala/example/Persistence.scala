package example

import diode._
import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.{Ajax, KeyCode}
import org.scalajs.dom.raw.Event
import io.circe.parser._
import io.circe.syntax._
import io.circe.{ Decoder, Encoder }
import scala.concurrent.ExecutionContext
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
