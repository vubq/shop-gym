<template>
  <div id='app'>
    <!-- <AdminLayout v-if="$store.state.auth.status.loggedIn === true && $store.state.auth.user !== null" />
    <router-view v-if="$store.state.auth.status.loggedIn === false && $store.state.auth.user === null" /> -->
    <router-view />
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator';
import AdminLayout from '@/layouts/admin/index.vue';

@Component({
  name: 'App',
  components: {
    AdminLayout
  }
})
export default class extends Vue {
  private user = JSON.parse(localStorage.getItem('user') as any);

  // private created() {
  //   console.log(this.$store.state.auth);
    
  // }
}
</script>

<style lang='scss' scoped>
</style>
