<template>
  <div>
    <div class="filter-container">
      <b-card class="collapse-card">
        <div class="row">
          <div class="column">
            <p class="collapse-card-header">Genres</p>
            <div class="badges">
              <b-badge
                href="#"
                 @click.prevent="emitFilter('genre', 'Action')"
                variant="primary"
                >Action</b-badge
              >
              <b-badge
                href="#"
                 @click.prevent="emitFilter('genre', 'Adventure')"
                variant="success"
                >Adventure</b-badge
              >
              <b-badge
                href="#"
                @click.prevent="emitFilter('genre', 'RPG')"
                variant="danger"
                >RPG</b-badge
              >
              <b-badge
                href="#"
                @click.prevent="emitFilter('genre', 'Shooter')"
                variant="warning"
                >Shooter</b-badge
              >
            </div>
          </div>
          <div class="column">
            <p class="collapse-card-header">Platform</p>
            <div class="badges">
              <b-badge
                href="#"
                @click.prevent="emitFilter('platform', 'PC')"
                variant="danger"
                >PC</b-badge
              >
              <b-badge
                href="#"
               @click.prevent="emitFilter('platform', 'PlayStation')"
                variant="primary"
                >PlayStation</b-badge
              >
              <b-badge
                href="#"
                @click.prevent="emitFilter('platform', 'Xbox')"
                variant="success"
                >Xbox</b-badge
              >
            </div>
          </div>
          <div class="column">
            <p class="collapse-card-header">Tags</p>
            <div class="badges">
              <b-badge
                href="#"
                @click.prevent="emitFilter('tags', 'Singleplayer')"
                variant="primary"
                >Singleplayer</b-badge
              >
              <b-badge
                href="#"
                @click.prevent="emitFilter('tags', 'Multiplayer')"
                variant="success"
                >Multiplayer</b-badge
              >
              <b-badge
                href="#"
                @click.prevent="emitFilter('tags', 'Open World')"
                variant="danger"
                >Open World</b-badge
              >
              <b-badge
                href="#"
                @click.prevent="emitFilter('tags', 'First-Person')"
                variant="warning"
                >First-Person</b-badge
              >
              <b-badge
                href="#"
                @click.prevent="emitFilter('tags', 'Third Person')"
                variant="info"
                >Third Person</b-badge
              >
            </div>
          </div>
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchPageFilterBox",
  data() {
    return {
      showFilterOptions: false,
    };
  },
  methods: {
    emitFilter(filterType, value) {
      switch (filterType) {
        case 'genre':
          this.$emit('filter-by-genre', value);
          break;
        case 'platform':
          this.$emit('filter-by-platform', value);
          break;
        case 'tags':
          this.$emit('filter-by-tags', value);
          break;
        default:
          break;
      }
    },
  },
};

</script>

<style>
.filter-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 1%;
}
.filter-options {
  display: flex;
}
.filter-button-container {
  margin-right: 20px;
}
.column {
  flex: 1;
  margin-right: 20px;
}
.row {
  display: flex;
  flex-wrap: wrap;
  margin: -10px;
}
.collapse-card-header {
  font-weight: bold;
  margin-bottom: 10px;
}
.badges {
  flex: 1;
  display: flex;
  flex-direction: column;
  margin: 10px;
}
.badge {
  margin-bottom: 5px; /* Add margin between badges */
}
.button-container {
  margin-top: 30px;
}
.collapse-card {
    background-color: grey;
}

.filter-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 1%;
  width: 100%; /* Set the container to full width */
}

.column {
  flex: 1;
  margin-right: 20px;
  max-width: 33%; /* Adjust the maximum width of each column */
}

.row {
  display: flex;
  flex-wrap: wrap;
  margin: -10px;
}

.collapse-card {
  background-color: grey;
  width: 80%; /* Adjust the width of the collapse card */
  margin: auto; /* Center the collapse card horizontally */
}

</style>