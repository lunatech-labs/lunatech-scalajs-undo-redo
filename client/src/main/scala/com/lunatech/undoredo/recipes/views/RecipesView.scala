package com.lunatech.undoredo.recipes.views

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import com.lunatech.undoredo.recipes.Client
import com.lunatech.undoredo.recipes.models._
import com.lunatech.undoredo.recipes.handlers.RecipesCircuit

object RecipesView {

  private val couscous = Recipe(
    "couscous",
    "Couscous",
    Seq("ig12", "ig22"),
    Seq("is12", "is22")
  )
  private val cremeBrulee = Recipe(
    "creme-brulee",
    "Creme Brulee",
    Seq("ig13", "ig23"),
    Seq("is13", "is23")
  )
  case class Props(
      model: RootModel
  )

  private val component = ScalaComponent
    .builder[Props]("recipesView")
    .render_P { props: Props =>
      <.div(
        <.div(
          ^.cls := "btn-group",
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(RecipesCircuit.dispatch(UndoRedoHistoryActions.Undo)),
            "Undo"
          ),
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(RecipesCircuit.dispatch(UndoRedoHistoryActions.Redo)),
            "Redo"
          ),
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(RecipesCircuit.dispatch(Reset)),
            "Reset"
          )
        ),
        <.p(),
        <.div(
          <.p("Recipes: "),
          <.ul(
            ^.cls := "list-group",
            props.model.recipes.toVdomArray { recipe =>
              <.li(
                ^.key := recipe.id,
                ^.cls := "list-group-item",
                <.span(recipe.name),
                <.button(
                  ^.`type` := "button",
                  ^.cls := "btn btn-outline-secondary",
                  ^.onClick --> Callback(RecipesCircuit.dispatch(RecipeActions.Delete(recipe))),
                  "Delete"
                )
              )
            }
          )
        ),
        <.p(),
        <.div(
          ^.cls := "btn-group",
          ^.role := "group",
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(RecipesCircuit.dispatch(RecipeActions.Add(couscous))),
            "Add Couscous"
          ),
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(RecipesCircuit.dispatch(RecipeActions.Add(cremeBrulee))),
            "Add Creme Brulee"
          )
        ),
        <.p(),
        <.div(
          ^.cls := "btn-group",
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(Client.fetchRecipes(RecipesCircuit)),
            "Fetch"
          ),
          <.button(
            ^.`type` := "button",
            ^.cls := "btn btn-outline-secondary",
            ^.onClick --> Callback(Client.persist(props.model.persistence, RecipesCircuit)),
            "Persist"
          )
        )
      )
    }
    .build

  def apply() = RecipesCircuit.connectRootModel { model =>
    component(Props(model()))
  }
}
