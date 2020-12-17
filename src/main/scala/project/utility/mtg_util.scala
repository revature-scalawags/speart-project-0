package project.utility
import scalaj.http.Http
import scala.concurrent._
import java.net.SocketTimeoutException
import ExecutionContext.Implicits.global

class MTGUtil(val api_key:String, val secret:String){
    //https://api.scryfall.com/cards/random?format=text
    //https://api.magicthegathering.io/v1/
    private val BASE_URL = "https://api.scryfall.com/cards/random?q=game:arena"
    private val CARD_URL = "cards"
    private val TYPES_URL = "subtypes"
    var number_request = 1

    def getTypes(): String = {
        for( i <- 0 until number_request ) {
            Future {
                val response = Http(BASE_URL)
                println(response.asString.body)
            }
        }
        
        Thread.sleep(9999)
        return "here"
    }

    def setnumber_request(nr:Int):Unit = number_request = nr

}