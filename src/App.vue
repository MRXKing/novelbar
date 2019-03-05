<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted(){
    this.$router.beforeEach((to,from,next) => {

      if (to.matched[1].name == 'novel') {
        this.$store.state.disabled = true
        next()
      }else {
        this.$store.state.disabled = false
        next()
      }
      if (to.matched[1].name == "search" || to.matched[1].name == "bookIndex" || to.matched[1].name == "vBook") {
        $('.footer').css('display','none')
        next()
      }else {
        $('.footer').css('display','block')
        next()
      }

    })
  }
}
</script>

<style>
*
{
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  -webkit-user-drag: none;
}
html,body
{
  height: 100%;
}
#app {
  width: 100%;
  height: 100%;
}
</style>
