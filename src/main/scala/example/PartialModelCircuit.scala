package example

import diode._
import diode.ActionResult.ModelUpdate
import scala.math.{ max, min }

trait UndoableAction extends Action {
  def update(model: PartialModel): PartialModel
  def undo(model: PartialModel): PartialModel
}

case class PartialModel(counter: Counter, pastActions: List[UndoableAction], futureActions: List[UndoableAction])

object PartialModel {

  case class Increase(amount: Int) extends UndoableAction {
    def update(model: PartialModel): PartialModel = model.copy(counter = Counter(model.counter.value + amount))
    def undo(model: PartialModel): PartialModel = model.copy(counter = Counter(model.counter.value - amount))
  }

  case class Decrease(amount: Int) extends UndoableAction {
    def update(model: PartialModel): PartialModel = model.copy(counter = Counter(model.counter.value - amount))
    def undo(model: PartialModel): PartialModel = model.copy(counter = Counter(model.counter.value + amount))
  }

  case object Reset extends Action
  case object Undo extends Action
  case object Redo extends Action
}

object PartialModelCircuit extends Circuit[PartialModel] {

  val initialModel = PartialModel(Counter(0), Nil, Nil)

  override val actionHandler: HandlerFunction =
    (model, action) => action match {

      case PartialModel.Reset => Some(ModelUpdate(initialModel))

      case PartialModel.Undo => {
        val actionToUndo = model.pastActions.head
        val newModel = actionToUndo.undo(model).copy(
          pastActions = model.pastActions.tail,
          futureActions = actionToUndo +: model.futureActions
        )
        Some(ModelUpdate(newModel))
      }

      case PartialModel.Redo => {
        val actionToRedo = model.futureActions.head
        val newModel = actionToRedo.update(model).copy(
          pastActions = actionToRedo +: model.pastActions,
          futureActions = model.futureActions.tail
        )
        Some(ModelUpdate(newModel))
      }

      case action: UndoableAction => {
        val newModel = action.update(model).copy(
          pastActions = action +: model.pastActions
        )
        Some(ModelUpdate(newModel))
      }

      case _ => None
    }
}
