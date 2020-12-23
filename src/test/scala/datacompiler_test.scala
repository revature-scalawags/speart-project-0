package scala

import org.scalatest.flatspec.AnyFlatSpec
import project.utility.DataCompiler
import java.io.StringReader
import java.io.ByteArrayOutputStream
import scala.collection.mutable.Map


class Datacompiler_Test extends AnyFlatSpec  {

    var data_comp = new DataCompiler()
    Thread.sleep(100)

    //Test for wrong input from user
    "cli_main" should "check random input and quit" in {
        val inputStr = "wbcahbwfhba\nq"
        val check_string = "\n---Wrong input when selecting an option [x] type in x.---\n"
        val in = new StringReader(inputStr)
        val out = new ByteArrayOutputStream()

        Console.withOut(out) {
            Console.withIn(in) {
                data_comp.cli_main()
            }
        }

        assert(out.toString().contains(check_string))
        assert(out.toString().contains("Quitting"))
        out.close()
        
    }

    "build_and_run_query" should "check random input and back" in {
        val inputStr = "wbcahbwfhba\nb"
        val check_string = "\n---Wrong input when selecting an option [x] type in x.---\n"
        val in = new StringReader(inputStr)
        val out = new ByteArrayOutputStream()

        Console.withOut(out) {
            Console.withIn(in) {
                data_comp.build_and_run_query()
            }
        }

        assert(out.toString().contains(check_string))
        assert(out.toString().contains("Back"))
        out.close()
        
    }

    "set_number_of_request" should "check incorrect number then for correct number" in {
        val inputStr = "1000\n300"
        val check_string = "Enter in number of request: [1-500]:"
        val check_string1 = "Not a valid number."
        val in = new StringReader(inputStr)
        val out = new ByteArrayOutputStream()

        Console.withOut(out) {
            Console.withIn(in) {
                data_comp.set_number_of_request()
            }
        }

        assert(out.toString().contains(check_string))
        assert(out.toString().contains(check_string1))
        assert(data_comp.magic_utility.number_request == 300)
        out.close()
    }

    "sorting_data" should "check if it sorts correctly" in {
        var raw_data = Map[String, Map[String, Any]]()
        var something:Any = "akr"
        raw_data += ("0" -> Map("set" -> something))

        var compiled_data = data_comp.sorting_data(raw_data)
        assert(compiled_data("set")("akr") == 1)
    }
    
}
