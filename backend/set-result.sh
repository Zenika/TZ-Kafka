#!/bin/bash

curl --request POST \
  --url http://localhost:8082/topics/results \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
		{
			"key": "monaco_angers",
			"value": { "teamA":"2", "teamB":"0"}
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
