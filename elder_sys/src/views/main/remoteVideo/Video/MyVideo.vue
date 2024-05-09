<template>
  <div>
    <video ref="videoElement" controls style="width: 100%; height: auto;"></video>
  </div>
</template>

<script>
import flvjs from 'flv.js';

export default {
  mounted() {
    if (flvjs.isSupported()) {
      const videoElement = this.$refs.videoElement;
      const flvPlayer = flvjs.createPlayer({
        type: 'flv',
        url: 'http://81.70.100.114:7789/live/livestream.flv'
      });
      flvPlayer.attachMediaElement(videoElement);
      flvPlayer.load();
    }
  }
};
</script>

