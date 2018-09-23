<template>
    <section class="section">
      <div class="container has-shadow has-text-centered">
        <div class="column is-4 is-offset-4">
          <h3 class="title has-text-grey">Authentification</h3>
          <div class="box">
              <div class="field">
                <div class="control">
                  <input class="input is-large" type="username" placeholder="Votre pseudo" autofocus="" v-model="username">
                </div>
              </div>
              <button class="button is-block is-info is-large is-fullwidth" v-on:click="login">Connexion</button>
          </div>
        </div>
      </div>
    </section>
</template>

<script>

export default {
  name: 'login',
  data () {
    return {
      password: '',
      username: '',
      isComponentModalActive: false,
      email: '',
      pseudo: '',
      teamName: ''

    }
  },
  methods: {
    redirect: function () {
      this.$router.push('/')
    },
    login: function () {
      const data = { 'username': this.username }
      localStorage.setItem('user-token', JSON.stringify(data))
      this.redirect()
    }
  }
}
</script>

<style scoped>

  .box {
    margin-top: 5rem;
  }
  input {
    font-weight: 300;
  }
  p {
    font-weight: 700;
  }
</style>
