package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import models._
import com.wisedu.next.models._
import java.util.UUID
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class CreateFeedForm(title: String, summary: String, summary_img_url: String, src_id: String, src_url: String,
                          service_name: String)

object FeedsController extends Controller {

  val feedForm: Form[CreateFeedForm] = Form(
    mapping(
      "title" -> nonEmptyText,
      "summary" -> nonEmptyText,
      "summary_img_url" -> nonEmptyText,
      "src_id" -> nonEmptyText,
      "src_url" -> nonEmptyText,
      "service_name" -> nonEmptyText
    )(CreateFeedForm.apply)(CreateFeedForm.unapply)
  )

  def toFeed(cFeedForm: CreateFeedForm):Feed = {
    val id = UUID.nameUUIDFromBytes(cFeedForm.src_id.getBytes)
    val serId = UUID.nameUUIDFromBytes(cFeedForm.service_name.getBytes)
    val sigAttr = Map{"title" -> cFeedForm.title; "summ" -> cFeedForm.summary; "summImgUrl" -> cFeedForm.summary_img_url;
      "srcId" -> cFeedForm.src_id; "srcUrl" -> cFeedForm.src_url}
    val time = Map{"cTime" -> DateTime.now()}
    val tags:List[String] = List()
    val realTags:List[String] = List()
    val resUrl:List[String] = List()
    val statics = Map{"likes" -> 1L; "views" -> 1L}
    Feed(id, serId, sigAttr, time, tags, realTags, resUrl, statics)
  }

  def index = Action {
    Ok(views.html.index(feedForm))
  }

  def addFeed = Action.async {implicit request =>
    feedForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok(views.html.index(errorForm)))
      },
      cFeedForm => {
        AppDatabase.feeds.store(toFeed(cFeedForm)).map {
          feed => Ok(views.html.index(feedForm))
        }
      }
    )
  }
}