package project
import utility.MTGUtil
import scala.collection.mutable.Map
import scalaj.http._

object Project0 extends App {

    //Object variables
    private var api_key = ""
    private var secret = ""

    //New lines to make project look better
    println("\n\n\n\n")

    // Environment variables setters
    if (sys.env.get("API_KEY") != None && !sys.env.get("API_KEY").get.isEmpty()){
        api_key = sys.env.get("API_KEY").get
    }
    if (sys.env.get("SECRET") != None && !sys.env.get("SECRET").get.isEmpty()){
        secret = sys.env.get("SECRET").get
    }

    //check arguments
    if(args.length > 0 && cli_args()){
        //Check args and run
        
        cli_main()
    }else{
        cli_main()
    }


    //TODO: A lot
    def cli_args():Boolean = {
        // Check args
        args.foreach(println)

        var i = 0
        while(i < args.length){

            args(i) match {
                case "-run" => {
                    i -= 1
                }
                case "-t" |"-r" | "-s" | "-c" | "-n" => {
                    if(i + 1 < args.length){
                        println("Check if valid")
                    }else{
                        println("Print how to use the program")
                        return false
                    }
                }
                case _ => {
                    println(s"An error has occurred. Unknown flag or arg: ${args(i)}")
                    return false
                }
            }

            i += 2
        }

        return true
    }
    

    //DONE
    def cli_main(): Unit = {
        var user_input = ""
        while(user_input != "q"){
            // Give options
            options()

            // Take User input
            user_input = scala.io.StdIn.readLine()

            // Check user input
            user_input match {
                case "1" => set_restrait()
                case "2" => type_restrait()
                case "3" => rarity_restrait()
                case "4" => color_restrait()
                case "5" => color_restrait()
                case "6" => clear_restraits()
                case "7" => build_and_run_query()
                case "q" => println("Quitting") 
                case _ => println("Wrong input when selecting an option [x] type in x.")
            }

        }
    }

    //TODO: Tell the user what has been set up for the query
    def options():Unit = {
        println("User Options")
        println("[1]: Cards set restrait")
        println("[2]: Cards type restrait")
        println("[3]: Cards rarity restrait")
        println("[4]: Cards color restrait")
        println("[5]: Number of cards")
        println("[6]: Clear all restraits")
        println("[7]: Run")
        println("[q]: Quit")
    }

    //TODO: Give user options on which set
    def set_restrait():Unit = {
        println("Cards set restrait")
    }

    //TODO: Give user options on which type
    def type_restrait():Unit = {
        println("Cards type restrait")
    }

    //TODO: Give user options on which rarity
    def rarity_restrait():Unit = {
        println("Cards rarity restrait")
    }

    //TODO: Give user options on which colors
    def color_restrait():Unit = {
        println("Cards color restrait")
    }

    //TODO: Give user options on which colors
    def clear_restraits():Unit = {
        println("Clearing restraits")
    }

    //TODO: Give user on how many cards to run this for
    def build_and_run_query():Unit = {

        //First grab the number of request
        val number_request = set_number_of_request()
        var magic_utility = new MTGUtil(api_key, secret)

        //magic_utility.getTypes()
    }

    //TODO: MAKE INTO A DO WHILE AND CHECK IF USER WANTS THIS NUMBER
    def set_number_of_request():Int = {
        //Grab number of request
        var number_request = 0

        //Keep grab number from user until valid number
        while (number_request <= 0 && number_request > 500){
            println("Enter in number of request: [1-500]:")
            number_request = scala.io.StdIn.readInt()

            //If grabbed number is not valid print an error message
            if(number_request <= 0 && number_request > 500){
                println("Not a valid number.")
            }
        }

        return number_request
    }

}                      