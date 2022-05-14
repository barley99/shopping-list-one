package repository

import domain.ShoppingItem
import fs2.Stream

trait Repository[F[_]] {
  def create(shoppingItem: ShoppingItem): F[ShoppingItem]
  def update(id: Long, shoppingItem: ShoppingItem): F[Option[ShoppingItem]]
  def get(shoppingItemId: Long): F[Option[ShoppingItem]]
  def delete(shoppingItemId: Long): F[Option[Unit]]
  def list(all: Boolean): Stream[F, ShoppingItem]
}
