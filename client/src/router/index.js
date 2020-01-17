import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import Cars from '../components/Cars.vue'
// import Car from '../components/Car.vue'
import Listing from '../components/Listing.vue'
import Home from '../components/Home.vue';  
import SearchCar from '../components/SearchCar.vue';  
import Browse from '../components/Browse.vue';  
export default new VueRouter({
  mode: 'history',
  routes: [
    { path: '/home', name: 'Home', component: Home },
    { path: '/cars', name: 'Cars', component: Cars },
    { path: '/listing', name: 'Listing', component: Listing },
    { path: '/cars/:id', component: Listing },
    { path: '/search', component: SearchCar, props: (route) => ({ query: route.query.q }) },
    { path: '/browse', name: 'Browse', component: Browse }
  ]
})