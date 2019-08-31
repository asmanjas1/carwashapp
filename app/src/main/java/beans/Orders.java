package beans;

import java.util.Date;

/**
 * Created by Dell on 8/13/2019.
 */

public class Orders {
    private Integer orderId;
    private Date orderDate;
    private Date orderCompletedDate;
    private Double orderAmount;
    private String orderStatus;
    private String orderPaymentStatus;
    private Consumer consumer;

    private Integer orderCarwasherId;
    private Integer orderVehicleId;
    private Integer orderAddressId;

    public Integer getOrderCarwasherId() {
        return orderCarwasherId;
    }

    public void setOrderCarwasherId(Integer orderCarwasherId) {
        this.orderCarwasherId = orderCarwasherId;
    }

    public Integer getOrderVehicleId() {
        return orderVehicleId;
    }

    public void setOrderVehicleId(Integer orderVehicleId) {
        this.orderVehicleId = orderVehicleId;
    }

    public Integer getOrderAddressId() {
        return orderAddressId;
    }

    public void setOrderAddressId(Integer orderAddressId) {
        this.orderAddressId = orderAddressId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderCompletedDate() {
        return orderCompletedDate;
    }

    public void setOrderCompletedDate(Date orderCompletedDate) {
        this.orderCompletedDate = orderCompletedDate;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderPaymentStatus() {
        return orderPaymentStatus;
    }

    public void setOrderPaymentStatus(String orderPaymentStatus) {
        this.orderPaymentStatus = orderPaymentStatus;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

}
