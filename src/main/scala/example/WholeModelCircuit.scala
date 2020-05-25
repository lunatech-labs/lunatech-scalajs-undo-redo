package example

import diode._
import diode.ActionResult.ModelUpdate
import scala.math.{ max, min }

case class Counter(value: Int)

case class WholeModel(counter: Counter, history: Map[Int, Counter], position: Int)

object WholeModel {
  case class Increase(amount: Int) extends Action
  case class Decrease(amount: Int) extends Action
  case object Reset extends Action
  case object Undo extends Action
  case object Redo extends Action
}

object WholeModelCircuit extends Circuit[WholeModel] {

  val initialModel = WholeModel(Counter(0), Map(0 -> Counter(0)), 0)

  override val actionHandler: HandlerFunction =
    (model, action) => action match {

      case WholeModel.Increase(a) => {
        val increasedCounter = Counter(model.counter.value + a)
        val increasedHistory = model.history + ((model.position + 1) -> increasedCounter)
        val increasedWholeModel = model.copy(counter = increasedCounter, history = increasedHistory, position = model.position + 1)
        Some(ModelUpdate(increasedWholeModel))
      }

      case WholeModel.Reset => Some(ModelUpdate(initialModel))

      case WholeModel.Undo => {
        val newPosition = max(model.position - 1, 0)
        val previousCounter = model.history(newPosition)
        val undoneWholeModel = model.copy(counter = previousCounter, position = newPosition)
        Some(ModelUpdate(undoneWholeModel))
      }

      case WholeModel.Redo => {
        val maxHistoryIndex = model.history.maxBy(_._1)._1
        val newPosition = min(model.position + 1, maxHistoryIndex)
        val previousCounter = model.history(newPosition)
        val redoneWholeModel = model.copy(counter = previousCounter, position = newPosition)
        Some(ModelUpdate(redoneWholeModel))
      }

      case _ => None
    }
}
