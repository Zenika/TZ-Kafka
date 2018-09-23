import axios from 'axios'
import {Subject} from 'rxjs'
const uuidv4 = require('uuid/v4')

const axiosRequest = axios.create({
  timeout: 1000
})

var pronoList = new Subject()
export default {
  pronoList
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
        baseURI = baseURI.replace('rest-proxy', 'localhost')
        subscribeToTopics()
      },
      (error) => { console.log(error) })
}

function subscribeToTopics () {
  var data = {'topics': ['prono']}
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
          if (record.topic === 'prono') {
            pronoList.next({
              matchId: record.key,
              match: record.value.match,
              scoreHome: record.value.scoreHome,
              scoreAway: record.value.scoreAway,
              date: record.value.date,
              user: record.value.user
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
