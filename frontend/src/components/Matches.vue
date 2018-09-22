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
          <b-table
            :data="matches"
            :paginated="false"
            default-sort="date">
            <template slot-scope="props">
              <b-table-column field="teamA" label="Equipe domicile" sortable>
                {{ props.row.teamA }}
              </b-table-column>

              <b-table-column field="teamB" label="Equipe visiteur" sortable>
                {{ props.row.teamB }}
              </b-table-column>

              <b-table-column field="date" label="Date" sortable centered>
                {{ props.row.date }}
              </b-table-column>
              <b-table-column field="date" label="Score" sortable centered>
                <button v-if="isNotYetPronostiquable(props.row)" class="button is-block is-gray">Pronostiquer</button>
                <button v-else-if="isPronostiquable(props.row)" class="button is-block is-info" v-on:click="displayProno(props.row)">Pronostiquer</button>
                <div v-else-if="!hasNoResult(props.row)" class ="has-text-centered">{{getResult(props.row)[0].teamA}} - {{getResult(props.row)[0].teamB}}</div>
              </b-table-column>
            </template>
          </b-table>
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
import moment from 'moment'
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
    isNotYetPronostiquable (match) {
      return this.hasNoResult(match) && moment(match.date, 'DD/MM/YYYY HH:mm:ss').subtract(1, 'd').isAfter(moment())
    },
    isPronostiquable (match) {
      return this.hasNoResult(match) && moment(match.date, 'DD/MM/YYYY HH:mm:ss').isAfter(moment())
    },
    getResult (match) {
      var result = this.results.filter(result => {
        return match.matchId === result.matchId
      })
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
              'user': user,
              'date': moment().format('DD/MM/YYYY HH:mm:ss')
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
  .is-gray {
    background-color:lightgray;
  }
  .button {
    margin: 0 auto;
  }
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
