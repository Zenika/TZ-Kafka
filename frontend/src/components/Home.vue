<template>
  <div>
    <section class='section home-page'>
      <matches></matches>
      <prono></prono>
      <score></score>
    </section>
  </div>
</template>

<script>
import '../service/match'
import matches from './Matches'
import prono from './Prono'
import score from './Score'

export default {
  components: {
    'Matches': matches,
    'Prono': prono,
    'Score': score
  },
  name: 'Home',

  data () {
    return {
      loaded: false,
      get token () {
        return JSON.parse(localStorage.getItem('user-token'))
      }
    }
  }
}
</script>

<style scoped>
</style>
