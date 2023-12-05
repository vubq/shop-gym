<template>
  <div>
    <span style="font-weight: bold;">Bán tại cửa hàng</span>

    <el-button
      size="small"
      @click="addTab()"
      style="margin-left: 10px;"
    >
      Thêm hóa đơn
    </el-button>

    <el-tabs
      v-model="editableTabsValue" 
      type="border-card" 
      closable 
      @tab-remove="removeTab" 
      style="box-shadow: none; background-color: #fff; margin-top: 10px; min-height: 100vh;"
    >
      <el-tab-pane
        v-for="item in editableTabs"
        :key="item.name"
        :label="item.title"
        :name="item.name"
      >
        <Bill :bill="item"></Bill>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import Bill from './bill/index.vue'

@Component({
  name: 'SellAtTheShop',
  components: {
    Bill
  }
})
export default class extends Vue {
  private editableTabsValue: any = 'HD1';
  private editableTabs: any = [{
    title: 'Hóa đơn 1',
    name: 'HD1'
  }];
  private tabIndex: any = 1;

  private addTab() {
    let newTabName = 'HD' + ++this.tabIndex;
    this.editableTabs.push({
      title: 'Hóa đơn ' + this.tabIndex,
      name: newTabName
    });
    this.editableTabsValue = newTabName;
  }

  private removeTab(targetName: any) {
    let tabs = this.editableTabs;
    let activeName = this.editableTabsValue;
    if (activeName === targetName) {
      tabs.forEach((tab: any, index: any) => {
        if (tab.name === targetName) {
          let nextTab = tabs[index + 1] || tabs[index - 1];
          if (nextTab) {
            activeName = nextTab.name;
          }
        }
      });
    }
    
    this.editableTabsValue = activeName;
    this.editableTabs = tabs.filter((tab: any) => tab.name !== targetName);
  }

}
</script>

<style lang='scss' scoped>
</style>