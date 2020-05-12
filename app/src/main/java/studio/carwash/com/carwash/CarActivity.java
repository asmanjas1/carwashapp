package studio.carwash.com.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import beans.Consumer;
import beans.Vehicle;
import consumer.fragments.ConsumerHomeFragment;
import resources.CarConstant;
import resources.RestClient;
import resources.RestInvokerService;
import resources.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarActivity extends AppCompatActivity {

    private Spinner spinnerVehicleMake, spinnerVehicleModel;
    private Button btnAddCar;
    private EditText vehicleNumber;
    private String vehicleMaker, vehicleModal;
    public  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        this.setTitle("Add vehicle");

        vehicleNumber =  findViewById(R.id.input_vehicle_number);
        spinnerVehicleMake = findViewById(R.id.spinnerVehicleMake);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorActionbar)));

        ArrayAdapter<String> carMakerAdapter =
                new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, CarConstant.carMakeList);
        //carMakerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerVehicleMake.setAdapter(carMakerAdapter);
        spinnerVehicleMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                vehicleMaker = item;
                onMakerSelected(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vehicleMaker = "";
            }
        });

        spinnerVehicleModel = (Spinner) findViewById(R.id.spinnerVehicleModel);
        List<String> carModelList = new ArrayList<>();
        carModelList.add("Select Model");

        ArrayAdapter<String> carModelAdapter =
                new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,carModelList);
        spinnerVehicleModel.setAdapter(carModelAdapter);
        spinnerVehicleModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                vehicleModal = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vehicleModal = "";
            }
        });


        btnAddCar = (Button) findViewById(R.id.btn_createVehicle);
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar();
            }
        });
    }

    public void onMakerSelected(String maker){
        List<String> carModelList = CarConstant.carModelmap.get(maker);
        if(carModelList == null){
            carModelList = new ArrayList<>();
        }
        if(!carModelList.contains("Select Model")){
            carModelList.add(0,"Select Model");
        }

        ArrayAdapter<String> carModelAdapter =
                new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,carModelList);
        spinnerVehicleModel.setAdapter(carModelAdapter);
    }

    public void addCar() {
        if(!validateCar()){
            return;
        }
        progressDialog = CarConstant.getProgressDialog(this,"Adding Vehicle...");
        progressDialog.show();
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleNumber.getText().toString());
        vehicle.setVehicleType("Car");
        vehicle.setVehicleName(vehicleMaker + " " + vehicleModal);
        Consumer con = new Consumer();
        con.setConsumerId(SaveSharedPreference.getConsumerFromGson(this).getConsumerId());
        vehicle.setConsumer(con);

        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Call<Map<String, Object>> call = restInvokerService.addVehicle(vehicle);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                if( map.get("resCode").equals(200.0)){
                    progressDialog.dismiss();
                    String ss = map.get("data").toString();
                    SaveSharedPreference.setConsumerObj(CarActivity.this, ss);
                    Toast.makeText(getBaseContext(), "Vehicle Added Successfully.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Error adding in vehicle.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Error adding in vehicle.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    public boolean validateCar(){
        boolean isValid = true ;;
        String vehicleNum = vehicleNumber.getText().toString();
        if (TextUtils.isEmpty(vehicleNum) ) {
            isValid = false;
            Toast.makeText(getBaseContext(), "Please Enter Vehicle Number", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(vehicleMaker) || vehicleMaker.equals("Select Maker")) {
            isValid = false;
            Toast.makeText(getBaseContext(), "Please Select Vehicle Maker", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(vehicleModal) || vehicleModal.equals("Select Model")) {
            isValid = false;
            Toast.makeText(getBaseContext(), "Please Select Vehicle Modal", Toast.LENGTH_LONG).show();
        }
        return isValid;

    }
}
