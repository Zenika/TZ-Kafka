<template>
  <b-collapse class="card">
    <div slot="trigger" slot-scope="props" class="card-header">
      <p class="card-header-title">
        Les pronostiques
      </p>
      <a class="card-header-icon">
        <i v-if="props.open" class="fa fa-caret-down" aria-hidden="true"></i>
        <i v-else class="fa fa-caret-up" aria-hidden="true"></i>
      </a>
    </div>
    <div class="card-content">
      <div v-if="loaded">
        <div v-if="pronos.length > 0">
          <table class="table">
            <thead>
            <tr>
              <th><abbr title="home">Utilisateur</abbr></th>
              <th><abbr title="away">Equipe domicile</abbr></th>
              <th><abbr title="date">Score domicile</abbr></th>
              <th><abbr title="away">Equipe visiteur</abbr></th>
              <th><abbr title="date">Score visiteur</abbr></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="prono in pronos" :key="prono.offset">
              <td>{{ JSON.parse(prono.user).username }}</td>
              <td>{{ prono.match.teamA }}</td>
              <td>{{ prono.scoreHome }}</td>
              <td>{{ prono.match.teamB }}</td>
              <td>{{ prono.scoreAway}}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div v-else>
          Aucun prono
        </div>
      </div>
      <div v-else>
        En cours de chargement
      </div>
    </div>
  </b-collapse>
</template>

<script>
import BCollapse from 'buefy/src/components/collapse/Collapse'
import pronoService from '../service/prono'
export default {
  components: {BCollapse},
  created () {
    pronoService.pronoList.subscribe(prono => {
      this.loaded = true
      console.log(prono)
      this.pronos.push(prono)
    })
  },
  name: 'Prono',
  data () {
    return {
      loaded: false,
      pronos: []
    }
  }
}
</script>

<style scoped>

</style>
