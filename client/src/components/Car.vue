<template>
<b-container>
        <section v-if="errored">
            <h5>No cars available yet 😢</h5>
        </section>
        <section v-else>
            <b-row>
                   <div v-if="loading">Loading... 🚗 🚗 🚗</div>
                
                    <b-col cols="4" v-bind:key="carEl.id" v-for="carEl in carObj">
                      <img :src="carEl.car_images" />
                   </b-col>
                   <b-col cols="6" v-bind:key="carEl.id" v-for="carEl in carObj">
                      <h3>{{ carEl.car_title }}</h3>
                      <h4>{{ carEl.car_price }}</h4>
                     
                    </b-col>
                    <b-col cols="2" v-bind:key="carEl.id" v-for="carEl in carObj">
                        <h5>ID: {{ carEl.car_id }}</h5>
                        <h5>Make: {{ carEl.car_make }}</h5>
                        <h5>Model: {{ carEl.car_model }}</h5>
                        <h5>Dealer ID: {{ carEl.dealer_id }}</h5>
                        <router-link  :to="carEl.car_url" >
                          <b-button variant="success">View listing</b-button>
                        </router-link>
                    </b-col>
              
                </b-row>
                <br />
                <b-row>
                      <b-col cols="12">
                      <h3>Description:</h3>
                       <p v-bind:key="carEl.id" v-for="carEl in carObj">{{ carEl.car_description}}</p>
                    </b-col>
        </b-row>
        </section>
    </b-container>
</template>

<script>
import axios from 'axios';
export default {
    data () {
    return {
      carObj: null,
      loading: true,
      errored: false,
    }
  },
  methods: {
    getCar(){
      axios
      .get('http://localhost:3000/cars/'+this.$route.params.id)
      .then(response => this.carObj = response.data)
      .catch(error => {
        console.log(error)
        this.errored = true
      })
      .finally(() => this.loading = false)
    }
  },
  created() {
    this.getCar();
  },
}

</script>
