<template>
  <div id="app">
    <h1 v-text="title"></h1>
    <input v-model="newItem" @keyup.enter="addNew"/>
    <ul>
      <li v-for="item in items" v-bind:class="{finished: item.isFinished}" v-on:click="toggleFinish(item)">{{item.lable}}</li>
    </ul>
  </div>
</template>

<script>
import Store from './store'
export default {
  data: function () {
    return {
      title: 'this is a todo list',
      items: Store.fetch() || [],
      newItem: ''
    }
  },
  watch: {
    items: {
      handler: function (items) {
        Store.save(items)
      },
      deep: true
    }
  },
  methods: {
    toggleFinish: function (item) {
      item.isFinished = !item.isFinished
    },
    addNew: function () {
      console.log(this.newItem);
      this.items.push({
        lable: this.newItem,
        isFinished: false
      })
      this.newItem = ''
    }
  }
}
</script>

<style>
  .finished{
    text-decoration: line-through;
  }
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
