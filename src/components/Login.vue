<template>
  <div id="login">
    로그인이 안되어있어 ㅠㅠ<br>
    <div id="img" @click="login()">
	    <img class="max-small" v-bind:src="imgSrc">
    </div>
  </div>
</template>

<script>
export default {
  methods:{
    login(){
      location.replace("http://localhost:9000/login")
    }
  },
  data(){
    return {
      imgSrc : "./static/loginBtn.png"
    }
  },
  beforeCreate(){
    let temp = this.$route.query.id;
    if(temp){
      this.$store.accessToken = temp;
      this.$router.push({name:"promises"})
    }
  }
}
</script>