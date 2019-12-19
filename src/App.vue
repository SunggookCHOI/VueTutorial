<template>
  <div id="app">
    <div id="header">
      <Header/>
    </div>
    <div id="promise-list">
      <PromiseList/>
    </div>
  </div>
</template>
 
<script>
import Header from "./components/Header.vue"
import PromiseList from "./components/PromiseList.vue"
export default {
  components : {
    "Header" : Header,
    "PromiseList" : PromiseList
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
  margin-top: 60px;
}
</style>