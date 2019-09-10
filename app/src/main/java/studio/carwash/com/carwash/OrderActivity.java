package studio.carwash.com.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import beans.Consumer;
import beans.ConsumerAddress;
import beans.Orders;
import beans.Vehicle;
import consumer.adapter.ConsumerAddressAdapter;
import resources.CarConstant;
import resources.RestClient;
import resources.RestInvokerService;
import resources.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    TextView textViewCarName,textViewCarNumber,textViewSelectAddress,textViewSelectedAddress,
    textViewAddressLineSelected,textViewAddressLocalityLandmarkSelected,textViewAddressTotalSelected;
    CardView cardViewSelectedAddress;
    Gson gson = new Gson();
    Button btnAddYourAddress,btn_placeOrder;
    List<ConsumerAddress> addressList;
    RecyclerView recyclerViewAddress;
    ConsumerAddress selectedAddress;
    Vehicle selectedVehicle;


    public  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        this.setTitle("Order");

        Intent intent = getIntent();
        Vehicle vehicle =null;
        if (intent != null) {
            String vehicleJson = intent.getStringExtra("vehicleSelected");
            vehicle = gson.fromJson(vehicleJson, Vehicle.class);
        }

        textViewCarName = (TextView) findViewById(R.id.textViewCarName);
        textViewCarNumber = (TextView) findViewById(R.id.textViewCarNumber);
        textViewSelectAddress = (TextView) findViewById(R.id.select_address);
        textViewSelectedAddress = (TextView) findViewById(R.id.textViewSelected_Address);

        textViewAddressLineSelected = (TextView) findViewById(R.id.textViewAddressLineSelected);
        textViewAddressLocalityLandmarkSelected = (TextView) findViewById(R.id.textViewAddressLocalityLandmarkSelected);
        textViewAddressTotalSelected = (TextView) findViewById(R.id.textViewAddressTotalSelected);

        cardViewSelectedAddress = (CardView) findViewById(R.id.cardViewSelectedAddress);
        btn_placeOrder = (Button) findViewById(R.id.btn_placeOrder);
        recyclerViewAddress = (RecyclerView) findViewById(R.id.recyclerViewForConsumerAddressList);

        if(vehicle != null){
            selectedVehicle = vehicle;
            textViewCarName.setText(vehicle.getVehicleName());
            textViewCarNumber.setText(vehicle.getVehicleNumber());
        }

        recyclerViewAddress.setHasFixedSize(true);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));
        textViewSelectedAddress.setVisibility(View.GONE);
        cardViewSelectedAddress.setVisibility(View.GONE);
        btn_placeOrder.setVisibility(View.GONE);

        btnAddYourAddress = (Button) findViewById(R.id.btn_addYourAddress);
        btnAddYourAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });

        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        loadConsumerAddress();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadConsumerAddress();
    }

    public void loadConsumerAddress () {
        final ProgressDialog progressDialog =  CarConstant.getProgressDialog(this,"Loading...");
        progressDialog.show();
        Consumer consumer = SaveSharedPreference.getConsumerFromGson(this);
        addressList = consumer.getListOfAddress();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(addressList != null && addressList.size()>0){
                    progressDialog.dismiss();
                    ConsumerAddressAdapter consumerAddressAdapter = new ConsumerAddressAdapter(OrderActivity.this,addressList);
                    recyclerViewAddress.setAdapter(consumerAddressAdapter);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(OrderActivity.this,"Not able to fetch Order List",Toast.LENGTH_SHORT).show();
                }
            }
        }, 100);
    }

    public void addressSelected(ConsumerAddress address) {
        recyclerViewAddress.setVisibility(View.GONE);
        textViewSelectAddress.setVisibility(View.GONE);
        btnAddYourAddress.setVisibility(View.GONE);

        textViewSelectedAddress.setVisibility(View.VISIBLE);
        cardViewSelectedAddress.setVisibility(View.VISIBLE);
        btn_placeOrder.setVisibility(View.VISIBLE);
        selectedAddress = address;
        textViewAddressLineSelected.setText(address.getAddressLine());
        textViewAddressLocalityLandmarkSelected.setText(address.getLocality());
        textViewAddressTotalSelected.setText(address.getCity()+" "+address.getState()+" "+address.getPincode());
    }

    public void placeOrder() {
        progressDialog = CarConstant.getProgressDialog(this,"Processing order...");
        progressDialog.show();

        Orders orders = new Orders();
        Consumer con = new Consumer();
        con.setConsumerId(SaveSharedPreference.getConsumerFromGson(this).getConsumerId());
        orders.setConsumer(con);
        orders.setOrderAmount(30d);
        orders.setOrderPaymentStatus("pending");
        orders.setOrderStatus("New");

        orders.setOrderAddressId(selectedAddress.getAddressId());
        orders.setOrderVehicleId(selectedVehicle.getVehicleId());

        orders.setOrderAddressCity(selectedAddress.getCity());
        orders.setOrderAddressState(selectedAddress.getState());

        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Call<Map<String, Object>> call = restInvokerService.placeOrder(orders);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                if( map.get("resCode").equals(200.0)){
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Order Placed successfully.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Order Unsuccessfully.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                progressDialog.dismiss();
                return;
            }
        });

    }
}
