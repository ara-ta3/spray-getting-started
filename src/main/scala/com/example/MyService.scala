package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import spray.json._
import MediaTypes._

class MyServiceActor extends Actor with MyService {

  def actorRefFactory = context

  def receive = runRoute(myRoute)
}

case class Name(firstName :String, lastName: String)
case class User(id:String, name:Name)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val NameFormat = jsonFormat2(Name)
  implicit val UserFormat = jsonFormat2(User)
}

trait MyService extends HttpService {
  import MyJsonProtocol._

  val name = Name("Arata", "Tanaka")
  val myRoute =
    path("") {
      get {
        respondWithMediaType(`application/json`) {
          complete {
            name.toJson.compactPrint
          }
        }
      }
    } ~
  path("post") {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          """{"json":"hoge"}"""
        }
      }
    }
  }
}
