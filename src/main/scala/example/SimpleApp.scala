package example

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.JsDom.all._
import org.scalajs.dom._

@JSExportTopLevel("SimpleApp")
object SimpleApp extends JSApp {
  // create a view for the counter
  val wholeModelView = new WholeModelView(WholeModelCircuit.zoom(identity), WholeModelCircuit)

  @JSExport
  override def main(): Unit = {

    val root = document.getElementById("root")

    // subscribe to changes in the application model and call render when anything changes
    WholeModelCircuit.subscribe(WholeModelCircuit.zoom(identity))(_ => render(root))

    // start the application by dispatching a Reset action
    WholeModelCircuit(WholeModel.Reset)
  }

  def render(root: Element) = {
    val e = div(
      cls := "container",
      h1("Undo/Redo"),
      p(a(href := "https://github.com/lunatech-labs/lunatech-scalajs-undo-redo", "Source code")),
      div(wholeModelView.render, wholeModelView.render)
    ).render
    // clear and update contents
    root.innerHTML = ""
    root.appendChild(e)
  }
}
