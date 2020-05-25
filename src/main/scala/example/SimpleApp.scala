package example

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.JsDom.all._
import org.scalajs.dom._

@JSExportTopLevel("SimpleApp")
object SimpleApp extends JSApp {

  val wholeModelView = new WholeModelView(WholeModelCircuit.zoom(identity), WholeModelCircuit)
  val partialModelView = new PartialModelView(PartialModelCircuit.zoom(identity), PartialModelCircuit)

  @JSExport
  override def main(): Unit = {

    WholeModelCircuit.subscribe(WholeModelCircuit.zoom(identity))(_ => renderWholeModel)
    PartialModelCircuit.subscribe(PartialModelCircuit.zoom(identity))(_ => renderPartialModel)

    WholeModelCircuit(WholeModel.Reset)
    PartialModelCircuit(PartialModel.Reset)
  }

  def renderWholeModel = {
    val wholeModelElement = document.getElementById("wholeModel")
    wholeModelElement.innerHTML = ""
    wholeModelElement.appendChild( div(cls := "container", wholeModelView.render).render)
  }

  def renderPartialModel = {
    val partialModelElement = document.getElementById("partialModel")
    partialModelElement.innerHTML = ""
    partialModelElement.appendChild( div(cls := "container", partialModelView.render).render)
  }

  def render = {
    val top = div(
      cls := "container",
      h1("Undo/Redo"),
      p(a(href := "https://github.com/lunatech-labs/lunatech-scalajs-undo-redo", "Source code"))
    )

    val root = document.getElementById("root")

    root.innerHTML = ""
    root.appendChild(top.render)
  }
}
