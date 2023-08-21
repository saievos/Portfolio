<template>
  <div id="root">
    <b-card-group columns>
      <b-card
        :title="video.user_name"
        :img-src="ImageOne"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
        v-for="video in videos.slice(0, 1)"
        :key="video"
      >

        <b-button :href="video.url" variant="primary">See Video</b-button>
      </b-card>
      <b-card
        :title="video.user_name"
        :img-src="ImageTwo"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
        v-for="video in videos.slice(1, 2)"
        :key="video"
      >

        <b-button :href="video.url" variant="primary">See Video</b-button>
      </b-card>
      <b-card
        :title="video.user_name"
        :img-src="ImageThree"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
        v-for="video in videos.slice(2, 3)"
        :key="video"
      >

        <b-button :href="video.url" variant="primary">See Video</b-button>
      </b-card>
    </b-card-group>
    <b-card-group columns>
      <b-card
        :title="video.user_name"
        :img-src="ImageFour"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
        v-for="video in videos.slice(3, 4)"
        :key="video"
      >

        <b-button :href="video.url" variant="primary">See Video</b-button>
      </b-card>
      <b-card
        :title="video.user_name"
        :img-src="ImageFive"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
        v-for="video in videos.slice(4, 5)"
        :key="video"
      >

        <b-button :href="video.url" variant="primary">See Video</b-button>
      </b-card>
      <b-card
        :title="video.user_name"
        :img-src="ImageSix"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
        v-for="video in videos.slice(5, 6)"
        :key="video"
      >

        <b-button :href="video.url" variant="primary">See Video</b-button>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
import ListService from "../services/ListService";
import ImageOne from "../assets/gta_1.jpg";
import ImageTwo from "../assets/gta_2.jpg";
import ImageThree from "../assets/gta_3.jpg";
import ImageFour from "../assets/gta_4.jpg";
import ImageFive from "../assets/gta_5.jpg";
import ImageSix from "../assets/gta_6.png";

export default {
  data() {
    return {
      videos: [],
      token: "",
      gameId: "",
      index: 1,
      ImageOne: ImageOne,
      ImageTwo: ImageTwo,
      ImageThree: ImageThree,
      ImageFour: ImageFour,
      ImageFive: ImageFive,
      ImageSix: ImageSix
    };
  },
  methods: {
    parseAccessToken(urlFragment) {
      const regex = /#access_token=([^&]+)/;
      const match = urlFragment.match(regex);
      if (match && match[1]) {
        return match[1];
      } else {
        return null;
      }
    },

    fetchGameVideos() {
      ListService.fetchVideos("32982", this.token)
        .then((response) => {
          this.videos = response.data;
        })
        .catch((error) => {
          console.error("Error fetching videos.", error);
        });
    },

    getPictures(index) {
      return "gta_" + index + ".jpg";
    }
  },
  created() {
    this.token = this.parseAccessToken(this.$route.hash);
    this.fetchGameVideos();
  },
};
</script>

<style>

  #root {
    min-width: 100vw;
    min-height: 100vh;
    background: rgb(46,46,46);
  }

</style>