## Back end kafka

Run the docker-compose file `docker-compose.yml` with the following command : 
```
docker-compose -f backend/containers/docker-compose.yml up --build -d
```

Stop it with : 
```
docker-compose -f backend/containers/docker-compose.yml down
```

That docker-compose file was found on the following repo : https://github.com/simplesteph/kafka-stack-docker-compose 