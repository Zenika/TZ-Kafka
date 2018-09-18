#!/bin/bash

curl --request POST \
  --url http://localhost:8082/topics/match \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
		{
			"value": { "teamA":"Monaco", "teamB":"Angers","date":"Mardi 25 Septembre 19 heure", "id": "toto"}
		},
        {
			"value": { "teamA":"Nantes", "teamB":"Nice","date":"Mardi 25 Septembre 19 heure", "id": "toto2"}
		},
        {
			"value": { "teamA":"Toulouse", "teamB":"Saint Etienne","date":"Mardi 25 Septembre 19 heure", "id": "toto3"}
		}
	]
}'