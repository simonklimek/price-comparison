<template>

    <b-container>
        <h1>Available at:</h1>
          <div v-if="loading">Loading... 🚗 🚗 🚗</div>
          <div>
            <b-table striped hover :items="dealers" v-bind:key="dealers.dealer_id" ></b-table>
          </div>
    </b-container>
</template>

<script>
import axios from 'axios';
export default {
    name: 'CarDealers',
    data() {
      return {
        loading: true,
        errored: false,
        dealers: null,
      }
    },
    methods: {
      getDealers(){
        axios
        .get('http://localhost:3000/dealers')
        .then(response => this.dealers = response.data)
        .catch(error => {
            console.log(error)
            this.errored = true
        })
        .finally(() => this.loading = false)
      }
    },
    created() {
      this.getDealers();
    }
}
</script>
