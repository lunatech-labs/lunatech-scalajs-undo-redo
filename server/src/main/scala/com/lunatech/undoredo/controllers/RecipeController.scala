package com.lunatech.undoredo.controllers

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import scala.concurrent.{ ExecutionContext, Future }

import com.lunatech.undoredo.shared.model.Recipe
import akka.http.scaladsl.server.Route

class RecipeController()(implicit val ec: ExecutionContext) {

  private var recipes: Seq[Recipe] = Seq.empty

  val getRecipes = path("recipes") {
    get {
      complete {
        Future.successful(recipes)
      }
    }
  }

  val addRecipe = path("recipes") {
    post {
      entity(as[Recipe]) { recipe =>
        complete {
          recipes = recipes :+ recipe
          "Ok"
        }
      }
    }
  }

  val deleteRecipe = pathPrefix("recipes") {
    path(Remaining) { recipeId =>
      delete {
        complete {
          recipes = recipes.filter(_.id == recipeId)
          "Ok"
        }
      }
    }
  }

  val routes: Route = getRecipes ~ addRecipe ~ deleteRecipe
}
