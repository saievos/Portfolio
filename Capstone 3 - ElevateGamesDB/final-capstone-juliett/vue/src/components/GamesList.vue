<template>
  <div>
    <b-card-group deck>
      <b-card v-for="game in games.slice(0, 10)" :key="game.id">
        <b-card-text>
          {{ game.name }}
          <img class="game_image" :src="game.background_image" />
        </b-card-text>
        <b-button @click="fetchGameDetails(game.id)">
          View Game Details
        </b-button>
        <template #footer>
          <small class="text-muted">Last updated 3 mins ago</small>
        </template>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
// import axios from "axios";
import GameService from '../services/GameService'

export default {
  name: 'games-list',
  data() {
    return {
      games: [],
    };
  },
  mounted() {
    this.fetchGames();
  },
  methods: {
    fetchGames() {
      GameService.fetchGames().then(response => {
        this.games = response.data.results;
        this.$store.commit('SET_GAMES_LIST', this.games);
      }).catch(error => {
        console.error("Error fetching games:", error);
      });
    },
    fetchGameDetails(id){
      this.$router.push({name: 'game_details', params:{id: id}});
    }
  },
};
</script>

<style>
.game_image {
  width: 25%;
  height: auto;
}
</style>