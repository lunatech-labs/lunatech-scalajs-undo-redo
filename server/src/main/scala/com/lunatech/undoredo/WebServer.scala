package com.lunatech.undoredo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import scala.concurrent.{ ExecutionContext, Future }

import com.lunatech.undoredo.controllers.{ BaseController, RecipeController }
import akka.http.scaladsl.Http

class WebServer(
    baseController: BaseController,
    recipesController: RecipeController
)(implicit val system: ActorSystem, executor: ExecutionContext) {

  def bind(): Future[ServerBinding] = {
    val routes = baseController.routes ~ recipesController.routes

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
  }
}
