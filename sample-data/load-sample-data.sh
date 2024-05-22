#!/bin/bash

mongoimport --db greetings --collection greetings --drop --jsonArray --file ./sample-data.json
//imports the json file and its data into the mongo db