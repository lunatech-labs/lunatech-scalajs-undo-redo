package example

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.JsDom.all._
import org.scalajs.dom._

@JSExportTopLevel("SimpleApp")
object SimpleApp extends JSApp {

  val fullCopiesView = new FullCopiesView(FullCopiesCircuit.zoom(identity), FullCopiesCircuit)
  val reversibleActionsView = new ReversibleActionsView(ReversibleActionsCircuit.zoom(identity), ReversibleActionsCircuit)
  val recipesView = new RecipesView(RecipesCircuit.zoom(identity), RecipesCircuit)

  @JSExport
  override def main(): Unit = {

    FullCopiesCircuit.subscribe(FullCopiesCircuit.zoom(identity))(_ => {
      val element = document.getElementById("fullCopies")
      element.innerHTML = ""
      element.appendChild( div(cls := "container", fullCopiesView.render).render)
    })
    FullCopiesCircuit(FullCopies.Reset)

    ReversibleActionsCircuit.subscribe(ReversibleActionsCircuit.zoom(identity))(_ => {
      val element = document.getElementById("reversibleActions")
      element.innerHTML = ""
      element.appendChild( div(cls := "container", reversibleActionsView.render).render)
    })
    ReversibleActionsCircuit(ReversibleActions.Reset)

    RecipesCircuit.subscribe(RecipesCircuit.zoom(identity))(_ => {
      val element = document.getElementById("recipes")
      element.innerHTML = ""
      element.appendChild( div(cls := "container", recipesView.render).render)
    })
    RecipesCircuit(ReversibleRecipeActions.Reset)
  }
}
