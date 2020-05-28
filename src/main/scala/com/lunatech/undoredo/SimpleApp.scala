package com.lunatech.undoredo

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.JsDom.all._
import org.scalajs.dom._

@JSExportTopLevel("SimpleApp")
object SimpleApp extends JSApp {

  val fullCopiesView = new FullCopiesView(FullCopiesCircuit.zoom(identity), FullCopiesCircuit)
  val reversibleActionsView = new ReversibleActionsView(ReversibleActionsCircuit.zoom(identity), ReversibleActionsCircuit)
  val recipesView = new RecipesView(RecipesCircuit.zoom(identity), RecipesCircuit)


  def fullCopies(): Unit = {
    FullCopiesCircuit.subscribe(FullCopiesCircuit.zoom(identity))(_ => {
      val element = document.getElementById("fullCopies")
      element.innerHTML = ""
      element.appendChild(div(cls := "container", fullCopiesView.render).render)
    })
    FullCopiesCircuit(FullCopies.Reset)
  }

  def reversibleActions(): Unit = {
    ReversibleActionsCircuit.subscribe(ReversibleActionsCircuit.zoom(identity))(_ => {
      val element = document.getElementById("reversibleActions")
      element.innerHTML = ""
      element.appendChild(div(cls := "container", reversibleActionsView.render).render)
    })
    ReversibleActionsCircuit(ReversibleActions.Reset)
  }

  def recipes(): Unit = {
    RecipesCircuit.subscribe(RecipesCircuit.zoom(identity))(_ => {
      val element = document.getElementById("recipes")
      element.innerHTML = ""
      element.appendChild(div(cls := "container", recipesView.render).render)
    })
    RecipesCircuit(RecipeActions.Reset)
  }

  @JSExport
  override def main(): Unit = {

    //fullCopies()
    //reversibleActions()
    recipes()
  }
}
