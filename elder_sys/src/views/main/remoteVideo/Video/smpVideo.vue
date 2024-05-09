<template>
    <img ref="mjpegElement" :src="mjpegUrl" style="width:100%;height:auto;">
</template>

<script>
export default {
  data() {
    return {
      mjpegUrl: 'http://192.168.50.1:8080/?action=stream'
    };
  }
};
</script>
