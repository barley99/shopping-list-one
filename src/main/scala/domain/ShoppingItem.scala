package domain
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

final case class ShoppingItem(
    id: Long,
    title: String,
    done: Boolean,
)

object ShoppingItem {
  implicit val decode: Decoder[ShoppingItem] = deriveDecoder
  implicit val encode: Encoder[ShoppingItem] = deriveEncoder
}
