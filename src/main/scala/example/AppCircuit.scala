package example

import diode._
import diode.ActionResult.ModelUpdate
import scala.math.{ max, min }


case class Counter(value: Int)

case class World(counter: Counter, history: Map[Int, Counter], position: Int)

case class RootModel(world: World)

// Define actions
case class Increase(amount: Int) extends Action
case class Decrease(amount: Int) extends Action
case object Reset extends Action

case object Undo extends Action
case object Redo extends Action

object AppCircuit extends Circuit[RootModel] {
  val world0 = World(Counter(0), Map(0 -> Counter(0)), 0)
  def initialModel = RootModel(world0)

  override val actionHandler: HandlerFunction =
    (model, action) => action match {

      case Increase(a) => {
        val increasedCounter = Counter(model.world.counter.value + a)
        val increasedHistory = model.world.history + ((model.world.position + 1) -> increasedCounter)
        val increasedWorld = World(increasedCounter, increasedHistory, model.world.position + 1)
        Some(ModelUpdate(model.copy(world = increasedWorld)))
      }

      case Reset => Some(ModelUpdate(model.copy(world = world0)))

      case Undo => {
        val newPosition = max(model.world.position - 1, 0)
        val previousCounter = model.world.history(newPosition)
        val undoneWorld = model.world.copy(counter = previousCounter, position = newPosition)
        Some(ModelUpdate(model.copy(world = undoneWorld)))
      }

      case Redo => {
        val maxHistoryIndex = model.world.history.maxBy(_._1)._1
        val newPosition = min(model.world.position + 1, maxHistoryIndex)
        val previousCounter = model.world.history(newPosition)
        val redoneWorld = model.world.copy(counter = previousCounter, position = newPosition)
        Some(ModelUpdate(model.copy(world = redoneWorld)))
      }

      case _ => None
    }
}
