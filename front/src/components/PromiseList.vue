<template>
  <div id="promise-list" v-bind="promises">
    {{this.$store.state.user.name}} 님의 약속 <br>
    <ul>
        <li v-for="promise in promises">
            <div class="prom" @click="$router.push({name:'promiseDetail',params:{promise:promise}})">
                {{promise.promiseName}}<br>
                {{promise.placeName}}<br>
                {{promise.promiseTime}}
            </div>
        </li>
    </ul>
  </div>
</template>

<script>
import Promise from './promise/Promise.vue'
export default {
    methods:{
        getPromise(){
            return this.$http.get("http://localhost:9000/promise",{
                headers: {
                    'Content-Type': 'application/json'
                },
                params : {
                    "userId" : "choi"
                }
            })
            .then( res =>{
                this.promises = res.data
            }).catch(e =>{
                this.promises = e
            })
        }
    },
    data (){
        return {
            promises : []
        }
    },
    created(){
        this.getPromise()
        console.log('asdf')
        console.log(this.$store.accessToken)
    }
}
</script>

<style>
  div.prom {
      border: 2px solid black;
      border-radius: 5px;
      margin : 10px;
  }
  li {
    list-style-type: none;
 }
</style>