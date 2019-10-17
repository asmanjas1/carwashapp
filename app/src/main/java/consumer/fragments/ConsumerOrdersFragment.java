package consumer.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import beans.Orders;
import consumer.adapter.ConsumerOrdersInProgressAdapter;
import resources.CarConstant;
import resources.RestClient;
import resources.RestInvokerService;
import resources.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carwash.com.carwash.R;

/**
 * Created by Dell on 8/14/2019.
 */

public class ConsumerOrdersFragment extends Fragment {

    RecyclerView recyclerViewInProgressOrders;
    List<Orders> ordersList = new ArrayList<>();
    ProgressDialog progressDialog;
    public static Gson gson = new Gson();

    public ConsumerOrdersFragment (){}

    public static ConsumerOrdersFragment newInstance() {
        ConsumerOrdersFragment fragment;
        fragment = new ConsumerOrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Orders");
        View view  = inflater.inflate(R.layout.consumer_orders_fragment, container, false);

        recyclerViewInProgressOrders = (RecyclerView) view.findViewById(R.id.recyclerViewForInProgressOrders);
        recyclerViewInProgressOrders.setHasFixedSize(true);
        recyclerViewInProgressOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        loadOrders();
        return view;
    }

    public void loadOrders(){
        progressDialog =  CarConstant.getProgressDialog(getContext(),"Loading...");
        progressDialog.show();
        Integer id = SaveSharedPreference.getConsumerFromGson(getContext()).getConsumerId();
        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Call<Map<String, Object>> call = restInvokerService.getAllOrdersForConsumer(id);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
               if( map.get("resCode").equals(200.0)){
                    List<String> list = (List<String>) map.get("data");
                   processOrderResponse(list);
               } else {
                    progressDialog.dismiss();
                    return;
               }
            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                progressDialog.dismiss();
                return;
            }
        });
    }

    public void processOrderResponse(List<String> list){
        for(String ss: list){
            Orders order = gson.fromJson(ss, Orders.class);
            ordersList.add(order);
        }

        Collections.reverse(ordersList);
        loadInProgressOrders();
    }

    public void loadInProgressOrders() {
        if(ordersList != null && ordersList.size()>0){
            progressDialog.dismiss();
            ConsumerOrdersInProgressAdapter consumerCarAdapter = new ConsumerOrdersInProgressAdapter(getContext(),ordersList);
            recyclerViewInProgressOrders.setAdapter(consumerCarAdapter);
        }else {
            progressDialog.dismiss();
            Toast.makeText(getContext(),"Not able to fetch In order list",Toast.LENGTH_SHORT).show();
        }
    }

}
