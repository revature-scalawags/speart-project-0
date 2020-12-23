
package scala

import project.Project0
import org.scalatest.flatspec.AnyFlatSpec
import java.io.StringReader
import java.io.ByteArrayOutputStream

class Project0_Test extends AnyFlatSpec  {

    //Can only do functional programming because it extends app

    Thread.sleep(100)

    //Test for wrong input from user
    "cli_args" should "Check for wrong input" in {

        val out = new ByteArrayOutputStream()

        var wtest1 = Array("-n", "1444")
        var wtest2 = Array("-n", "0")
        var wtest3 = Array("-n", "501")
        var wtest4 = Array("-y", "406")
        var wtest5 = Array("400")

        Console.withOut(out) {
            assert(Project0.cli_args(wtest1) == false)
            assert(Project0.cli_args(wtest2) == false)
            assert(Project0.cli_args(wtest3) == false)
            assert(Project0.cli_args(wtest4) == false)
            assert(Project0.cli_args(wtest5) == false)
        }
    }


}
