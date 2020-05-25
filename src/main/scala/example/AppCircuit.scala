package example

import diode._
import diode.ActionResult.ModelUpdate
import scala.math.{ max, min }

case class RootModel(wholeModel: WholeModel)

object AppCircuit extends Circuit[RootModel] {

  val emptyWholeModel = WholeModel(Counter(0), Map(0 -> Counter(0)), 0)
  def initialModel = RootModel(emptyWholeModel)

  override val actionHandler: HandlerFunction =
    (model, action) => action match {

      case WholeModel.Increase(a) => {
        val wholeModel = model.wholeModel
        val increasedCounter = Counter(wholeModel.counter.value + a)
        val increasedHistory = wholeModel.history + ((wholeModel.position + 1) -> increasedCounter)
        val increasedWholeModel = model.wholeModel.copy(counter = increasedCounter, history = increasedHistory, position = wholeModel.position + 1)
        Some(ModelUpdate(model.copy(wholeModel = increasedWholeModel)))
      }

      case WholeModel.Reset => Some(ModelUpdate(model.copy(wholeModel = emptyWholeModel)))

      case WholeModel.Undo => {
        val wholeModel = model.wholeModel
        val newPosition = max(wholeModel.position - 1, 0)
        val previousCounter = wholeModel.history(newPosition)
        val undoneWholeModel = wholeModel.copy(counter = previousCounter, position = newPosition)
        Some(ModelUpdate(model.copy(wholeModel = undoneWholeModel)))
      }

      case WholeModel.Redo => {
        val wholeModel = model.wholeModel
        val maxHistoryIndex = wholeModel.history.maxBy(_._1)._1
        val newPosition = min(wholeModel.position + 1, maxHistoryIndex)
        val previousCounter = wholeModel.history(newPosition)
        val redoneWholeModel = wholeModel.copy(counter = previousCounter, position = newPosition)
        Some(ModelUpdate(model.copy(wholeModel = redoneWholeModel)))
      }

      case _ => None
    }
}
