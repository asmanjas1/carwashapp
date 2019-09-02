package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import beans.Consumer;
import beans.ConsumerAddress;
import beans.Orders;
import beans.Vehicle;

public class DummyResponseResultFromRest {

    public static List<Vehicle> getVehicleList(){
        List<Vehicle> orderList = new ArrayList<>();
        Vehicle v1 = new Vehicle(125456973, "KA21 201012","Honda City","Car Suv",new Consumer());
        Vehicle v2 = new Vehicle(125456973, "KA21 201012","Honda City","Car Suv",new Consumer());
        Vehicle v3 = new Vehicle(125456973, "KA21 201012","Honda City","Car Suv",new Consumer());
        Vehicle v4 = new Vehicle(125456973, "KA21 201012","Honda City","Car Suv",new Consumer());
        Vehicle v5 = new Vehicle(125456973, "KA21 201012","Honda City","Car Suv",new Consumer());

        orderList.add(v1);
        orderList.add(v2);
        orderList.add(v3);
        orderList.add(v4);
        orderList.add(v5);

        return orderList;
    }

    public static List<ConsumerAddress> getConsumerAddressList() {
        List<ConsumerAddress> addressList = new ArrayList<>();
        ConsumerAddress add1 = new ConsumerAddress();
        add1.setAddressId(1212121);add1.setAddressLine("2nd Cross, Gandhupuram");
        add1.setCity("Bengalore");add1.setLocality("Satya Sai Layout,Whitefield");
        add1.setState("Karnataka");add1.setPincode(506066);

        ConsumerAddress add2 = new ConsumerAddress();
        add2.setAddressId(1212121);add2.setAddressLine("2nd Cross, Gandhupuram");
        add2.setCity("Bengalore");add2.setLocality("Satya Sai Layout,Whitefield");
        add2.setState("Karnataka");add2.setPincode(506066);

        addressList.add(add1);
        addressList.add(add2);

        return addressList;
    }

    public static List<Orders> getConsumerOrderList1() {
        List<Orders> addressList = new ArrayList<>();
        Orders o1 = new Orders();
        Orders o2 = new Orders();

        o1.setOrderAmount(30d);
        o1.setOrderId(146126);
        //o1.setOrderCompletedDate(new Date());
        Vehicle v = new Vehicle(125456973, "KA21 201012","Honda City","Car Suv",new Consumer());
        List<Vehicle> orderList = new ArrayList<>();
        orderList.add(v);
        Consumer co = new Consumer();
        co.setListOfVehicle(orderList);
        o1.setConsumer(co);

        o2.setOrderAmount(40d);
        o2.setOrderId(1461221);
        //o2.setOrderCompletedDate(new Date());
        o2.setConsumer(co);

        addressList.add(o1);
        addressList.add(o2);

        return addressList;
    }

    public static List<Orders> getConsumerOrderList2() {
        List<Orders> addressList = new ArrayList<>();
        Orders o1 = new Orders();

        o1.setOrderAmount(30d);
        o1.setOrderId(146126);
        //o1.setOrderCompletedDate(new Date());
        Vehicle v = new Vehicle(125456973, "KA21 201014","Honda City Wegon","Car Suv",new Consumer());
        List<Vehicle> orderList = new ArrayList<>();
        orderList.add(v);
        Consumer co = new Consumer();
        co.setListOfVehicle(orderList);
        o1.setConsumer(co);

        addressList.add(o1);

        return addressList;
    }
}
