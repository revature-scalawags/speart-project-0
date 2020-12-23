package scala

import org.scalatest.flatspec.AnyFlatSpec
import project.utility.Output
import java.io.{PrintWriter, File}
import java.io.StringReader
import java.io.ByteArrayOutputStream
import scala.collection.mutable.Map

class output_test extends AnyFlatSpec {

    var output_class = new Output()
    Thread.sleep(100)

    "write_map_to_file" should "check if Map[String, Map[String, Any]] was put into file" in {
        var something:Any = 1
        var raw_data:Map[String, Map[String, Any]] = Map("Test" -> Map("testing" -> something))

        val out = new ByteArrayOutputStream()

        Console.withOut(out) {
            output_class.write_map_to_file(raw_data, "datalake/test.csv")
        }

        var f = new File("datalake/test.csv")
        assert(f.exists())
        f.delete()
    }

    "write_map_to_file_int" should "check if Map[String, Map[String,Int]] was put into file" in {
        var compiled_data:Map[String, Map[String,Int]] = Map("Test" -> Map("testing" -> 1))

        val out = new ByteArrayOutputStream()

        Console.withOut(out) {
            output_class.write_map_to_file_int(compiled_data, "datalake/test1.csv")
        }

        var f = new File("datalake/test1.csv")
        assert(f.exists())
        f.delete()
    }

    "write_map_stdout" should "check if Map[String, Map[String,Int]] was put into STDOUT" in {
        var compiled_data:Map[String, Map[String,Int]] = Map("Test" -> Map("testing" -> 1))

        val out = new ByteArrayOutputStream()

        Console.withOut(out) {
            output_class.write_map_stdout(compiled_data)
        }

        assert(out.toString().contains("Test"))
        assert(out.toString().contains("testing"))

        assert(out.toString().contains("Total Test: 1"))
        assert(out.toString().contains("testing: 1   \t(100.0%)"))
    }

}
