<template>
  <div
    v-loading="isLoading"
    element-loading-text="Đang xử lý..."
    element-loading-spinner="el-icon-loading">
    <span style="font-weight: bold;">Bán tại cửa hàng</span>

    <el-button
      size="small"
      @click="addBill()"
      style="margin-left: 10px;"
    >
      Thêm hóa đơn
    </el-button>

    <el-tabs
      v-model="orderChoose" 
      type="border-card" 
      closable 
      @tab-remove="removeTab" 
      style="box-shadow: none; background-color: #fff; margin-top: 10px; min-height: 100vh;"
    >
      <el-tab-pane
        v-for="o in orders"
        :key="o.id"
        :label="o.fullNameCustomer"
        :name="o.id"
      >
        <Bill :bill="o" :isLoading="isLoading"></Bill>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import Bill from './bill/index.vue'
import { OrderModel } from '@/models/OrderModel';

@Component({
  name: 'SalesAtTheCounter',
  components: {
    Bill
  }
})
export default class extends Vue {
  private isLoading = false;
  private orderChoose = 'HD1';
  private orderIndex = 1;
  private orders: OrderModel[] = [
    {
      ...new OrderModel(),
      id: 'HD1',
      fullNameCustomer: 'Hóa đơn 1'
    }
  ];

  private addBill() {
    const order = new OrderModel();
    order.id = 'HD' + ++this.orderIndex;
    order.fullNameCustomer = 'Hóa đơn ' + this.orderIndex;
    this.orders.push(order);
    this.orderChoose = 'HD' + this.orderIndex;
  }

  private removeTab(targetName: any) {
    let tabs = this.orders;
    let activeName = this.orderChoose;
    if (activeName === targetName) {
      tabs.forEach((tab: any, index: any) => {
        if (tab.id === targetName) {
          let nextTab = tabs[index + 1] || tabs[index - 1];
          if (nextTab) {
            activeName = nextTab.id || '';
          }
        }
      });
    }
    
    this.orderChoose = activeName;
    this.orders = tabs.filter((tab: any) => tab.id !== targetName);
  }

}
</script>

<style lang='scss' scoped>
</style>