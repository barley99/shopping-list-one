package api
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class ShoppingListApi {
  val route: Route = path("item" / IntNumber) { id: Int =>
    get {
      ???
    } ~ put {
      ???
    } ~ delete {
      ???
    }
  } ~ path("items") {
    get {
      ???
    } ~ post {
      ???
    }
  }
}
