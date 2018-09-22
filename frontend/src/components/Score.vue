<template>
  <b-collapse class="card">
    <div slot="trigger" slot-scope="props" class="card-header">
      <p class="card-header-title">
        Les scores
      </p>
      <a class="card-header-icon">
        <i v-if="props.open" class="fa fa-caret-down" aria-hidden="true"></i>
        <i v-else class="fa fa-caret-up" aria-hidden="true"></i>
      </a>
    </div>
    <div class="card-content">
      <div v-if="loaded">
        <div v-if="scores.length > 0">
          <b-table
            :data="scores"
            :paginated="false"
            default-sort="date">
            <template slot-scope="props">
              <b-table-column field="teamA" label="Utilisateur" sortable>
                {{ props.row.userId}}
              </b-table-column>

              <b-table-column field="teamB" label="Total Point" sortable>
                {{ props.row.score }}
              </b-table-column>x
            </template>
          </b-table>
        </div>
        <div v-else>
          Aucun score pour le moment
        </div>
      </div>
      <div v-else>
        En cours de chargement
      </div>
    </div>
  </b-collapse>
</template>

<script>
import scoresService from '../service/score'
import BCollapse from 'buefy/src/components/collapse/Collapse'
export default {
  components: {BCollapse},
  created () {
    scoresService.scores.subscribe(score => {
      this.loaded = true
      var currentScore = this.scores.filter(actualScore => actualScore.userId === score.userId)

      if (currentScore.length === 1) {
        var index = this.scores.indexOf(currentScore[0])
        currentScore[0].score = currentScore[0].score + score.score
        this.scores[index] = currentScore[0]
      } else {
        this.scores.push(score)
      }
    })
  },
  data () {
    return {
      loaded: false,
      scores: []
    }
  },
  name: 'Score'
}
</script>

<style scoped>

</style>
