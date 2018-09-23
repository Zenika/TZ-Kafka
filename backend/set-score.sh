#!/bin/bash

curl --request POST \
  --url http://localhost:8082/topics/score_topic \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
		{
			"key": "nantes_reims",
			"value": { "userId":"Jeffrey", "score":"8"}
		},
				{
			"key": "nantes_reims",
			"value": { "userId":"Logan", "score":"8"}
		},
		{
			"key": "bordeaux_nimes",
			"value": { "userId":"Logan", "score":"8"}
		},
		{
			"key": "marseille_guingamp",
			"value": { "userId":"Logan", "score":"8"}
		}
	]
}'