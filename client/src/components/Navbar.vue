<template>
    <div>
      <b-navbar toggleable="lg" type="dark" variant="success">
        <b-container>
          <router-link :to="{ name: 'Home' }" class="nav-link">
            <b-navbar-brand>
              <img src="../assets/logo.png" width="320px"/></b-navbar-brand>
            </router-link>
            <b-nav-item>
              <ul class="navbar-nav">
                  <li class="nav-item">
                    <router-link :to="{ name: 'Home' }" class="nav-link">Home</router-link>
                  </li>
                  <li class="nav-item">
                    <router-link :to="{ name: 'Cars' }" class="nav-link">Cars</router-link>
                  </li>
                  <li class="nav-item">
                    <router-link :to="{ name: 'Listing' }" class="nav-link">Listing</router-link>
                  </li>
                  <li class="nav-item">
                    <router-link v-for="routes in links" v-bind:key="routes.id" :to="`${routes.page}`" class="nav-link">{{routes.text}}</router-link>
                  </li>
                    <li class="nav-item">
                    <router-link :to="{ name: 'Browse' }" class="nav-link">Browse Dealers</router-link>
                  </li>
              </ul>
            </b-nav-item>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav>
              <!-- Right aligned nav items -->
              <b-navbar-nav class="ml-auto">
                <b-nav-form>
                  <b-form-input 
                    size="sm" 
                    class="mr-sm-2" 
                    placeholder="Search for a car"
                    v-model="car">
                  </b-form-input>
                  <b-button 
                    size="sm" 
                    class="my-2 my-sm-0" 
                    type="submit" 
                    @click.prevent="getCar"
                    >Search</b-button>
                </b-nav-form>
                <!-- <b-nav-item-dropdown right>
                  <template slot="button-content"><em>User</em></template>
                  <b-dropdown-item href="#">Profile</b-dropdown-item>
                  <b-dropdown-item href="#">Sign Out</b-dropdown-item>
                </b-nav-item-dropdown> -->
              </b-navbar-nav>
            </b-collapse>
          </b-container>
      </b-navbar>
    </div>  
    
</template>

<script>
export default {
    data() {
        return {
            car: '',
             links: [ 
              { 
                id: 0,
                text: 'Hello World Page', 
                page: '/Home' 
              } 
    ] 
        }
    },
  methods: {
    getCar() {
      //...
    }
  }
}    
// https://blog.logrocket.com/getting-started-with-bootstrapvue-2d8bf907ef11/
</script>