import Vue from 'vue';

// import axios from 'axios';

// window.axios = require('axios');

import App from './App.vue'
import api from '@/api'
Vue.prototype.$api = api

// import Post from './Post.vue'
// import Navbar from './components/Navbar.vue';  
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import './custom.css'
import router from './router'

Vue.use(BootstrapVue)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router
}).$mount('#app')


