package api
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import domain.ShoppingItem
import repository.Repository
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._

import scala.concurrent.{ExecutionContext, Future}

class TapirShoppingListApi(db: Repository[IO])(implicit ec: ExecutionContext) {
  implicit lazy val sShoppingItem: Schema[ShoppingItem] = Schema.derived

//    |/items|	POST		|Add new item|
  private val createShoppingItemEndpoint = endpoint.post
    .in("items")
    .in(jsonBody[ShoppingItem])
    .in(header[String]("Idempotency-Key"))
    .errorOut(stringBody)
    .serverLogic[Future] { case (item, key) => db.create(key, item).unsafeToFuture().map(Right(_)) }
    .description("Add new shopping item")
//    |/items|	GET			|Get all pending items|
//    |/item/{id}|PUT			|Update an item|
//    |/item/{id}|DELETE		|Delete an item|
//    |/items?all=true|GET	|Get all pending and done items|

  val allEndpoints = List(createShoppingItemEndpoint)
}
