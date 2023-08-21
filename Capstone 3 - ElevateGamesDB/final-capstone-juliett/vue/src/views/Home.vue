<template>
  <img :src="image" alt="Banner Image" class="banner-image" @click="search()" />
</template>

<script>
import image from "../components/Banner.jpg";
export default {
  name: "home",
  components: {},
  data() {
    return {
      image: image,
    };
  },
  methods: {
    search() {
      this.$router.push({
        name: "search",
      });
    },
  },
};
</script>

<style>
.banner-image {
  display: flex;
  align-content: flex-start;
  width: 100vw;
  height: 70vh;
}
</style>