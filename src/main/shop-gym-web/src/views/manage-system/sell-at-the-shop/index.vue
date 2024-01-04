<template>
  <div
    v-loading="isLoading"
  >
    <div style="display: flex; justify-content: space-between;">
      <h3>Bán tại quầy</h3>
      <div style="display: flex; align-items: center; margin-left: 10px;">
        <el-button @click="addBill()">
          <i class="el-icon-plus" />
        </el-button>
      </div>
    </div>

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
        <bill :bill="o" :isLoading.sync="isLoading" @remove-tab="removeTab"></bill>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { ResponseCode, Status } from '../../../enums/enums'
import Bill from './bill.vue'

export default {
  components: {
    Bill
  },
  data() {
    return {
      isLoading: false,
      orderChoose: 'HD1',
      orderIndex: 1,
      orders: [
        {
          id: 'HD1',
          fullNameCustomer: 'Hóa đơn 1',
          type: '',
          phoneNumber: '',
          address: '',
          createdBy: '',
          createdAt: new Date(),
          completedAt: new Date(),
          totalAmount: '',
          voucherId: '',
          status: 'SUCCESS',
          orderDetails: []
        }
      ]
    }
  },
  methods: {
    addBill() {
      const order = {
        id: 'HD' + ++this.orderIndex,
        fullNameCustomer: 'Hóa đơn ' + this.orderIndex,
        type: '',
        phoneNumber: '',
        address: '',
        createdBy: '',
        createdAt: new Date(),
        completedAt: new Date(),
        totalAmount: '',
        voucherId: '',
        status: 'SUCCESS',
        orderDetails: []
      }
      this.orders.push(order)
      this.orderChoose = 'HD' + this.orderIndex
    },
    removeTab(targetName) {
      let tabs = this.orders
      let activeName = this.orderChoose
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.id === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1]
            if (nextTab) {
              activeName = nextTab.id || ''
            }
          }
        })
      }
      this.orderChoose = activeName
      this.orders = tabs.filter((tab) => tab.id !== targetName)
      if(this.orders.length === 0) {
        const order = {
          id: 'HD' + ++this.orderIndex,
          fullNameCustomer: 'Hóa đơn ' + this.orderIndex,
          type: '',
          phoneNumber: '',
          address: '',
          createdBy: '',
          createdAt: new Date(),
          completedAt: new Date(),
          totalAmount: '',
          voucherId: '',
          status: 'SUCCESS',
          orderDetails: []
        }
        this.orders.push(order)
        this.orderChoose = 'HD' + this.orderIndex
      }
    }
  },
  created() {
  }
}
</script>

<style scoped lang="css">
.el-tabs--border-card>.el-tabs__header {
  border-bottom: 0;
}
</style>
