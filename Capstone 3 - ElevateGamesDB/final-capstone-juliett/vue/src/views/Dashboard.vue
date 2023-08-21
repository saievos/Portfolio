<template>
  <div class="dashboard-container">
    <div class="sidebar">
      <h2 class="sidebar-header">Select a List:</h2>

      <ul class="list-group">
        <b-button
          class="list-group-item"
          v-for="listName in listNames"
          :key="listName"
          :class="{ active: listName === selectedList }"
          @click="handleListSelect(listName)"
        >
          <div class="list-name">
            {{ listName }}

            <button
              v-if="
                !['Wishlist', 'Currently Playing', 'Completed'].includes(
                  listName
                ) && listName !== selectedListToDelete
              "
              @click="showDeleteConfirmation(listName)"
              class="delete-button"
            >
              Delete
            </button>

            <button
              v-if="
                !['Wishlist', 'Currently Playing', 'Completed'].includes(
                  listName
                ) && listName !== selectedListToDelete
              "
              @click="editListName(listName)"
              class="delete-button"
            >
              Edit
            </button>
          </div>
          <div
            v-if="showDeleteModal && selectedListToDelete === listName"
            class="modal-buttons"
          >
            <button @click="deleteList">Yes</button>
            <button @click="cancelDelete">No</button>
          </div>
        </b-button>

        <li>
          <form @submit.prevent="createCustomList">
            <b-form-input
              class="searchbar"
              v-model="customListName"
              placeholder="Enter list name"
            />
            <b-button class="create-list-button" type="submit" variant="primary"
              >Create List</b-button
            >
          </form>
        </li>

        <li>
          <form v-if="edit" @submit.prevent="editList">
            <b-form-input
              class="searchbar"
              v-model="customListName"
              placeholder="New list name"
            />
            <b-button class="create-list-button" type="submit" variant="primary"
              >Edit List</b-button
            >
          </form>
        </li>
      </ul>

      <div class="game-pictures">
        <div
          class="card"
          v-for="game in gamesData"
          :key="game.id"
          @click="handleGameClicked(game)"
        >
          <img
            :src="game.background_image"
            :alt="game.name"
            class="card-img-top"
          />
          <div class="card-body">
            <p class="card-text">{{ game.name }}</p>
            <b-button @click.stop="removeGame(game.id)"
              >Remove From List</b-button
            >
          </div>
        </div>
      </div>

      <div v-if="showDeleteModal" class="custom-modal">
        <div class="modal-content">
          <h2>Delete List</h2>
          <p>Do you want to delete the list "{{ selectedListToDelete }}"?</p>
          <div class="modal-buttons">
            <button @click="deleteList">Yes</button>
            <button @click="cancelDelete">No</button>
          </div>
        </div>
      </div>
    </div>

    <div class="main-content">
      <h1 class="dashboard-header">Dashboard</h1>
      <div v-if="!selectedGame" class="welcome-message">
        <h2>Welcome Back!</h2>
      </div>
      <b-container class="mt-3" v-else>
        <b-row>
          <b-col class="box-one">
            <img
              :src="selectedGame.background_image"
              alt="Game Pic"
              id="image-one"
              class="rounded-img"
            />
          </b-col>
          <b-col>
            <div class="box-two centered-box grey-bg">
              <h2>{{ selectedGame.name }}</h2>
              <div class="box-content">
                <p class="genres" v-for="genre in genres" :key="genre.id">
                  {{ genre }}
                </p>
                <div>
                <a
                  href="https://id.twitch.tv/oauth2/authorize?response_type=token&client_id=03vf45apqn4941sztgy229hmdpvzwj&redirect_uri=http://localhost:8080/twitch_page/&scope=channel%3Amanage%3Apolls+channel%3Aread%3Apolls"
                  >Gameplay Twitch Videos
                </a>
                </div>
              </div>
            </div>
          </b-col>
        </b-row>
        
        <b-row>
          <b-col>
            <div class="box-three centered-box grey-bg">
              <div class="box-content">
                <p class="content-heading">Developers:</p>
                <p class="content-paragraph">{{ developers.join(" | ") }}</p>
              </div>
              <div class="box-content">
                <p class="content-heading">Stores:</p>
                <p class="content-paragraph">{{ stores.join(" | ") }}</p>
              </div>
              <div class="box-content">
                <p class="content-heading">Platforms:</p>
                <p class="content-paragraph">{{ platforms.join(" | ") }}</p>
              </div>
            </div>
          </b-col>

          <b-col class="box-four">
            <img
              :src="selectedGame.background_image_additional"
              alt="Game Pic"
              id="image-one"
              class="rounded-img"
            />
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import GameService from "../services/GameService";
import ListService from "../services/ListService";

export default {
  data() {
    return {
      selectedList: "",
      listNames: [],
      gameIds: [],
      gamesData: [],
      selectedGame: null,
      customListName: "",
      showDeleteModal: false,
      selectedListToDelete: null,
      genres: [],
      developers: [],
      platforms: [],
      tags: [],
      stores: [],
      edit: false,
      changeList: "",
    };
  },
  methods: {
    showGameDetails(game) {
      this.$emit("game-clicked", game);
    },
    handleGameClicked(game) {
      this.selectedGame = game;
      this.genres = this.selectedGame.genres.map((genre) => genre.name);
      this.developers = this.selectedGame.developers.map(
        (developer) => developer.name
      );
      this.platforms = this.selectedGame.platforms.map(
        (platform) => platform.platform.name
      );
      this.tags = this.selectedGame.tags.map((tag) => tag.name);
      this.stores = this.selectedGame.stores.map((store) => store.store.name);
    },

    clearSelectedGame() {
      this.selectedGame = null;
    },

    async handleListSelect(listName) {
      try {
        const response = await ListService.retrieveGameLists(listName);
        this.selectedList = listName;
        this.gameIds = response.data;
        this.populateGamesData();
      } catch (error) {
        console.error("Error fetching game IDs:", error);
      }
    },

    async populateGamesData() {
      const gamesData = await Promise.all(
        this.gameIds.map(async (id) => {
          try {
            const response = await GameService.fetchGameDetails(id);
            return response.data;
          } catch (error) {
            console.error("Error fetching game details:", error);
            return null;
          }
        })
      );

      this.gamesData = gamesData.filter((game) => game !== null);
    },

    async createCustomList() {
      if (this.customListName.trim() === "") {
        return;
      }
      try {
        await ListService.createCustomList(this.customListName);
        this.listNames.push(this.customListName);
        this.customListName = ""; // Clear input field after creating list
      } catch (error) {
        console.error("Error creating custom list:", error);
      }
    },

    showDeleteConfirmation(listName) {
      this.selectedListToDelete = listName;
      this.showDeleteModal = true;
    },

    cancelDelete() {
      this.showDeleteModal = false;
      this.selectedListToDelete = null;
    },

    async deleteList() {
      try {
        await ListService.deleteCustomList(this.selectedListToDelete);
        this.listNames = this.listNames.filter(
          (listName) => listName !== this.selectedListToDelete
        );
        this.cancelDelete(); // Close the modal
      } catch (error) {
        console.error("Error deleting custom list:", error);
      }
    },

    removeGame(id) {
      ListService.deleteGame(id)
        .then((response) => {
          if (response.data != -1) {
            console.log("success");
            // Remove the deleted game from the gamesData array using Vue.delete
            const index = this.gamesData.findIndex((game) => game.id === id);
            if (index !== -1) {
              this.gamesData.splice(index, 1);
            }
          } else {
            console.log("failed");
          }
        })
        .catch((error) => {
          console.error("Error deleting game:", error);
        });
    },
    editListName(listName) {
      this.changeList = listName;
      this.edit = true;
    },
    editList() {
      ListService.editList(this.changeList, this.customListName)
        .then((response) => {
          for (let i = 0; i < this.listNames.length; i++) {
            if (this.listNames[i] == this.changeList) {
              this.listNames[i] = this.customListName;
            }
            response;
          }
        })
        .catch((error) => {
          console.error("Error updating list name:", error);
        });

      this.changeList = "";
      this.customListName = "";
      this.edit = false;
      location.reload();
    },
  },
  mounted() {
    ListService.retrieveListNames()
      .then((response) => {
        this.listNames = response.data;
      })
      .catch((error) => {
        console.error("Error fetching list names:", error);
      });
  },
};
</script>

<style scoped>
.box-content {
  display: flex;
}

#image-one {
  display: flex;
  max-width: 35vw;
  min-height: 50vh;
}

.grey-bg {
  background-color: darkgray;
  padding: 10px;
  border-radius: 5px;
  max-width: 35vw;
  max-height: 50vh;
}

.box-one,
.box-two,
.box-three,
.box-four {
  padding: 20px;
}

.dashboard-container {
  display: flex;
  min-height: 100vh;
  background-color: rgb(46, 46, 46);
}

.sidebar {
  flex: 1;
  padding: 20px;
  border-right: 1px solid #ccc;
}

.main-content {
  flex: 3;
  padding: 20px;
}

.game-pictures {
  margin-top: 20px;
}

.card {
  margin-bottom: 10px; /* Reduce space between cards */
  display: flex; /* Use flex layout to arrange content */
  align-items: center; /* Vertically center content */
}

.card-img-top {
  max-height: 180px; /* Set a maximum height for the image */
  margin-right: 10px; /* Add some spacing between image and text */
  align-self: flex-start; /* Align the image to the top of the card */
  object-fit: cover; /* Crop the image to fit its container */
  object-position: center;
}

.card-body {
  flex: 1; /* Allow the card body to expand and take available space */
}

.card-text {
  margin: 0; /* Remove margin for text to avoid unnecessary spacing */
}

.custom-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
}

.delete-button {
  background-color: red;
  color: white;
  border: none;
  padding: 3px 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.list-group-item {
  background-color: darkgray;
  border-color: rgb(46, 46, 46);
  transition: background-color 0.3s ease;
}

.list-group-item:hover {
  background-color: lightgray;
}

.list-group-item.active {
  background-color: lightblue;
}

.sidebar-header {
  color: darkgray;
}

.dashboard-header {
  color: darkgray;
}

.searchbar {
  margin-top: 10px;
  margin-bottom: 10px;
}

.welcome-message {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh;
}

.welcome-message h2 {
  color: darkgray;
  font-size: 60px;
}

.rounded-img {
  border-radius: 10px; /* Adjust the value as needed */
}

.box-content {
  display: flex;
  margin: 20px;
  gap: 20px;
}

.content-list {
  list-style-type: disc; /* Use bullet points for list items */
  padding-left: 20px; /* Add some left padding to the list */
}

.content-heading {
  font-weight: bold; /* Make the heading text bold */
  margin-bottom: 5px; /* Add a small space below the heading */
}

</style>