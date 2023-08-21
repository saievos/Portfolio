<template>
  <div class="page">
    <div class="search-filter-wrapper">
    <form @submit.prevent="searchByTitle()">
      <b-input
        class="searchBar"
        type="text"
        v-model="query"
        placeholder="Search Games by Names"
      />
      <div class="filter-box">
      <b-button
        size="sm"
        class="my-2 my-sm-0"
        type="button"
        @click="refreshGames()"
        v-if="this.filteredGames.length != 0"
        >Refresh Games</b-button
      >

      <!-- SearchPageFilterBox Component -->
      <search-page-filter-box
        @filter-by-genre="filterByGenre"
        @filter-by-platform="filterByPlatform"
        @filter-by-tags="filterByTags"
      />
      </div>
    </form>
    </div>
    <div class="card-container">
      <b-card-group
        class="cardGroup"
        deck
        v-if="filteredGames.length === 0"
      >
        <b-card
          :title="game.name"
          :img-src="game.background_image"
          img-alt="Image"
          img-top
          :class="getGenreBorderClass(game.genres)"
          v-for="game in games"
          :key="game.id"
          @click="fetchGameDetails(game.id)"
        >
          <b-card-text>Playtime: {{ game.playtime }} Hours</b-card-text>

          <div class="add-to-list-container">
            <b-button
              v-if="userLoggedIn"
              v-b-modal.game-list-modal
              @click.stop="setGameId(game.id)"
            >
              Add to List
            </b-button>
          </div>
        </b-card>
      </b-card-group>

      <b-card-group
        class="cardGroup"
        deck
        v-if="filteredGames.length !== 0"
      >
        <b-card
          :title="filteredGames.name"
          :img-src="filteredGames.background_image"
          img-alt="Image"
          img-top
          :class="getGenreBorderClass(filteredGames.genres)"
          v-for="filteredGames in filteredGames"
          :key="filteredGames.id"
          @click="fetchGameDetails(filteredGames.id)"
        >
          <b-card-text>Playtime: {{ filteredGames.playtime }} Hours</b-card-text>

          <div class="add-to-list-container">
            <b-button
              v-if="userLoggedIn"
              v-b-modal.game-list-modal
              @click.stop="setGameId(filteredGames.id)"
            >
              Add to List
            </b-button>
          </div>
        </b-card>
      </b-card-group>
      <b-modal id="game-list-modal">
        <b-button
          v-for="list in listNames"
          :key="list.id"
          @click.prevent="selectList(list)"
        >
          {{ list }}
        </b-button>
      </b-modal>
    </div>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">Previous</button>
      <span>{{ currentPage }}</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages">
        Next
      </button>
    </div>
  </div>
</template>

<script>
import SearchPageFilterBox from "../components/SearchPageFilterBox.vue";
import GameService from "../services/GameService";
import ListService from "../services/ListService";

export default {
  name: "search-page",
  components: {
    SearchPageFilterBox,
  },
  data() {
    return {
      query: "",
      games: [],
      platforms: [],
      filteredGames: [],
      gameId: null,
      selectedList: null,
      listNames: [],
      currentPage: 1,
      totalPages: 1,
    };
  },
  computed: {
    userLoggedIn() {
      return this.$store.state.token !== "";
    },
    filterGames() {
      if (this.query === "") {
        return this.games;
      }
      const lowerQuery = this.query.toLowerCase();
      return this.games.filter((game) =>
        game.name.toLowerCase().includes(lowerQuery)
      );
    },
  },
  methods: {
    fetchGames() {
      GameService.fetchGames({
        page: this.currentPage,
        page_size: 120,
      })
        .then((response) => {
          this.games = response.data.results;
          this.totalPages = Math.ceil(response.data.count / 120); // Calculate total pages
          this.applyFilters(); // Apply filters after fetching games
        })
        .catch((error) => {
          console.error("Error fetching games:", error);
        });
    },

    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchGames();
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages + 1) {
        this.currentPage++;
        this.fetchGames();
      }
    },

    fetchGameDetails(id) {
      this.$router.push({ name: "game_details", params: { id: id } });
    },
    searchByTitle() {
      this.filteredGames = this.games.filter((game) => {
        return game.name.toLowerCase().includes(this.query.toLowerCase());
      });

      this.query = "";
    },

    applyFilters() {
      this.filteredGames = this.games.filter((game) => {
        return (
          this.filterByGenre(game) &&
          this.filterByPlatform(game) &&
          this.filterByTags(game)
        );
      });
    },
    updateFilteredGames() {
      this.filteredGames = this.games.filter((game) => {
        return (
          this.filterByGenre(game) &&
          this.filterByPlatform(game) &&
          this.filterByTags(game)
        );
      });
    },

    filterByGenre(selectedGenre) {
      this.refreshGames();

      this.games.forEach((game) => {
        game.genres.forEach((genre) => {
          if (genre.name == selectedGenre) {
            this.filteredGames.push(game);
          }
        });
      });
    },
    filterByPlatform(selectedPlatform) {
      this.refreshGames();

      this.games.forEach((game) => {
        game.parent_platforms.forEach((platform) => {
          if (platform.platform.name == selectedPlatform) {
            this.filteredGames.push(game);
          }
        });
      });
    },
    filterByTags(selectedTag) {
      this.refreshGames();

      this.games.forEach((game) => {
        game.tags.forEach((tag) => {
          if (tag.name == selectedTag) {
            this.filteredGames.push(game);
          }
        });
      });
    },

    refreshGames() {
      this.filteredGames = [];
    },

    getGenreBorderClass(genres) {
      const genreClassMapping = {
        Action: "border-danger",
        Adventure: "border-success",
        RPG: "border-warning",
        Shooter: "border-dark",
      };

      const defaultClass = "border-primary"; // Fallback class

      for (const genre of genres) {
        if (genre.name in genreClassMapping) {
          return genreClassMapping[genre.name];
        }
      }

      return defaultClass;
    },

    toggleRadialMenu() {
      this.showModal = !this.showModal;
      if (!this.showModal) {
        this.selectedList = null; // Clear the selected list when hiding the menu
      }
    },

    addGameToList(gameId, selectedList) {
      const gameIdToAdd = gameId;
      GameService.addGameToList(gameIdToAdd, selectedList.id)
        .then(() => {
          // Handle success
          // Close the modal and radial menu
          this.showModal = false;
          this.selectedList = null; // Clear the selected list
        })
        .catch(() => {
          // Handle error
          // Close the modal and radial menu
          this.showModal = false;
          this.selectedList = null; // Clear the selected list
        });
    },

    fetchLists() {
      ListService.retrieveListNames()
        .then((response) => {
          this.listNames = response.data;
        })
        .catch((error) => {
          console.error("Error fetching list names: ", error);
        });
    },
    setGameId(id) {
      this.gameId = id;
    },
    selectList(list) {
      ListService.addGameToList(this.gameId, list)
        .then((response) => {
          if (response.data === -1) {
            console.log("Duplicate List Value");
          } else {
            console.log("Success");
          }
        })
        .error((error) => {
          console.error("Error adding to list: ", error);
        });
    },
  },
  created() {
    this.fetchGames();
    this.fetchLists();
    this.updateFilteredGames();
  },
};
</script>

<style>
.page {
  background-color: rgb(46, 46, 46);
  padding: 30px 30px;
}
.searchBar {
  width: 80%; /* Adjust the width as needed */
  margin: 20px 20px 10px 5px; /* Add margin for spacing, adjust left margin (50px in this case) */
}
.button-container {
  margin-top: 30px;
}
.card-container {
  margin-top: 30px;
  margin-right: 30px;
}
.cardGroup {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 25px 5px;
}
.card img {
  width: 100%;
  height: 300px;
  object-fit: cover;
}
.border-danger {
  border-color: var(--danger);
}
.border-success {
  border-color: var(--success);
}
.border-warning {
  border-color: var(--warning);
}
.border-dark {
  border-color: var(--dark);
}
.collapse-card {
  border: none !important;
}
.collapse-card-header {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  font-weight: bold;
}

.search-filter-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.cardGroup {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 25px 15px;
}

.card {
  width: 100%;
}

.filter-box {
  display: flex;
  flex-direction: row;
  align-items: center;
}

</style>
