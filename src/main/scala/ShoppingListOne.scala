import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import api.AkkaHttpShoppingListApi
import cats.effect._
import com.typesafe.scalalogging.LazyLogging
import doobie._
import repository.DoobieRepository

import scala.concurrent.ExecutionContext

object ShoppingListOne extends LazyLogging {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem  = ActorSystem()
    implicit val ec: ExecutionContext = system.dispatcher

    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql://localhost/postgres",
      "postgres",
      "postgres"
    )

    val repository = new DoobieRepository[IO](xa)
    val routes     = new AkkaHttpShoppingListApi(repository).route

    Http()
      .newServerAt("localhost", 8080)
      .bind(routes)
      .andThen { case b => logger.info(s"server started at: $b") }
  }
}
