<template>
   <div>
       <br />
       <br />
    <Car />
      <br />
       <br />
    <hr />
      <br />
       <br />
    <CarDealers />
        <br />
       <br />

  </div>

</template>

<script>
import Car from './Car.vue';  
import CarDealers from './CarDealers.vue';  
export default {
    name: 'Listing',
    components: {
      Car,
      CarDealers
    },
    data() {
        return {

        };
    }
}
</script>