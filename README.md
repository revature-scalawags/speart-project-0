# Project 0
Does request to a [magic the gathering api](https://scryfall.com/docs/api)  to get a bunch of random cards. This data is filtered and compiled for the user. The data can then be stored in various places. 

<br />

## Setup
    > docker run --name mymongo -p 27017:27017 -d mongo
    Java version 8 or 11
    Scala


<br />

## Compile
> sbt compile

<br />

## Run
Runs the program without args or with
> sbt run

> sbt "run \<flag> \<flag-arg>"

<br />


## Flags
    * -n
        * Number of cards/request to grab from api [1-500]

<br />

## Test
> sbt test

<br />

## Scaladocs
Run this command then path to target/scala\<version>/api
> sbt doc

<br />

## View mongo database
> docker exec -it mymongo bash mongo

    # This tells you your database
    > db 

    # Change to database
    > use mtgdb

    # This tells you the table names
    > db.getCollectionNames() 

    # Grabs all rows from table
    > db.<collection name>.find()
    > db.<collection name>.find().pretty()

    # Use this to kill the table
    > db.mtg.drop() 



