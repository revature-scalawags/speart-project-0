package project.utility

import com.typesafe.scalalogging.LazyLogging
import java.io.{PrintWriter, File}
import com.mongodb.BasicDBObject
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.collection.immutable._
import org.mongodb.scala.bson.collection.mutable.Document
import scala.collection.mutable.Map
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import org.bson.BsonValue
import org.bson.types.ObjectId
import org.bson.BsonObjectId

class Output extends LazyLogging{

    /** Writes writing_data to a csv file.
      *
      * @param writing_data Data taken directly from the api
      * @param filename Name of the csv file you want to write to
      */
    def write_map_to_file(writing_data:Map[String, Map[String, Any]], filename:String){
        //Log system changes
        logger.debug("Writing to a file: " + filename)

        val csv_file = new PrintWriter(new File( filename ))
        for( i <- writing_data.keys){
            for ( j <- writing_data(i).keys ){
                csv_file.write(s"$i, $j, ${writing_data(i)(j)}\n")
            }
        }
        csv_file.close()
    }

    /** Writes compiled data from raw data into a csv file.
      *
      * @param writing_data Data compiled from api data
      * @param filename Filename of what file you want to write to
      */
    def write_map_to_file_int(writing_data:Map[String, Map[String, Int]], filename:String){
        //Log system changes
        logger.debug("Writing to a file: " + filename)

        val csv_file = new PrintWriter(new File( filename ))
        for( i <- writing_data.keys){
            for ( j <- writing_data(i).keys ){
                csv_file.write(s"$i, $j, ${writing_data(i)(j)}\n")
            }
        }
        csv_file.close()
    }

    /** Gets a map then goes through the map to output it's contents to the commandline after an expression is done.
      *
      * @param map_data compiled data from the api
      */
    def write_map_stdout(map_data:Map[String, Map[String, Int]]):Unit = {
        for( i <- map_data.keys){
            var total = 0

            for ( j <- map_data(i).keys ){
                total += map_data(i)(j)
            }

            println("----------------")
            println(s"Total $i: $total")

            for ( j <- map_data(i).keys ){
                println(s"$j: ${ map_data(i)(j) }   \t(${ (map_data(i)(j)/ total.toFloat) * 100}%)")
            }
        }
    }

    /** Adds both maps to the mongo database.
      * 
      * @param raw_data Raw data from api.
      * @param compiled_data Compiled data from raw_data
      */
    def mongo_database_add(raw_data:Map[String, Map[String, Any]], compiled_data:Map[String, Map[String, Int]]):Unit = {
        val client = MongoClient()
        val db = client.getDatabase("mtgdb")

        val doc: org.mongodb.scala.bson.collection.immutable.Document = org.mongodb.scala.bson.collection.immutable.Document("compiled_data" -> map_to_doc(compiled_data), "raw_data" -> map_to_doc_any(raw_data))
        var something = Await.result(db.getCollection("mtg").insertOne(doc).head(), Duration(10, TimeUnit.SECONDS)).getInsertedId()
        //println(something.asObjectId().getValue())

        client.close()
    }

    /** Adds entire map to a document. Documents have documents in them recurivly giving back a map of documents
      * 
      * @param compiled_data Compiled data from raw_data
      * @return A document that is mapped to other docs all containing information from compuiled_data
      */
    def map_to_doc(compiled_data:Map[String, Map[String, Int]]):org.mongodb.scala.bson.collection.immutable.Document = {

        var doc = Document()
        for( i <- compiled_data.keys){
            var doc_1 = Document()
            for ( j <- compiled_data(i).keys ){
                doc_1 += (j -> compiled_data(i)(j))
            }
            doc += (i -> org.mongodb.scala.bson.collection.immutable.Document(doc_1))
        }

        return org.mongodb.scala.bson.collection.immutable.Document(doc)
    }

    /** Adds entire map to a document. Documents have documents in them recurivly giving back a map of documents
      *
      * @param raw_data Data from the api that is a map of strings to maps
      * @return A document that is mapped to other docs all containing information from raw_data
      */
    def map_to_doc_any(raw_data:Map[String, Map[String, Any]]):org.mongodb.scala.bson.collection.immutable.Document = {

        var doc = Document()
        for( i <- raw_data.keys){
            var doc_1 = Document()
            for ( j <- raw_data(i).keys ){
                doc_1 += (j -> raw_data(i)(j).toString())
            }
            doc += (i -> org.mongodb.scala.bson.collection.immutable.Document(doc_1))
        }

        return org.mongodb.scala.bson.collection.immutable.Document(doc)
    }
}
