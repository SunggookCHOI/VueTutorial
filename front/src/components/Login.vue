<template>
  <div id="login">
    로그인이 안되어있어 ㅠㅠ<br>
    <img class="max-small" v-bind:src="imgKakao" @click="login()">
    <img class="max-small" v-bind:src="imgNaver" @click="loginNaver()">
  </div>
</template>

<script>
import config from '../../config/config.js'

export default {
  methods:{
    login(){
      location.replace(`https://kauth.kakao.com/oauth/authorize?client_id=${config.kakao.key}&redirect_uri=http://localhost:8080/&response_type=code`)
    },
    loginNaver(){
      location.replace("http://localhost:9000/loginNaver")
      // this.$http.get("http://localhost:9000/loginNaver")
      //   .then(res=>{
      //     console.log(res)
      //   })
      //   .catch(e=>{
      //     console.log(e)
      //   })
    }
  },
  data(){
    return {
      imgKakao : "./static/loginBtn.png",
      imgNaver : "./static/loginNaver.png"
    }
  },
  beforeCreate(){
    let temp = this.$route.query.code;
    if(temp){
      this.$http.get(`http://localhost:9000/oauth?code=${temp}`)
        .then(res=>{
          console.log(res.data)
          this.$store.state.user.id=res.data.id
          this.$store.state.user.name=res.data.nickname
        })
        .catch(e=>{
          console.log(e)
        })
      console.log('asadfasdfasdfasdfasfasdfasdf')
      // this.$store.accessToken = temp;
      // this.$router.push({path:"/"})
    }
    if(this.$route.query.naver){
      console.log('asdfasdfasdfasdf')
      this.$http.get(`http://localhost:9000/naverInfo?code=${this.$route.query.naver}`)
        .then(res=>{
          this.$store.state.user.id=res.data.id
          this.$store.state.user.name=res.data.name
          console.log(res)
          this.$router.push({name:"promises"})
        })
        .catch(e=>{console.log(e)})
    }
  }
}
</script>