<template>
  <div>
    <section class='section home-page'>
      {{this.token.username}}
    </section>
  </div>
</template>

<script>
import '../service/match'

export default {
  name: 'Home',
  data () {
    return {
      get token () {
        return JSON.parse(localStorage.getItem('user-token'))
      }
    }
  }
}
</script>

<style scoped>
  .home-page {
    margin-top: 40px;
  }

</style>
