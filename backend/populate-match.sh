#!/bin/bash

curl --request POST \
  --url http://localhost:8082/topics/match \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
				{
			"value": { "teamA":"Bordeaux", "teamB":"Lille","date":"26/09/2018 19:00", "id": "bordeaux_lille"}
		},
		{
			"value": { "teamA":"Monaco", "teamB":"Angers","date":"25/09/2018 9:00", "id": "monaco_angers"}
		},
        {
			"value": { "teamA":"Nantes", "teamB":"Nice","date":"25/09/2018 19:00", "id": "nantes_nice"}
		},
        {
			"value": { "teamA":"Toulouse", "teamB":"Saint Etienne","date":"25/09/2018 19:00", "id": "toulouse_saint_etienne"}
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
			"value": { "teamA":"Nantes", "teamB":"Reims","date":"16/09/2018", "id": "nantes_reims"}
		},
        {
			"value": { "teamA":"Bordeaux", "teamB":"Nimes","date":"16/09/2018", "id": "bordeaux_nimes"}
		},
        {
			"value": { "teamA":"Marseille", "teamB":"Guingamp","date":"16/09/2018", "id": "marseille_guingamp"}
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
			"value": { "teamA":"0", "teamB":"0", "matchId":"nantes_reims"}
		},
        {
			"value": { "teamA":"3", "teamB":"3", "matchId": "bordeaux_nimes"}
		},
        {
			"value": { "teamA":"4", "teamB":"Guingamp","matchId": "marseille_guingamp"}
		}
	]
}'