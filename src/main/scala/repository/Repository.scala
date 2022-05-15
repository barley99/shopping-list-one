package repository

import domain.ShoppingItem
import fs2.Stream

trait Repository[F[_]] {
  def create(key: String, shoppingItem: ShoppingItem): F[ShoppingItem]
  def update(id: Long, shoppingItem: ShoppingItem): F[Option[ShoppingItem]]
  def get(shoppingItemId: Long): F[Option[ShoppingItem]]
  def delete(shoppingItemId: Long): F[Option[Unit]]
  def list(all: Boolean): Stream[F, ShoppingItem]
}
