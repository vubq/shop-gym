<template>
  <div>
    <el-input placeholder="Please input" v-model="bill.title" ></el-input>
  </div>
</template>

<script lang='ts'>
import { Component, Vue, Prop } from 'vue-property-decorator'

@Component({
  name: 'Bill',
  components: {}
})
export default class extends Vue {
  @Prop() private bill: any;
}
</script>

<style lang='scss' scoped>
</style>