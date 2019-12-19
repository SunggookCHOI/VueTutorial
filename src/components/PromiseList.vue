<template>
  <div id="promise-list" v-bind="promises">
    ASDFASDFASDFASF <br>
    <ul>
        <li v-for="promise in promises">
            {{promise.promiseName}}
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
                console.log("asdf")
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
    }
}
</script>