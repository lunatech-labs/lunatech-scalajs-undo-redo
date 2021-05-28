package com.lunatech.undoredo.fullcopies.handlers

import diode._
import diode.ActionResult.ModelUpdate
import scala.math.{ max, min }
import diode.react.{ ReactConnectProxy, ReactConnector }

import com.lunatech.undoredo.fullcopies.models._

object FullCopiesCircuit extends Circuit[FullCopies] with ReactConnector[FullCopies] {

  val initialModel = FullCopies(Counter(0), Map(0 -> Counter(0)), 0)

  override val actionHandler: HandlerFunction =
    (model, action) =>
      action match {

        case FullCopies.Increase(a) =>
          val increasedCounter = Counter(model.counter.value + a)
          val increasedHistory = model.history + ((model.position + 1) -> increasedCounter)
          val increasedFullCopies = model.copy(
            counter  = increasedCounter,
            history  = increasedHistory,
            position = model.position + 1
          )
          Some(ModelUpdate(increasedFullCopies))

        case FullCopies.Reset => Some(ModelUpdate(initialModel))

        case FullCopies.Undo =>
          val newPosition      = max(model.position - 1, 0)
          val previousCounter  = model.history(newPosition)
          val undoneFullCopies = model.copy(counter = previousCounter, position = newPosition)
          Some(ModelUpdate(undoneFullCopies))

        case FullCopies.Redo =>
          val maxHistoryIndex  = model.history.maxBy(_._1)._1
          val newPosition      = min(model.position + 1, maxHistoryIndex)
          val previousCounter  = model.history(newPosition)
          val redoneFullCopies = model.copy(counter = previousCounter, position = newPosition)
          Some(ModelUpdate(redoneFullCopies))

        case _ => None
      }
  val connectFullCopies: ReactConnectProxy[FullCopies] = FullCopiesCircuit.connect(identity(_))
}
