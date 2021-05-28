package com.lunatech.undoredo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http.ServerBinding
import scala.io.StdIn
import scala.concurrent.{ ExecutionContext, Future }

import com.lunatech.undoredo.controllers.BaseController
import akka.http.scaladsl.Http

class WebServer(
    baseController: BaseController
)(implicit val system: ActorSystem, executor: ExecutionContext) {

  def bind(): Future[ServerBinding] = {
    val routes = baseController.routes

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
  }
}
