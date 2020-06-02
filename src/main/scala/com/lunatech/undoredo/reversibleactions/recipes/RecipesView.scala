package com.lunatech.undoredo.reversibleactions.recipes

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import diode._
import scalatags.JsDom.all._
import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.{Ajax, KeyCode}
import org.scalajs.dom.raw.Event
import scala.scalajs.js
import io.circe.parser._
import io.circe.syntax._
import io.circe.{ Decoder, Encoder }
import scala.concurrent.ExecutionContext

class RecipesView(state: ModelRO[RootModel], dispatch: Dispatcher) {

  val couscous = Recipe("couscous", "Couscous", Seq("ig12", "ig22"), Seq("is12", "is22"))
  val cremeBrulee = Recipe("creme-brulee", "Creme Brulee", Seq("ig13", "ig23"), Seq("is13", "is23"))

  js.timers.setInterval(5000) {
    Client.persist(state().persistence, dispatch)
  }

  def render = {
    div(
      div(
        cls := "btn-group",
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(UndoRedoHistoryActions.Undo)), "Undo"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(UndoRedoHistoryActions.Redo)), "Redo"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(Reset)), "Reset")
      ),
      p(),
      div(
        p("Recipes: "),
        ul(cls := "list-group",
          state().recipes.toSeq.map { recipe => li(cls := "list-group-item",
            span(recipe.name),
            button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(RecipeActions.Delete(recipe))), "Delete"),
          )}),
      ),
      p(),
      div(
        cls := "btn-group",
        role := "group",
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(RecipeActions.Add(couscous))), "Add Couscous"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => dispatch(RecipeActions.Add(cremeBrulee))), "Add Creme Brulee")
      ),
      p(),
      div(
        cls := "btn-group",
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => Client.fetchRecipes(dispatch)), "Fetch"),
        button(`type` := "button", cls := "btn btn-outline-secondary", onclick := (() => Client.persist(state().persistence, dispatch)), "Persist")
      )
    )
  }
}
