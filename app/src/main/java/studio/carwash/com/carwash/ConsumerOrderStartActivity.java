package studio.carwash.com.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import beans.Consumer;
import beans.Vehicle;
import consumer.adapter.ConsumerAddressAdapter;
import consumer.adapter.ConsumerCarAdapter;
import resources.CarConstant;
import resources.RestClient;
import resources.RestInvokerService;
import resources.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumerOrderStartActivity extends AppCompatActivity {

    Button addCarButton;
    TextView textView_bookNow, textView_noCarsFound;
    RecyclerView recyclerViewCar;
    List<Vehicle> vehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_order_start);
        this.setTitle("Select Vehicle");

        addCarButton = (Button) findViewById(R.id.btn_addNewCar);
        recyclerViewCar = (RecyclerView) findViewById(R.id.recyclerViewForConsumerCarList);
        textView_bookNow = (TextView) findViewById(R.id.textView_booknow);
        textView_noCarsFound = (TextView) findViewById(R.id.textView_noCarsFound);

        recyclerViewCar.setHasFixedSize(true);
        recyclerViewCar.setLayoutManager(new LinearLayoutManager(this));
        textView_noCarsFound.setVisibility(View.GONE);
        textView_bookNow.setVisibility(View.GONE);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorActionbar)));

        loadCars();
        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carIntent = new Intent(getApplicationContext(), CarActivity.class);
                startActivity(carIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCars();
    }

    public void loadCars() {
        final ProgressDialog progressDialog =  CarConstant.getProgressDialog(this,"Loading...");
        progressDialog.show();
        Consumer consumer = SaveSharedPreference.getConsumerFromGson(this);
        vehicleList = consumer.getListOfVehicle();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(vehicleList != null && vehicleList.size()>0){
                    progressDialog.dismiss();
                    recyclerViewCar.setVisibility(View.VISIBLE);
                    textView_noCarsFound.setVisibility(View.GONE);
                    ConsumerCarAdapter consumerCarAdapter = new ConsumerCarAdapter(ConsumerOrderStartActivity.this,vehicleList);
                    recyclerViewCar.setAdapter(consumerCarAdapter);
                }else {
                    textView_noCarsFound.setVisibility(View.VISIBLE);
                    recyclerViewCar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"No vehicle found.",Toast.LENGTH_LONG).show();
                }
            }
        }, 100);
    }

    public void deleteVehicle(Vehicle vehicle){
        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Call<Map<String, Object>> call = restInvokerService.deleteVehicle(vehicle);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                if( map.get("resCode").equals(200.0)){
                    Toast.makeText(getBaseContext(), "Vehicle deleted Successfully.", Toast.LENGTH_LONG).show();
                    refreshConsumer();
                } else {
                    Toast.makeText(getBaseContext(), "Error in deleting vehicle.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error in deleting vehicle.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    public void refreshConsumer(){
        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Consumer consumer = SaveSharedPreference.getConsumerFromGson(this);
        Call<Map<String, Object>> call = restInvokerService.getConsumerById(consumer.getConsumerId());
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                if( map.get("resCode").equals(200.0)){
                    String ss = map.get("data").toString();
                    SaveSharedPreference.setConsumerObj(ConsumerOrderStartActivity.this, ss);
                    loadCars();
                } else {
                    Toast.makeText(getBaseContext(), "Error in refreshConsumer.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error in refreshConsumer.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}
