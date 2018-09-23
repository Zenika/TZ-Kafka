#!/bin/bash

curl --request POST \
  --url http://localhost:8082/topics/match \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
				{
			"key" : "bordeaux_lille",
			"value": { "teamA":"Bordeaux", "teamB":"Lille","date":"26/09/2018 19:00"}
		},
		{
			"key" : "monaco_angers",
			"value": { "teamA":"Monaco", "teamB":"Angers","date":"25/09/2018 9:00" }
		},
        {
					"key":"nantes_nice",
			"value": { "teamA":"Nantes", "teamB":"Nice","date":"25/09/2018 19:00"}
		},
        {
					"key": "toulouse_saint_etienne",
			"value": { "teamA":"Toulouse", "teamB":"Saint Etienne","date":"25/09/2018 19:00"}
		}
	]
}'

curl --request POST \
  --url http://localhost:8082/topics/match \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
		{
			"key" : "nantes_reims",
			"value": { "teamA":"Nantes", "teamB":"Reims","date":"16/09/2018" }
		},
        {
					"key": "bordeaux_nimes",
			"value": { "teamA":"Bordeaux", "teamB":"Nimes","date":"16/09/2018"}
		},
        {
					"key" : "marseille_guingamp",
			"value": { "teamA":"Marseille", "teamB":"Guingamp","date":"16/09/2018"}
		}
	]
}'

curl --request POST \
  --url http://localhost:8082/topics/results \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
		{
			"key": "nantes_reims",
			"value": { "teamA":"0", "teamB":"0"}
		},
        {
					"key": "bordeaux_nimes",
			"value": { "teamA":"3", "teamB":"3"}
		},
        {
					"key": "marseille_guingamp",
			"value": { "teamA":"4", "teamB":"0"}
		}
	]
}'
