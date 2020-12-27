<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  @import "./assets/css/main.css";
  @import "./assets/css/color-dark.css";     /*深色主题*/

  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 120px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }
  .table {
    width: 100%;
    font-size: 14px;
  }
  .red {
    color: #ff0000;
  }
  .mr10 {
    margin-right: 10px;
  }

  {
    align:center;
  }
  .table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }
</style>
