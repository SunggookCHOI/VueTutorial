import Vue from 'vue';
import App from './App.vue';
import router from './router/router'
import axios from 'axios'
import store from './store/index.js'

Vue.prototype.$http = axios
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')