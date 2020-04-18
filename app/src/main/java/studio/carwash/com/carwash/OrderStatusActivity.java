package studio.carwash.com.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import beans.Carwasher;
import beans.ConsumerAddress;
import beans.Orders;
import beans.Vehicle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import resources.CarConstant;
import resources.RestClient;
import resources.RestInvokerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusActivity extends AppCompatActivity {

    @InjectView(R.id.textViewOrderStatusId) TextView textViewOrderStatusId;
    @InjectView(R.id.textViewOrderStatusDate) TextView textViewOrderStatusDate;
    @InjectView(R.id.textViewOrderStatusAmount) TextView textViewOrderStatusAmount;
    @InjectView(R.id.textViewOrderStatusStatus) TextView textViewOrderStatusStatus;
    @InjectView(R.id.textViewOrderStatusPaymentStatus) TextView textViewOrderStatusPaymentStatus;
    @InjectView(R.id.textViewOrderStatusVehicleName) TextView textViewOrderStatusVehicleName;
    @InjectView(R.id.textViewOrderStatusVehicleNumber) TextView textViewOrderStatusVehicleNumber;
    @InjectView(R.id.textViewOrderStatusAddressLine) TextView textViewOrderStatusAddressLine;
    @InjectView(R.id.textViewOrderStatusAddressLocalityLandmark) TextView textViewOrderStatusAddressLocalityLandmark;
    @InjectView(R.id.textViewOrderStatusAddressTotal) TextView textViewOrderStatusAddressTotal;
    @InjectView(R.id.textViewOrderStatusCarwasherName) TextView textViewOrderStatusCarwasherName;
    @InjectView(R.id.textViewOrderStatusCarwasherPhoneNumber) Button textViewOrderStatusCarwasherPhoneNumber;
    @InjectView(R.id.orderStatusOrder) CardView orderStatusOrder;
    @InjectView(R.id.orderStatusVehicle) CardView orderStatusVehicle;
    @InjectView(R.id.orderStatusAddress) CardView orderStatusAddress;
    @InjectView(R.id.orderStatusCarwasher) CardView orderStatusCarwasher;
    //@InjectView(R.id.order_summary) TextView order_summary;
    @InjectView(R.id.btn_refresh_order) Button btn_refresh_order;

    Gson gson = new Gson();
    public  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        ButterKnife.inject(this);
        this.setTitle("Order Summary");

        loadOrderFromIntent();
    }

    public void loadOrderFromIntent(){
        final Intent intent = getIntent();
        Orders order =null;
        String orderId = null;
        if (intent != null) {
            String orderJson = intent.getStringExtra("orderSelected");
            order = gson.fromJson(orderJson, Orders.class);
            orderId = intent.getStringExtra("orderSelectedId");
        }

        //order_summary.setVisibility(View.GONE);
        orderStatusOrder.setVisibility(View.GONE);
        orderStatusVehicle.setVisibility(View.GONE);
        orderStatusAddress.setVisibility(View.GONE);
        orderStatusCarwasher.setVisibility(View.GONE);
        btn_refresh_order.setVisibility(View.GONE);

        if(orderId != null){
            loadAllDetailsForOrder(Integer.valueOf(orderId));
        } else {
            loadAllDetailsForOrder(order.getOrderId());
        }

        btn_refresh_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderJson = intent.getStringExtra("orderSelected");
                Orders order = gson.fromJson(orderJson, Orders.class);
                String orderId = intent.getStringExtra("orderSelectedId");

                if(orderId != null){
                    loadAllDetailsForOrder(Integer.valueOf(orderId));
                } else {
                    loadAllDetailsForOrder(order.getOrderId());
                }
            }
        });
    }

    public void loadAllDetailsForOrder(Integer id){
        final long time1 = System.currentTimeMillis();
        Log.d("sssasasa", String.valueOf(time1));
        progressDialog =  CarConstant.getProgressDialog(this,"Loading...");
        progressDialog.show();
        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);

        Call<Map<String, Object>> call = restInvokerService.getAllDetailsForOrderId(id);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                Log.d("sssasasa", String.valueOf(System.currentTimeMillis() - time1));
                Log.d("akskasasa",map.toString());
                if( map.get("resCode").equals(200.0)){
                    String ss = map.get("data").toString();
                    Orders orders = gson.fromJson(ss, Orders.class);
                    setAllFields(orders);
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    public void setAllFields(Orders orders){
        if(orders != null){
            //order_summary.setVisibility(View.VISIBLE);
            orderStatusOrder.setVisibility(View.VISIBLE);
            btn_refresh_order.setVisibility(View.VISIBLE);
            textViewOrderStatusId.setText("Order Id: "+String.valueOf(orders.getOrderId()));
            textViewOrderStatusDate.setText("Order Date: "+orders.getOrderDate());
            textViewOrderStatusAmount.setText("Order Amount: ₹ "+String.valueOf(orders.getOrderAmount()));
            textViewOrderStatusStatus.setText("Order Status: "+orders.getOrderStatus());
            textViewOrderStatusPaymentStatus.setText("Payment Status: "+orders.getOrderPaymentStatus());
        }


        Vehicle vehicle = orders.getOrderVehicle();
        if(vehicle != null){
            orderStatusVehicle.setVisibility(View.VISIBLE);
            textViewOrderStatusVehicleName.setText("Vehicle Name: "+vehicle.getVehicleName());
            textViewOrderStatusVehicleNumber.setText("Vehicle Number: "+vehicle.getVehicleNumber());
        }
        ConsumerAddress consumerAddress = orders.getOrderAddress();
        if(consumerAddress != null){
            orderStatusAddress.setVisibility(View.VISIBLE);
            textViewOrderStatusAddressLine.setText(consumerAddress.getAddressLine());
            textViewOrderStatusAddressLocalityLandmark.setText(consumerAddress.getLocality());
            textViewOrderStatusAddressTotal.setText(consumerAddress.getCity()+" "+consumerAddress.getState()+" "+consumerAddress.getPincode());
        }
        Carwasher carwasher = orders.getCarwasher();
        if(carwasher != null){
            orderStatusCarwasher.setVisibility(View.VISIBLE);
            if(carwasher.getName() != null){
                textViewOrderStatusCarwasherPhoneNumber.setVisibility(View.VISIBLE);
                textViewOrderStatusCarwasherName.setText("Carwasher Name: "+carwasher.getName());
                if(orders.getOrderStatus().equals("Completed")){
                    textViewOrderStatusCarwasherPhoneNumber.setVisibility(View.GONE);
                    btn_refresh_order.setVisibility(View.GONE);
                } else {
                    final String phoneNumber = carwasher.getPhoneNumber();
                    textViewOrderStatusCarwasherPhoneNumber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
                            startActivity(intent);
                        }
                    });
                }

            } else {
                textViewOrderStatusCarwasherPhoneNumber.setVisibility(View.GONE);
                textViewOrderStatusCarwasherName.setText("Waiting for your order confirmation! you will get notified once your" +
                        " order is confirmed.");
            }

        }
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadOrderFromIntent();
    }
}
