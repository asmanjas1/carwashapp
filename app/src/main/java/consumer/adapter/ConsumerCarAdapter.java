package consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.List;

import beans.Vehicle;
import consumer.fragments.ConsumerHomeFragment;
import studio.carwash.com.carwash.ConsumerActivity;
import studio.carwash.com.carwash.ConsumerOrderStartActivity;
import studio.carwash.com.carwash.MainActivity;
import studio.carwash.com.carwash.OrderActivity;
import studio.carwash.com.carwash.R;

//import com.consumer_fragments.ConsumerCommonOrderFragment;

public class ConsumerCarAdapter extends RecyclerView.Adapter<ConsumerCarAdapter.OrderViewHolder>{
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the Orders in a list
    private List<Vehicle> carList;
    Gson gson =  new Gson();

    public ConsumerCarAdapter(Context ctx, List<Vehicle> carList){
        this.mCtx = ctx;
        this.carList = carList;
    }
    @Override
    public void onBindViewHolder(ConsumerCarAdapter.OrderViewHolder holder, int position) {
        //getting the Order of the specified position
        Vehicle order = carList.get(position);
        //binding the data with the viewholder views
        holder.bindData(order);
    }

    @Override
    public ConsumerCarAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.car_list_layout, parent,false);
        return new ConsumerCarAdapter.OrderViewHolder(view);
       /* View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.generalorderlayoutforshopkeeper, parent, false);

        return new OrderViewHolder(itemView);*/
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        Vehicle vehicleSelected;
        TextView textViewCarName,textViewCarNumber;
        ImageView delete_car;

        public OrderViewHolder(View itemView) {
            super(itemView);
            textViewCarName = (TextView) itemView.findViewById(R.id.textViewCarName);
            textViewCarNumber = (TextView) itemView.findViewById(R.id.textViewCarNumber);
            delete_car = (ImageView) itemView.findViewById(R.id.delete_car);

            delete_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mytag",vehicleSelected.getVehicleName());
                    if(mCtx instanceof ConsumerOrderStartActivity ){
                        ((ConsumerOrderStartActivity) mCtx).deleteVehicle(vehicleSelected);
                    }
                }
            });
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent =  new Intent( mCtx, OrderActivity.class);
                   String vehicleJson = gson.toJson(vehicleSelected);
                   intent.putExtra("vehicleSelected",vehicleJson);
                   mCtx.startActivity(intent);
               }
           });
        }

        public void bindData(Vehicle vehicle){
            vehicleSelected = vehicle;
            textViewCarName.setText(vehicle.getVehicleName());
            textViewCarNumber.setText(vehicle.getVehicleNumber());
            /*imageView.setImageDrawable(mCtx.getResources().getDrawable(Order.getImage()));*/
        }
    }
}
