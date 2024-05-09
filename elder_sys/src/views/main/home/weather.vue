<template>
  <div class="box">
    <div id="he-plugin-standard"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },
  created() {
    window.WIDGET = {
      CONFIG: {
        layout: '1',
        width: 400,
        height: 150,
        background: '1',
        dataColor: 'FFFFFF',
        borderRadius: '5',
        key: '04c1df5a350e448cb2b2728f1b435f88',
      },
    }
    let script = document.createElement('script')
    script.src =
      'https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0'
    document.getElementsByTagName('head')[0].appendChild(script)
  },
  methods: {},
}
</script>

<style scoped></style>
