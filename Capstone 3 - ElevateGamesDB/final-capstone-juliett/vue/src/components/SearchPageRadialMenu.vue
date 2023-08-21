<template>
  <div>
    <ul class="list-menu">
      <li v-for="list in userLists" :key="list.id" @click="selectList(list)">
        {{ list.name }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    userLists: Array, // User's lists received as a prop
  },
  data() {
    return {};
  },
  methods: {
    selectList(list) {
      this.$emit("list-selected", list); // Emit the selected list
      this.showModal = false; // Hide the radial menu
    },
    getSliceStyle(index) {
      const totalSlices = Math.min(this.userLists.length, 5);
      const sliceAngle = 360 / totalSlices;
      const rotation = index * sliceAngle;
      const sliceColor = `hsl(${index * (360 / totalSlices)}, 70%, 50%)`;

      return {
        transform: `rotate(${rotation}deg) translate(100px) rotate(-${rotation}deg)`,
        backgroundColor: sliceColor,
      };
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
  top: 10px; /* Adjust the top position as needed */
  right: 10px; /* Adjust the right position as needed */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999; /* Ensure a high z-index to make the menu appear on top */
}

/* .list-menu {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  list-style: none;
  padding: 0;
  margin: 0;
}

.list-menu li {
  position: absolute;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  line-height: 100px;
  text-align: center;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.list-menu li:hover {
  background-color: rgba(0, 0, 0, 0.8);
  color: white;
} */
</style>