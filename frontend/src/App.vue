<template>
    <div id="app">
      <nav class="navbar is-fixed-top is-dark" role="navigation" aria-label="main navigation">
        <div class="navbar-brand">
          <router-link class="navbar-item" v-if="this.token" to="/logout" icon="fa-power-off">
            <i class="fa fa-power-off" aria-hidden="true"></i>
          </router-link>
          <router-link class="navbar-item" v-else slot='center' to="/login">Login</router-link>
        </div>
      </nav>
      <router-view/>
    </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      get token () {
        return localStorage.getItem('user-token')
      }
    }
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #app .fa {
    margin: 0 5px
  }

  html {
    background-color: #f5f7fa;
  }

  .section {
    padding: 5rem 1.5rem;
  }

  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }

  .card {
    margin: 30px 0;
    padding-bottom: 30px;
  }
</style>
