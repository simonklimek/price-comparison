<template>
  <div id="app">
    <Navbar />
    <router-view />
  </div>
</template>

<script>
// import Results from './components/Results.vue'
//   import Cars from './components/Cars.vue'
//   import Car from './components/Car.vue'
  import Navbar from './components/Navbar.vue';  
//   export default {
//     name: 'navbar',
//     components: {
//       Navbar,
//       Cars,
//       Car
//     }
//   }

export default {
  name: 'app',
  components: {
    Navbar
  }
}

</script>





