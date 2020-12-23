package project
import utility.DataCompiler
import scalaj.http._

/** Grabs arguments from user then gives all the information to the datacompiler class.
  * 
  */
object Project0 extends App {

    // Object variables
    var data_collection = new DataCompiler()

    // New lines to make project look better
    println("\n\n\n\n")

    // Check arguments
    if(args.length == 0 ){
        data_collection.cli_main()

    // Check args and run, then goes to main
    }else if (cli_args(args)){
        //Prerun based on the args
        data_collection.build_and_run_query()

        //Main actions of this program
        data_collection.cli_main()
    }

    
    /** Goes through the arguments passed into the object.
      * Returns true if args passed are correctly formatted.
      *
      * @param arguments Program arguments
      * @return True if the user used correct arguments when calling the function else false.
      */
    def cli_args(arguments: Array[String]):Boolean = {

        // Check arguments
        var i = 0
        while(i < arguments.length){

            arguments(i) match {
                case "-n" if(i + 1 < arguments.length) => {
                    try {
                        if(arguments(i+1).toInt > 0 && arguments(i+1).toInt < 501){
                            data_collection.magic_utility.number_request = arguments(i+1).toInt
                        }else{
                            println(s"An error has occurred. Number of request is not [1-500] ${arguments(i+1)}") 
                            help()
                            return false
                        }
                    }catch{
                        case e: NumberFormatException => {
                            println(s"An error has occurred. Unknown flag or arg: ${arguments(i+1)}") 
                            help()
                            return false
                        }
                    }
                }
                case _ => {
                    println(s"An error has occurred. Unknown flag or arg: ${arguments(i)}")
                    help()
                    return false
                }
            }

            i += 2
        }

        return true
    }


    /** Prints to command line how to use the program.
      * 
      */
    private def help():Unit = {
        println("No arguments --")
        println("sbt run\n")
        println("Arguments --")
        println("sbt \"run <flag> <flag -data>\"")
        println("Flags:")
        println("-n <Number> \n\t - For the number of cards or request user wants. [Must be 1-500]\n")
    }

}                      