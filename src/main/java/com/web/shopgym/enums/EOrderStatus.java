package com.web.shopgym.enums;

public enum EOrderStatus {
    HANGING_INVOICE, // Hóa đơn treo
    WAIT_FOR_CONFIRMATION, //Chờ xác nhận
    PREPARING_GOODS, // Đang chuẩn bị hàng
    SHIPPED, //Đã gửi hàng
    DELIVERING, // Đang giao hàng
    DELIVERED_SUCCESSFULLY, // Đã giao hàng
    SUCCESS, //Thành công
}
