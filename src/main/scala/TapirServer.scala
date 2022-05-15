import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import api.TapirShoppingListApi
import cats.effect.IO
import com.typesafe.scalalogging.LazyLogging
import doobie.Transactor
import repository.DoobieRepository
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import sttp.tapir.swagger.SwaggerUI
import sttp.tapir.openapi.circe.yaml.RichOpenAPI

import scala.concurrent.Future

object TapirServer extends LazyLogging {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val es     = system.dispatcher

    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql://localhost/postgres",
      "postgres",
      "postgres"
    )
    val repository = new DoobieRepository[IO](xa)

    val service = new TapirShoppingListApi(repository)
    val routes  = AkkaHttpServerInterpreter().toRoute(service.allEndpoints)
    val openApi = OpenAPIDocsInterpreter().serverEndpointsToOpenAPI(
      service.allEndpoints,
      "Shopping List Server",
      "0.1"
    )

    val swagger =
      AkkaHttpServerInterpreter().toRoute(SwaggerUI[Future](openApi.toYaml))

    import akka.http.scaladsl.server.RouteConcatenation._

    Http()
      .newServerAt("localhost", 8080)
      .bind(routes ~ swagger)
      .foreach(binding => println(binding.toString))
  }
}
