package project.utility

import scala.collection.mutable.Map
import com.typesafe.scalalogging.LazyLogging

class DataCompiler extends LazyLogging{

    private var api_key = ""
    private var secret = ""
    var magic_utility = new MTGUtil(api_key, secret)
    var output_utility = new Output()

    // Environment variables setters - ORDER MATTERS HERE
    if (sys.env.get("API_KEY") != None && !sys.env.get("API_KEY").get.isEmpty()){
        api_key = sys.env.get("API_KEY").get
    }
    if (sys.env.get("SECRET") != None && !sys.env.get("SECRET").get.isEmpty()){
        secret = sys.env.get("SECRET").get
    }
  

    /** Ask the user on options of what to do with the query.
     * 
     */
    def cli_main(): Unit = {
        var user_input = ""
        while(user_input != "q"){
            // Give options
            options()

            // Take User input
            user_input = scala.io.StdIn.readLine()

            // Check user input
            user_input match {
                case "1" => build_and_run_query()
                case "2" => set_number_of_request()
                case "q" => println("Quitting") 
                case _ => println("\n---Wrong input when selecting an option [x] type in x.---\n")
            }

        }
    }


    /** Prints out the user options for possiblities.
     * 
     */
    def options():Unit = {
        println("User Options")
        println("[1]: Run")
        println("[2]: Change number of cards [Request]: " + magic_utility.number_request)
        println("[q]: Quit")
    }


    /** Calls the api and gives the user options to do with the data.
     * 
      */
    def build_and_run_query():Unit = {

        //run the api and get the data
        var raw_data = magic_utility.api_calls()
        
        var compiled_data = Map[String, Map[String, Int]]()
        //var compiled_data = sorting_data(raw_data)

        var user_input = ""
        while(user_input != "b"){
            // Give options
            view_data_options()

            // Take User input
            user_input = scala.io.StdIn.readLine()

            // Check user input
            user_input match {
                case "1" => {
                    compiled_data = sorting_data(raw_data)

                    //Write to raw file
                    output_utility.write_map_to_file(raw_data, "datalake/raw.csv")

                    //Write to compiled file
                    output_utility.write_map_to_file_int(compiled_data, "datalake/compiled.csv")

                }
                case "2" => {
                    compiled_data = sorting_data(raw_data)
                    output_utility.mongo_database_add(raw_data, compiled_data)
                }
                case "3" => {
                    compiled_data = sorting_data(raw_data)
                    output_utility.write_map_stdout(compiled_data)
                }
                case "4" => println(raw_data)
                case "b" => println("Back") 
                case _ => println("\n---Wrong input when selecting an option [x] type in x.---\n")
            }
        }
    }

    /** Prints out user options to do with the data grabbed from the api.
      * 
      */
    private def view_data_options():Unit = {
        println("\n\n\n\n")
        println("User Options --")
        println("[1]: Write to cvs")
        println("[2]: Write to mongo")
        println("[3]: Read compiled data")
        println("[4]: Read raw data")
        println("[b]: Back")
    }

    /** Sets the number of request and keeps asking user until correctly given input.
      * 
      */
    def set_number_of_request():Unit = {
        //Grab number of request
        var number_request = 0

        //Keep grab number from user until valid number
        while (number_request < 1 || number_request > 500){
            println("Enter in number of request: [1-500]:")
            number_request = scala.io.StdIn.readInt()

            //If grabbed number is not valid print an error message
            if(number_request < 1 || number_request > 500){
                println("Not a valid number.")
            }
        }

        magic_utility.number_request = number_request
    }

    /** Gets the raw data from the api and compiles the data.
      * Then returns the data
      *
      * @param raw_data A map of data grabbed from the api
      * @return A map of filtered data from api
      */
    def sorting_data(raw_data:Map[String, Map[String, Any]]):Map[String, Map[String, Int]] = {

        val colorID_maping = Map('R' -> "Red", 'G' -> "Green", 'B' -> "Black", 'U' -> "Blue", 'W' -> "White", 'C' -> "Colorless")
        var compiled_data = Map[String, Map[String, Int]](
            "set" -> Map.empty[String, Int],
            "set_name" -> Map.empty[String, Int],
            "type_line" -> Map.empty[String, Int], 
            "color_identity" -> Map.empty[String, Int],
            "rarity" -> Map.empty[String, Int])

        
        for( card <- raw_data.keys ){

            for( card_details <- raw_data(card)){

                card_details._1 match {
                    case "set" | "set_name" | "rarity" => {
                        if( compiled_data( card_details._1 ).contains( card_details._2.toString().replaceAll("\"", "") ) ){
                            compiled_data( card_details._1 )( card_details._2.toString().replaceAll("\"", "") ) += 1
                        }else{
                            compiled_data( card_details._1 )( card_details._2.toString().replaceAll("\"", "") ) = 1
                        }
                    }
                    case "type_line" => { //String parse by " " then count
                        val card_types = card_details._2.toString().replaceAll("\"", "").split(" ")
                        for(types <- card_types){
                            if(types != raw"—"){ //  ΓÇö
                                if( compiled_data( card_details._1 ).contains( types ) ){
                                    compiled_data( card_details._1 )( types ) += 1
                                }else{
                                    compiled_data( card_details._1 )( types ) = 1
                                }
                            }
                        }
                    }
                    case "color_identity" => {
                        card_details._2.toString().foreach(
                            colorID =>
                            colorID match {
                                case 'R' | 'G' | 'B' | 'U' | 'W' | 'C' => {
                                    if( compiled_data( card_details._1 ).contains( colorID_maping(colorID) ) ){
                                        compiled_data( card_details._1 )( colorID_maping(colorID) ) += 1
                                    }else{
                                        compiled_data( card_details._1 )( colorID_maping(colorID) ) = 1
                                    }
                                }
                                case _ => //println("NOPE - " + colorID)
                            }
                        )
                    }
                    case _ => //println(card_details)
                }
            }
        }

        return compiled_data
    }

}
