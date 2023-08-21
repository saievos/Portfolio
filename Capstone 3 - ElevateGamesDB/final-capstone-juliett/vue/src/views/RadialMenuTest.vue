<template>
  <div>
    <button @click="toggleRadialMenu">Toggle Radial Menu</button>

    <!-- Simulated Radial Menu Component -->
    <div v-if="showModal" class="radial-menu">
      Radial Menu Content
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showModal: false,
    };
  },
  methods: {
    toggleRadialMenu() {
      this.showModal = !this.showModal;
    },
  },
};
</script>

<style>
.radial-menu {
  background-color: lightgray;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  position: absolute;
  top: 50px;
  left: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999; /* Ensure a high z-index to make the menu appear on top */
}
</style>