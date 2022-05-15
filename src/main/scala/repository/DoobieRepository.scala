package repository

import cats.effect.Sync
import domain.ShoppingItem
import doobie._
import doobie.implicits._
import fs2.Stream

final class DoobieRepository[F[_]: Sync](xa: Transactor[F]) extends Repository[F] {
  override def create(key: String, shoppingItem: ShoppingItem): F[ShoppingItem] =
    Q.insertItem(shoppingItem).withUniqueGeneratedKeys[ShoppingItem]("id", "title", "done").transact(xa)

  override def update(id: Long, shoppingItem: ShoppingItem): F[Option[ShoppingItem]] = {
    val q = for {
      _   <- Q.updateItem(id: Long, shoppingItem).run
      res <- Q.selectItemById(id).option
    } yield res
    q.transact(xa)
  }

  override def get(shoppingItemId: Long): F[Option[ShoppingItem]] =
    Q.selectItemById(shoppingItemId).option.transact(xa)

  override def delete(shoppingItemId: Long): F[Option[Unit]] = {
    def toOption(n: Int): Option[Unit] =
      if (n > 0) Some(()) else None
    Q.deleteById(shoppingItemId).run.map(toOption).transact(xa)
  }
  override def list(all: Boolean): Stream[F, ShoppingItem] =
    Q.selectItems(all).stream.take(100).transact(xa)

  object Q {
    def insertItem(shoppingItem: ShoppingItem) =
      sql"""
        |INSERT INTO ShoppingItems (title, done) 
        |VALUES (${shoppingItem.title}, ${shoppingItem.done})
        |""".stripMargin.update

    def selectItemById(id: Long) =
      sql"""
        |SELECT id, title, done FROM ShoppingItems WHERE id = $id
        |""".stripMargin.query[ShoppingItem]

    def updateItem(id: Long, shoppingItem: ShoppingItem) =
      sql"""
        |UPDATE ShoppingItems
        |SET title = ${shoppingItem.title}, done = ${shoppingItem.done}
        |WHERE id = $id
        |""".stripMargin.update

    def deleteById(shoppingItemId: Long) =
      sql"""
        |DELETE FROM ShoppingItems WHERE id = $shoppingItemId
        |""".stripMargin.update

    def selectItems(all: Boolean) = {
      val whereFragment: Fragment = if (all) fr"" else fr"WHERE done = FALSE"
      val mainFragment =
        sql"""
        |SELECT id, title, done FROM ShoppingItems
        |""".stripMargin
      (mainFragment ++ whereFragment ++ fr"ORDER BY id DESC").query[ShoppingItem]
    }
  }

}
