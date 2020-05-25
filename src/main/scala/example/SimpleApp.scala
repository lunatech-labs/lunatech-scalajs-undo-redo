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

    WholeModelCircuit.subscribe(WholeModelCircuit.zoom(identity))(_ => {
      val wholeModelElement = document.getElementById("wholeModel")
      wholeModelElement.innerHTML = ""
      wholeModelElement.appendChild( div(cls := "container", wholeModelView.render).render)
    })
    WholeModelCircuit(WholeModel.Reset)

    PartialModelCircuit.subscribe(PartialModelCircuit.zoom(identity))(_ => {
      val partialModelElement = document.getElementById("partialModel")
      partialModelElement.innerHTML = ""
      partialModelElement.appendChild( div(cls := "container", partialModelView.render).render)
    })
    PartialModelCircuit(PartialModel.Reset)
  }
}
