package models

import com.websudos.phantom.connectors.{ContactPoints, KeySpaceDef}
import com.websudos.phantom.dsl._
import com.wisedu.next.models._

import scala.concurrent.Future

object Defaults {
  val Connector = ContactPoints(Seq("172.16.31.201")).keySpace("nextspace")
}

abstract class ConcreteFeeds extends Feeds with RootConnector {
  def store(feed: Feed):Future[ResultSet] = {
    insert.value(_.id, feed.id)
      .value(_.serId, feed.serId)
      .value(_.sigAttr, feed.sigAttr)
      .value(_.time, feed.time)
      .value(_.tags, feed.tags)
      .value(_.realTags, feed.realTags)
      .value(_.resUrl, feed.resUrl)
      .value(_.statics, feed.statics)
      .future()
  }
}

class AppDatabase(val keyspace: KeySpaceDef) extends Database(keyspace) {
  object feeds extends ConcreteFeeds with keyspace.Connector
}

object AppDatabase extends AppDatabase(Defaults.Connector)