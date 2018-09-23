#!/bin/bash

curl --request POST \
  --url http://localhost:8082/topics/results \
  --header 'accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json' \
  --header 'content-type: application/vnd.kafka.json.v2+json' \
  --data '{
	"records": [
		{
			"key" : "monaco_angers",
			"value": { "teamA":"3", "teamB":"1"}
		}
	]
}'
