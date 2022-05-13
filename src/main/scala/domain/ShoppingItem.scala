package domain
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

final case class ShoppingItem(title: String, done: Boolean)

object ShoppingItem {
  implicit val jsonDecoder: Decoder[ShoppingItem] = deriveDecoder
  implicit val jsonEncoder: Encoder[ShoppingItem] = deriveEncoder
}
