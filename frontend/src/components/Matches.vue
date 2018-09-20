<template>
  <b-collapse v-if="matchList" class="card">
    <div slot="trigger" slot-scope="props" class="card-header">
      <p class="card-header-title">
        Les matchs
      </p>
      <a class="card-header-icon">
        <i v-if="props.open" class="fa fa-caret-down" aria-hidden="true"></i>
        <i v-else class="fa fa-caret-up" aria-hidden="true"></i>
      </a>
    </div>
    <div class="card-content">
      <div v-if="loaded">
        <div v-if="matches.length > 0">
          <table class="table">
            <thead>
            <tr>
              <th><abbr title="home">Equipe domicile</abbr></th>
              <th><abbr title="away">Equipe visiteur</abbr></th>
              <th><abbr title="date">Date</abbr></th>
              <th><abbr title="date">Actions</abbr></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="match in matches" :key="match.id">
              <td>{{ match.teamA }}</td>
              <td>{{ match.teamB }}</td>
              <td>{{ match.date }}</td>
              <td v-if="hasNoResult(match)"><button  class="button is-block is-info" v-on:click="displayProno(match)">Pronostiquer</button></td>
              <td v-else>{{getResult(match)[0].teamA}} - {{getResult(match)[0].teamB}}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div v-else>
          Aucune competition
        </div>
      </div>
      <div v-else>
        En cours de chargement
      </div>
    </div>
  </b-collapse>
  <b-collapse v-else class="card">
    <div slot="trigger" slot-scope="props" class="card-header">
      <p class="card-header-title">
        Pronostique
      </p>
      <a class="card-header-icon">
        <i v-if="props.open" class="fa fa-caret-down" aria-hidden="true"></i>
        <i v-else class="fa fa-caret-up" aria-hidden="true"></i>
      </a>
    </div>
    <div class="card-content">
      <span>

        <span>
          <label for='scoreHome'>{{ matchToBet.teamA }}</label>
          <input id='scoreHome' class='input' type='text' placeholder='Score domicile' autofocus='' v-model='scoreHome'>
        </span>
      </span>
      <span class="tiret"> - </span>
      <span>
        <span class='control'>
          <input id='scoreAway' class='input' type='text' placeholder='Score visiteur' autofocus='' v-model='scoreAway'>
          <label for='scoreAway'>{{ matchToBet.teamB }}</label>
        </span>
      </span>
      <button class="button is-bottom-left is-cancel" v-on:click="hideProno()">Annuler</button>
      <button class="button is-info" v-on:click="validateProno()">Valider</button>
    </div>
  </b-collapse>
</template>

<script>
import matchesLib from '../service/match'
import resultsLib from '../service/results'
import BCollapse from 'buefy/src/components/collapse/Collapse'
import axios from 'axios'
export default {
  components: {BCollapse},
  created () {
    matchesLib.matches.subscribe(match => {
      this.loaded = true
      this.matches.push(match)
    })
    resultsLib.resultsList.subscribe(result => {
      this.results.push(result)
    })
  },
  name: 'Matches',
  data () {
    return {
      scoreHome: 0,
      scoreAway: 0,
      matchList: true,
      matchToBet: {},
      loaded: false,
      matches: [],
      results: []
    }
  },
  methods: {
    getResult (match) {
      var result = this.results.filter(result => {
        return match.id === result.matchId
      })
      console.log(result)
      return result
    },
    hasNoResult (match) {
      return this.getResult(match).length === 0
    },
    hideProno () {
      this.matchToBet = {}
      this.matchList = true
    },
    displayProno (match) {
      this.matchToBet = match
      this.matchList = false
    },
    validateProno () {
      const user = localStorage.getItem('user-token')
      var data = {
        'records': [
          {
            'value': { 'match': this.matchToBet,
              'scoreHome': this.scoreHome,
              'scoreAway': this.scoreAway,
              'user': user
            }

          }
        ]
      }
      axios.post('http://localhost:8082/topics/prono', data,
        {headers: {'content-type': 'application/vnd.kafka.json.v2+json'}})
        .then(
          (response) => {
            this.matchToBet = {}
            this.matchList = true
            this.scoreAway = 0
            this.scoreHome = 0
          },
          (error) => { console.log(error) })
    }
  }
}
</script>

<style scoped>
  .is-cancel {
    background-color: red;
    color: white;
  }
  span input {
    width:40px;
  }
  span {
    display: inline;
  }
  label {
    vertical-align: -webkit-baseline-middle;
    padding: 0 20px;
  }
  .tiret {
    padding: 0 20px;
  }
</style>
