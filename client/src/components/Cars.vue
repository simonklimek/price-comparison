<template>
    <b-container>
        <h1>Car Listings: {{ cars.length}} results</h1>      
 
        <section v-if="errored">

            <h5>No cars available yet 😢</h5>
        </section>
        <section v-else>
           
            <b-row>
              <br />
                <div v-if="loading">Loading...🚗 🚗 🚗</div>
                <div v-bind:key="car.car_id" v-for="car in displayedCars">
                    <b-col l="4">
                        <b-card 
                        
                        v-bind:title="car.car_id + '  ' + car.car_title"
                        :img-src="car.car_images"
                        img-alt="Thumbnail Car Listing Image"
                        img-top
                        img-fluid
                        img-height="200px"
                        style="max-width: 21.8rem;"
                        class="mb-2 rounded-0">
                        <h5 v-text="car.car_price"></h5> 
                        <b-card-text>{{ `${car.car_description.slice(0,100)}...` }}</b-card-text>
                        <!-- <b-button @click.prevent="getCar" variant="primary">View car</b-button> -->
                        <router-link v-bind:key="car.car_id" :to="`/cars/${car.car_id}`" >
                          <b-button variant="success">View listing</b-button>
                        </router-link>
                    </b-card>
                    </b-col>
                </div>

                  <!-- <div class="overflow-auto">
                    <b-pagination
                      v-model="page"
                      pills
                      :total-rows="cars"
                      :per-page="perPage"
                      first-text="First"
                      prev-text="Prev"
                      next-text="Next"
                      last-text="Last"
                    ></b-pagination>
                  </div> -->
        
        <br />
        <br />
        </b-row>
              <b-row class="justify-content-md-center">
                  <b-col col lg="2">
                    <b-button variant="success"  v-if="page != 1" @click="page--"> Previous page </b-button>
                    </b-col>
                    <b-col col lg="2">
                    <!-- <button type="button" class="btn btn-sm btn-outline-secondary" v-for="pageNumber in pages.slice(page-1, page+5)" @click="page = pageNumber"> {{pageNumber}} </button> -->
                    <b-button variant="success" @click="page++" v-if="page < pages.length" > Next page </b-button>
                </b-col>
        </b-row>
        <br />
        <br />
        </section>
    </b-container>
</template>

<script>
import axios from 'axios';
export default {
    data () {
    return {
        page: 1,
        perPage: 18,
        cars: [],
        // cars: cars,
        // paginationItems: cars.length,
        loading: true,
        errored: false,
        pages: [],
    }
  },
  // filters: {
  //   currencydecimal (value) {
  //     return value.toFixed(2)
  //   }
  // },
  methods: {
    getCars() {
          axios
            .get('http://localhost:3000/cars')
            .then(response => this.cars = response.data)
            .catch(error => {
              console.log(error)
              this.errored = true
            })
            .finally(() => this.loading = false)
    },
    setPages() {
      let numberOfPages = Math.ceil(this.cars.length / this.perPage);
      for (let index = 1; index <= numberOfPages; index++) {
        this.pages.push(index);
      }
    },
    paginate (cars) {
      let page = this.page;
      let perPage = this.perPage;
      let from = (page * perPage) - perPage;
      let to = (page * perPage);
      return  cars.slice(from, to);
    }
  },
  created() {
    this.getCars();
  },
  mounted () {

  },
  watch: {
    cars() {
      this.setPages();
    }
  },
  computed: {
    displayedCars() {
      return this.paginate(this.cars);
    }
  }
};

// https://medium.com/@obapelumi/pagination-with-vuejs-1f505ce8d15b - example above this one used
// https://codesandbox.io/s/5vzmylmol  - important example pagination
          // <!-- https://bootstrap-vue.js.org/docs/components/pagination/ -->
</script>

