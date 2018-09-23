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
          <b-table
            :data="pronos"
            :paginated="false"
            default-sort="date">
            <template slot-scope="props">
              <b-table-column field="teamA" label="Utilisateur" sortable>
                {{ JSON.parse(props.row.user).username}}
              </b-table-column>

              <b-table-column field="teamB" label="Equipe domicile" sortable>
                {{ props.row.match.teamA }}
              </b-table-column>

              <b-table-column field="teamB" label="Score domicile" sortable>
                {{ props.row.scoreHome }}
              </b-table-column>

              <b-table-column field="teamB" label="Equipe visiteuse" sortable>
                {{ props.row.match.teamB }}
              </b-table-column>

              <b-table-column field="teamB" label="Score visiteur" sortable>
                {{ props.row.scoreAway}}
              </b-table-column>

              <b-table-column field="date" label="Date du prono" sortable centered>
                {{ props.row.date }}
              </b-table-column>
            </template>
          </b-table>
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
