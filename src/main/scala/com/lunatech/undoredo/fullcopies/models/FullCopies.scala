package com.lunatech.undoredo.fullcopies.models

import diode.Action

case class FullCopies(counter: Counter, history: Map[Int, Counter], position: Int)

case class Counter(value: Int)

object FullCopies {
  case class Increase(amount: Int) extends Action
  case class Decrease(amount: Int) extends Action
  case object Reset extends Action
  case object Undo extends Action
  case object Redo extends Action
}
