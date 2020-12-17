# Project 0
Data should be parsed from a CSV, JSON, or other text file format, transform and analyzed, then persist the analysis to a output file or NoSQL database

## Compile
> sbt compile

## Test
> sbt test

## Run
> sbt run


## Make new project with some things in it
> sbt new scala/scalatest-example.g8

## TODO Features
- Documentation (scaladocs, Readme, etc)
- Unit Testing (scalatest)
- Data Persistance (files & NoSQL)
- CLI flags and argument parsing
- Environment variables
- Logging
- Concurrency

> docker run -name mymongo -p 27017:27017 -d mongo
