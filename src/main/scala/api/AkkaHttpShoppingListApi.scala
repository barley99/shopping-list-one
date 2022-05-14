package api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import domain.ShoppingItem
import repository.Repository
import scala.concurrent.ExecutionContext

final class AkkaHttpShoppingListApi(db: Repository[IO])(implicit ec: ExecutionContext) {
  val route: Route = path("item" / LongNumber) { id =>
    get {
      complete { db.get(id).unsafeToFuture() }
    } ~ put {
      entity(as[ShoppingItem]) { item =>
        complete { db.update(id, item).unsafeToFuture() }
      }
    } ~ delete {
      complete { db.delete(id).unsafeToFuture() }
    }
  } ~ path("items") {
    (parameter("view".as[String].optional) & get) { view =>
      complete(db.list(view.getOrElse("").toLowerCase() == "all").compile.toList.unsafeToFuture())
    } ~ post {
      entity(as[ShoppingItem]) { item =>
        complete(db.create(item).unsafeToFuture())
      }
    }
  }
}
