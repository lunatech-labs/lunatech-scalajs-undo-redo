package com.lunatech.undoredo.controllers

import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContext
import com.lunatech.undoredo.twirl.Implicits._
import akka.http.scaladsl.server.PathMatcher
import akka.http.scaladsl.server.Route

class BaseController()(implicit val ec: ExecutionContext) {
  val home = path("") {
    get {
      complete {
        com.lunatech.undoredo.html.index.render()
      }
    }
  }

  val assets = pathPrefix("assets") {
    path(Remaining) { file =>
      encodeResponse {
        getFromResource("public/" + file)
      }
    }
  }

  val favicon = path("favicon.ico") {
    get {
      encodeResponse {
        getFromResource("public/undo.ico")
      }
    }
  }

  val routes: Route = home ~ assets ~ favicon
}
