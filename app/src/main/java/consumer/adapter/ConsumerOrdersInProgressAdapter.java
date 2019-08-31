package consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import beans.Orders;
import studio.carwash.com.carwash.R;

/**
 * Created by Dell on 8/21/2019.
 */

public class ConsumerOrdersInProgressAdapter extends RecyclerView.Adapter<ConsumerOrdersInProgressAdapter.OrderViewHolder>{

    //this context we will use to inflate the layout
    private Context mCtx;
    private List<Orders> ordersList;
    Gson gson =  new Gson();

    public ConsumerOrdersInProgressAdapter(Context ctx, List<Orders> ordersList){
        this.mCtx = ctx;
        this.ordersList = ordersList;
    }
    @Override
    public void onBindViewHolder(ConsumerOrdersInProgressAdapter.OrderViewHolder holder, int position) {
        Orders order = ordersList.get(position);
        holder.bindData(order);
    }

    @Override
    public ConsumerOrdersInProgressAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.ordersinprogress_list_layout, parent,false);
        return new ConsumerOrdersInProgressAdapter.OrderViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        Orders orderSelected;
        TextView textViewOrderId;

        public OrderViewHolder(View itemView) {
            super(itemView);
            textViewOrderId = (TextView) itemView.findViewById(R.id.textViewOrderID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent =  new Intent((ConsumerActivity) mCtx, OrderActivity.class);

                    String vehicleJson = gson.toJson(orderSelected);
                    Toast.makeText(mCtx,vehicleJson,Toast.LENGTH_SHORT).show();
                    //intent.putExtra("vehicleSelected",vehicleJson);
                    //((ConsumerActivity)mCtx).startActivity(intent);
                }
            });
        }

        public void bindData(Orders order){
            orderSelected = order;
            textViewOrderId.setText("Order ID: "+order.getOrderId());
        }
    }
}
