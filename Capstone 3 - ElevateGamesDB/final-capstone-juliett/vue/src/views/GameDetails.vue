<template>
  <div class="page">
    <b-container class="bv-example-row">
      <b-row class="flex-container">
        <b-col class="col-with-padding">
          <div class="box-one">
            <img class="image-one" :src="game.background_image" />
          </div>
        </b-col>

        <b-col class="col-with-padding">
          <div class="box-two centered-box grey-bg">
            <div class="box-two-head">
              <p1 class="title">{{ game.name }}</p1>
              <div class="subtitle">
                <p1 class="genres" v-for="genre in genres" :key="genre.id">{{
                  genre
                }}</p1>
                <p1 class="release-date">{{ game.released }}</p1>
              </div>
              <div class="circle">
                <p1 class="score">{{ game.metacritic }}</p1>
              </div>
            </div>

            <div class="box-two-description">
              <p1 class="descr">
                {{
                  showFullDescription
                    ? game.description_raw
                    : game.description_raw.slice(0, 1000)
                }}
              </p1>
              <b-button
              variant="primary"
                @click="showFullDescription = !showFullDescription"
                v-if="game.description_raw.length > 1000"
              >
                {{ showFullDescription ? "Read Less" : "Read More" }}
              </b-button>
            </div>
          </div>
        </b-col>
      </b-row>
      <div class="w-100"></div>

      <!-- Box Three -->
      <b-row>
        <b-col class="col-with-padding">
          <div class="box-three centered-box grey-bg">
            <div class="group centered-group">
              <div class="value">
                <span class="group-name">Developers:</span>
                <div class="content">
                  <p1 v-for="(developer, index) in developers" :key="index">
                    {{ developer }}
                    <span v-if="index < developers.length - 1">|</span>
                  </p1>
                </div>
              </div>
            </div>
            <div class="group centered-group">
              <div class="value">
                <span class="group-name">Stores:</span>
                <div class="content">
                  <p1 v-for="(store, index) in stores" :key="index">
                    {{ store }}
                    <span v-if="index < stores.length - 1">|</span>
                  </p1>
                </div>
              </div>
            </div>
            <div class="group centered-group">
              <div class="value">
                <span class="group-name">Platforms:</span>
                <div class="content">
                  <p1 v-for="(platform, index) in platforms" :key="index">
                    {{ platform }}
                    <span v-if="index < platforms.length - 1">|</span>
                  </p1>
                </div>
              </div>
            </div>
            <div class="group centered-group">
              <div class="value">
                <span class="group-name">Genres:</span>
                <div class="content">
                  <p1 v-for="(genre, index) in genres" :key="index">
                    {{ genre }}
                    <span v-if="index < genres.length - 1">|</span>
                  </p1>
                </div>
              </div>
            </div>
          </div>

          <!-- "Add to List" Button -->
          <div class="group centered-group">
            <div class="value">
              <div class="content">
                <b-button
                  v-if="userLoggedIn"
                  v-b-modal.game-list-modal
                  @click.stop="setGameId(game.id)"
                >
                  Add to List
                </b-button>
              </div>
            </div>
          </div>
        </b-col>

        <!-- Box Four -->
        <b-col class="col-with-padding">
          <div class="box-four">
            <img
              rounded
              alt="Rounded image"
              class="image-two"
              :src="game.background_image_additional"
            />
          </div>
        </b-col>
      </b-row>
    </b-container>
    <!-- Modal for Adding to List -->
    <b-modal id="game-list-modal">
      <b-button
        v-for="list in listNames"
        :key="list.id"
        @click.prevent="selectList(list)"
      >
        {{ list }}
      </b-button>
    </b-modal>

    <!-- Prop selectedGame in Dashboard -->
    <div v-if="selectedGame" class="game-details">
      <h2>{{ selectedGame.name }}</h2>
    </div>
  </div>
</template>

<script>
import GameService from "../services/GameService";
import ListService from "../services/ListService";

export default {
  name: "game-details",
  props: {
    selectedGame: Object,
  },
  data() {
    return {
      id: "",
      game: {},
      genres: [],
      developers: [],
      platforms: [],
      tags: [],
      stores: [],
      showFullDescription: false,
      userLoggedIn: false,
      listNames: [],
    };
  },
  created() {
    GameService.fetchGameDetails(this.$route.params.id)
      .then((response) => {
        this.game = response.data;
        this.genres = this.game.genres.map((genre) => genre.name);
        this.developers = this.game.developers.map(
          (developer) => developer.name
        );
        this.platforms = this.game.platforms.map(
          (platform) => platform.platform.name
        );
        this.tags = this.game.tags.map((tag) => tag.name);
        this.stores = this.game.stores.map((store) => store.store.name);

        const espanolIndex = this.game.description_raw.indexOf("Español");
        if (espanolIndex !== -1) {
          this.game.description_raw = this.game.description_raw.substring(
            0,
            espanolIndex
          );
        }
      })
      .catch((error) => {
        console.error("Error fetching game details:", error);
      });
    this.id = this.$route.params.id;
    this.$store.commit("SET_CURRENT_GAME", this.id);
    this.userLoggedIn = this.$store.state.token !== "";
    this.fetchLists();
  },
  methods: {
    fetchLists() {
      ListService.retrieveListNames()
        .then((response) => {
          this.listNames = response.data;
        })
        .catch((error) => {
          console.error("Error fetching list names: ", error);
        });
    },
    setGameId(gameId) {
      ListService.retrieveListNames()
        .then((response) => {
          this.listNames = response.data;

          // Open the modal to show the list names for selection
          this.$root.$emit("bv::show::modal", "game-list-modal");

          // Store the selected game id temporarily
          this.selectedGameId = gameId;
        })
        .catch((error) => {
          console.error("Error fetching list names: ", error);
        });
    },
    selectList(list) {
      const gameId = this.selectedGameId;
      ListService.addGameToList(gameId, list)
        .then(() => {
          // Assuming the response indicates a successful addition to the list.
          // You can update the UI, show a success message, etc.

          // Close the modal after adding the game to the list
          this.$root.$emit("bv::hide::modal", "game-list-modal");

          // Clear the selectedGameId after adding the game
          this.selectedGameId = null;
        })
        .catch((error) => {
          console.error("Error adding game to list: ", error);
          // Handle the error and provide appropriate feedback to the user.
        });
    },
  },
};
</script>

<style>


.box-one {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.box-two {
  display: flex;
  justify-content: center;
  height: 100%;
}

.box-two-head {
  display: flex;
  flex-direction: column;
  font-size: 20px;
  font-weight: bold;
}

.box-three {
  text-align: left;
  display: flex;
  justify-content: center;
  align-items: left;
  height: 100%;
}

.box-four {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.image-one,
.image-two {
  max-width: 100%;
  max-height: 100%;
  border-radius: 5px;
}

.circle {
  position: absolute;
  top: 25px;
  right: 20px;
  height: 50px;
  width: 50px;
  background-color: rgb(255, 5, 5);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.score {
  color: black;
  text-align: center;
  width: 100%;
  margin-top: px;
}

.centered-box {
  display: flex;
  flex-direction: column;
  align-items: left;
  text-align: left;
  padding: 30px;
}
.centered-group {
  margin-bottom: 15px;
}
.grey-bg {
  background-color: darkgray;
  padding: 10px;
  border-radius: 5px;
}
.group-name {
  font-weight: bold;
}

.col-with-padding {
  padding: 20px;
}

.subtitle {
  display: flex;
  align-items: center;
  font-size: 15px;
}

.genres {
  margin-right: 5px;
}

.release-date {
  margin: 0;
}

.page {
  background-color: rgb(46, 46, 46);
  font-family: Avenir, Helvetica, Arial, sans-serif;
}
</style>