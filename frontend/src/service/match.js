import axios from 'axios'

const axiosRequest = axios.create({
  timeout: 1000
})

var baseURI = ''
function createConsumer () {
  var data = { 'auto.offset.reset': 'earliest',
    'format': 'json'
  }
  axiosRequest.post('http://localhost:8082/consumers/toto', data,
    {headers: {'content-type': 'application/vnd.kafka.json.v2+json'}})
    .then(
      (response) => {
        console.log(response)
        // instanceID = response.data.instance_id
        baseURI = response.data.base_uri
        baseURI = baseURI.replace('kafka-rest-proxy', 'localhost')
        subscribeToTopics()
      },
      (error) => { console.log(error) })
}

function subscribeToTopics () {
  var data = {'topics': ['match', 'pronostics']}
  axiosRequest.post(baseURI + '/subscription', data,
    {headers: {'content-type': 'application/vnd.kafka.json.v2+json'}})
    .then(
      (response) => {
        getRecords()
      },
      (error) => { console.log(error) })
}

function getRecords () {
  axios.get(baseURI + '/records',
    {headers: {'Accept': 'application/vnd.kafka.json.v2+json'}})
    .then(
      (response) => {
        if (response.data.length === 0) {
          getRecords()
        }
      },
      (error) => { console.log(error) })
}
createConsumer()
