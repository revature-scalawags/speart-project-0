package project.utility

import scalaj.http.{Http, HttpRequest}
import scala.concurrent._
import spray.json._
import java.net.SocketTimeoutException
import ExecutionContext.Implicits.global
import scala.util.Failure
import scala.util.Success
import scala.collection.mutable.Map
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit

class MTGUtil(val api_key:String, val secret:String) extends LazyLogging{
    private val BASE_URL = "https://api.scryfall.com/cards/random?q=game:arena"
    var number_request = 2

    /** Sets the number of request for when you do the api calls.
      * 
      * @param nr An int that number of request gets set to.
      */
    def setnumber_request(nr:Int):Unit = number_request = nr

    /** Does the request call to the api.
      *
      * @param url A string of the url
      * @return An Http request from the api
      */
    def randomcards(url:String):HttpRequest = Http(url)

    /** Does number of request to an api and puts that data into a map.
      * The map gets filled and then passed back.
      * 
      * @return A map filled with information from the api.
      */
    def api_calls():Map[String, Map[String, Any]] = {
        var card_map = Map[String, Map[String, Any]]()

        for( i <- 0 until number_request ) {
            val card = Future {
                randomcards(BASE_URL).asString.body.parseJson
            }

            card onComplete {
                case Success(value) => card_map += ( i.toString -> relevant_data(value))
                case Failure(exception) => logger.debug("An error has occured with the request:" + exception.getMessage)//println("An error has occurred: " + exception.getMessage)
            }

            // This will always return an error if doing lots of request
            // Await.ready(card, Duration(10, TimeUnit.SECONDS) )
        }

        Thread.sleep(3000 + (30 * number_request))
        return card_map
    }

    /** Gets the pure raw data from the api and filters the data.
      * Filtered data gets put into a map and returned.
      *
      * @param value Pure raw data that comes from the api.
      * @return Filtered but still raw data data from raw data.
      */
    def relevant_data(value:JsValue): Map[String, Any] = {
        var cleaned_data = Map[String, Any]()

        value.asJsObject.fields.foreach(
            x => {
                x._1 match {
                    //case "power" | "mana_cost" | "rarity" | "set" | "set_name" | "toughness" | "type_line" => cleaned_data += (x._1.toString -> x._2) 
                    case  "rarity" | "set" | "set_name" | "type_line" => cleaned_data += (x._1.toString -> x._2) 
                    case "color_identity" => cleaned_data += (x._1.toString -> x._2)
                    case _ => //println(x) //- Do this if you want to grab see filtered out data
                }
            }
        )

        return cleaned_data
    }

}