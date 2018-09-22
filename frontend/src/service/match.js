import axios from 'axios'
import {Subject} from 'rxjs'
const uuidv4 = require('uuid/v4')

const axiosRequest = axios.create({
  timeout: 1000
})

var matches = new Subject()
export default {
  matches
}

var baseURI = ''
function createConsumer () {
  var data = { 'auto.offset.reset': 'earliest',
    'format': 'json'
  }
  axiosRequest.post('http://localhost:8082/consumers/' + uuidv4(), data,
    {headers: {'content-type': 'application/vnd.kafka.json.v2+json'}})
    .then(
      (response) => {
        // instanceID = response.data.instance_id
        baseURI = response.data.base_uri
        baseURI = baseURI.replace('kafka-rest-proxy', 'localhost')
        subscribeToTopics()
      },
      (error) => { console.log(error) })
}

function subscribeToTopics () {
  var data = {'topics': ['match']}
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
        response.data.map(record => {
          if (record.topic === 'match') {
            matches.next({
              matchId: record.key,
              teamA: record.value.teamA,
              teamB: record.value.teamB,
              date: record.value.date
            })
          }
        })
        setTimeout(getRecords, 1000)
      },
      (error) => {
        console.log(error)
        setTimeout(getRecords, 1000)
      })
}
createConsumer()
