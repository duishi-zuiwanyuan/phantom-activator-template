package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import models._
import com.wisedu.next.models._

import scala.concurrent.Future

case class CreateFeedForm(title: String, summ: String, summImgUrl: String, srcId: String, srcUrl: String)

object FeedsController extends Controller {

  val feedForm: Form[CreateFeedForm] = Form(
    mapping(
      "title" -> nonEmptyText,
      "summ" -> nonEmptyText,
      "summImgUrl" -> nonEmptyText,
      "srcId" -> nonEmptyText,
      "srcUrl" -> nonEmptyText
    )(CreateFeedForm.apply)(CreateFeedForm.unapply)
  )

  def toApiFeed(feedForm: CreateFeedForm):Feed = {
    nameUUIDFromBytes()
    Feed(feedForm.title)
  }

  def addFeed = Action.async { implicit request =>
    feedForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok(views.html.index(errorForm)))
      },
      feedForm => {
        Future.successful(Ok(views.html.index(errorForm)))
        val a = AppDatabase.feeds.store(feedForm)
      }
    )
  }

  def index = Action {
    Ok(views.html.index(feedForm))
  }

}