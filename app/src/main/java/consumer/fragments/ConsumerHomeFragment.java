package consumer.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import beans.Consumer;
import beans.Vehicle;
import consumer.adapter.ConsumerCarAdapter;
import resources.CarConstant;
import resources.DummyResponseResultFromRest;
import resources.RestClient;
import resources.RestInvokerService;
import resources.SaveSharedPreference;
import retrofit2.Call;
import studio.carwash.com.carwash.CarActivity;
import studio.carwash.com.carwash.ConsumerActivity;
import studio.carwash.com.carwash.R;

/**
 * Created by Dell on 8/13/2019.
 */

public class ConsumerHomeFragment extends Fragment{

    Button addCarButton,bookNow;
    TextView textView_bookNow;
    RecyclerView recyclerViewCar;
    List<Vehicle> vehicleList;


    public static ConsumerHomeFragment newInstance() {
        ConsumerHomeFragment fragment;
        fragment = new ConsumerHomeFragment();
        return fragment;
    }

    public ConsumerHomeFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        View view  = inflater.inflate(R.layout.consumer_home_fragment, container, false);

        addCarButton = (Button) view.findViewById(R.id.btn_addNewCar);
        bookNow = (Button) view.findViewById(R.id.btn_booknow);
        recyclerViewCar = (RecyclerView) view.findViewById(R.id.recyclerViewForConsumerCarList);
        textView_bookNow = (TextView) view.findViewById(R.id.textView_booknow);

        recyclerViewCar.setHasFixedSize(true);
        recyclerViewCar.setLayoutManager(new LinearLayoutManager(getContext()));
        textView_bookNow.setVisibility(View.GONE);

        loadCars();
        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carIntent = new Intent(getActivity(), CarActivity.class);
                startActivity(carIntent);
            }
        });

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_bookNow.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCars();
    }

    public void loadCars() {
        final ProgressDialog progressDialog =  CarConstant.getProgressDialog(getContext(),"Loading...");
        progressDialog.show();
        Consumer consumer = SaveSharedPreference.getConsumerFromGson(getContext());
        vehicleList = consumer.getListOfVehicle();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(vehicleList != null && vehicleList.size()>0){
                    progressDialog.dismiss();
                    ConsumerCarAdapter consumerCarAdapter = new ConsumerCarAdapter(getContext(),vehicleList);
                    recyclerViewCar.setAdapter(consumerCarAdapter);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Not able to fetch Order List",Toast.LENGTH_SHORT).show();
                }
            }
        }, 100);
    }

}
