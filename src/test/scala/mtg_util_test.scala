package scala

import org.scalatest.flatspec.AnyFlatSpec
import project.utility.MTGUtil
import java.io.StringReader
import java.io.ByteArrayOutputStream
import scala.collection.mutable.Map

class mtg_util_test extends AnyFlatSpec  {

    var mtg_class = new MTGUtil("", "")
    Thread.sleep(100)

    "setnumber_request" should "check setter" in {
        mtg_class.setnumber_request(200)
        assert(mtg_class.number_request == 200)
    }

    "randomcards" should "check url" in {
        assert(mtg_class.randomcards("https://www.google.com/").asString.code == 200)
    }

}
