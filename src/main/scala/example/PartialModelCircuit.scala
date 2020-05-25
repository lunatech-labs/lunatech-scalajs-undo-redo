package example

import diode._
import diode.ActionResult.ModelUpdate
import scala.math.{ max, min }

case class PartialModel(counter: Counter, pastActions: List[UndoableAction], futureActions: List[UndoableAction])

object PartialModel {
  case class Increase(amount: Int) extends Action
  case class Decrease(amount: Int) extends Action
  case object Reset extends Action
  case object Undo extends Action
  case object Redo extends Action
}

trait UndoableAction extends Action {
  def undo(model: PartialModel): PartialModel
  def update(model: PartialModel): PartialModel
}

class UndoProcessor extends ActionProcessor[PartialModel] {

  override def process(dispatch: Dispatcher, action: Any, next: Any => ActionResult[PartialModel], currentModel: PartialModel): ActionResult[PartialModel] = {
    action match {
      case PartialModel.Undo =>
          val actionToUndo = currentModel.pastActions.head
          val newModel = actionToUndo.undo(currentModel).copy(
            pastActions = currentModel.pastActions.tail,
            futureActions = actionToUndo +: currentModel.futureActions
          )
          ActionResult.ModelUpdate(newModel)

      case PartialModel.Redo =>
        val actionToRedo = currentModel.futureActions.head
        val newModel = actionToRedo.update(currentModel).copy(
          pastActions = actionToRedo +: currentModel.pastActions,
          futureActions = currentModel.futureActions.tail
        )
        ActionResult.ModelUpdate(newModel)
      case action: UndoableAction =>
        val newModel = action.update(currentModel).copy(
          pastActions = action +: currentModel.pastActions
        )
        ActionResult.ModelUpdate(newModel)
      case action =>
        next(action)
    }
  }
}
