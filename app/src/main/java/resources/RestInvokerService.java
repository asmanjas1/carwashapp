package resources;

import java.util.List;
import java.util.Map;

import beans.Carwasher;
import beans.Consumer;
import beans.ConsumerAddress;
import beans.ConsumerFirebase;
import beans.Orders;
import beans.Vehicle;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInvokerService {

    @POST("/consumerController/signUp")
    Call<Map<String, Object>> createConsumer(@Body Consumer consumer);

    @POST("/consumerController/login")
    Call<Map<String, Object>> loginConsumer(@Body Consumer consumer);

    @POST("/consumerController/saveVehicle")
    Call<Map<String, Object>> addVehicle(@Body Vehicle vehicle);

    @POST("/consumerController/saveaddress")
    Call<Map<String, Object>> addAddress(@Body ConsumerAddress consumerAddress);

    @POST("/orderController/placeOrder")
    Call<Map<String, Object>> placeOrder(@Body Orders orders);

    @GET("/orderController/getOrdersForConsumer/{consumerId}")
    Call<Map<String, Object>> getAllOrdersForConsumer(@Path("consumerId") Integer consumerId);

    @POST("/consumerController/saveFirebaseToken")
    Call<Map<String, Object>> saveFirevaseToken(@Body ConsumerFirebase consumerFirebase);

    @GET("/orderController/getAllDetailsForOrderId/{orderId}")
    Call<Map<String, Object>> getAllDetailsForOrderId(@Path("orderId") Integer orderId);

    @GET("/consumerController/doLoginByNumber/{phoneNumber}")
    Call<Map<String, Object>> doLoginByNumber(@Path("phoneNumber") String phoneNumber);

    @HTTP(method = "DELETE", path = "/consumerController/deleteAddress", hasBody = true)
    Call<Map<String, Object>> deleteAddress(@Body ConsumerAddress consumerAddress);

    @HTTP(method = "DELETE", path = "/consumerController/deleteVehicle", hasBody = true)
    Call<Map<String, Object>> deleteVehicle(@Body Vehicle vehicle);

    @GET("/consumerController/getConsumerById/{consumerId}")
    Call<Map<String, Object>> getConsumerById(@Path("consumerId") Integer consumerId);

    @GET("/orderController/getOrderValue/{vehicleType}")
    Call<Map<String, Object>> getOrderValue(@Path("vehicleType") String vehicleType);

}
