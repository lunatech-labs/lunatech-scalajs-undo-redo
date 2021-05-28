/*
 * Copyright Audi Electronics Venture GmbH 2019
 */

package com.lunatech.undoredo

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

import com.lunatech.undoredo.controllers.BaseController

object Main {

  def main(args: Array[String]) {
    implicit val system: ActorSystem  = ActorSystem("main-system")
    implicit val ec: ExecutionContext = system.dispatcher

    val baseController = new BaseController()

    new WebServer(baseController)
      .bind()
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
}
