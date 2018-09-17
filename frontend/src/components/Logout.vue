<template>
  <div></div>
</template>

<script>
export default {
  name: 'logout',
  created () {
    localStorage.removeItem('user-token')
    this.$router.push('/')
  }
}
</script>

<style scoped>

</style>
