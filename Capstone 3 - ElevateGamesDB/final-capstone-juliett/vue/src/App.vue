<template>
  <div id="app">
    <b-navbar type="dark" variant="dark">
      <b-navbar-brand>
        <img
          :src="require('@/components/Logo.jpg')"
          alt="Logo Image"
          class="logo-image"
        />
        <span class="logo-text">ElevateGamesDB</span>
      </b-navbar-brand>
      <b-navbar-nav class="ml-auto mr-3 d-flex align-items-center">
        <b-nav-item :to="{ name: 'home' }" v-if="this.$route.path != '/'"
          >Home</b-nav-item
        >
        <b-nav-item
          :to="{ name: 'dashboard' }"
          v-if="
            isLoggedIn &&
            this.$route.path != '/dashboard' &&
            this.$route.path != '/login'
          "
          >Dashboard</b-nav-item
        >
        <b-nav-item
          :to="{ name: 'search' }"
          v-if="this.$route.path != '/search'"
          >Find Your Next Game</b-nav-item
        >
        <b-nav-item
          :to="{ name: 'login' }"
          v-if="!isLoggedIn && this.$route.path != '/login'"
          >Login</b-nav-item
        >
        <b-nav-item :to="{ name: 'logout' }" v-if="isLoggedIn"
          >Logout</b-nav-item
        >
      </b-navbar-nav>
    </b-navbar>
    <router-view />
    <footer class="footer">
      <p>
        2023 ElevateGamesDB. All rights reserved.
        <img
          src="@/components/twitter.jpg"
          alt="Twitter Image"
          class="footer-image"
        />
        <img
          src="@/components/facebook.jpg"
          alt="Facebook Image"
          class="footer-image"
        />
      </p>
      <div class="social-media"></div>
    </footer>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      searchQuery: "",
      searchResults: [],
      gameDetails: null, // To store the details of the fetched game
    };
  },
  methods: {
    search() {
      this.$router.push({ name: "search" });
    },
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token;
    },
  },
};
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  background: rgb(46, 46, 46);
  min-height: 100vh;
}
.logo-image {
  width: 50px;
  height: auto;
  margin-right: 20px;
}
.logo-text {
  font-size: 1.2rem; /* Adjust the font size as needed */
  font-weight: bold; /* Set the font weight */
  color: white; /* Set the font color */
}
.footer {
  display: flex;
  padding: 10px;
  justify-content: center;
  color: white;
  margin-top: auto;
}
.footer-content {
  display: flex;
  align-items: center;
  width: 100%;
}
.social-media {
  display: inline;
}
.footer-image {
  height: 30px;
  margin-left: 10px;
}
</style>