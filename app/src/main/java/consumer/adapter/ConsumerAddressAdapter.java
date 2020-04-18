package consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import beans.ConsumerAddress;
import studio.carwash.com.carwash.OrderActivity;
import studio.carwash.com.carwash.R;

/**
 * Created by Dell on 8/20/2019.
 */

public class ConsumerAddressAdapter extends RecyclerView.Adapter<ConsumerAddressAdapter.OrderViewHolder>{
    private Context mCtx;
    private List<ConsumerAddress> addressList;
    Gson gson =  new Gson();

    public ConsumerAddressAdapter(Context ctx, List<ConsumerAddress> addressList){
        this.mCtx = ctx;
        this.addressList = addressList;
    }
    @Override
    public void onBindViewHolder(ConsumerAddressAdapter.OrderViewHolder holder, int position) {
        ConsumerAddress address = addressList.get(position);
        holder.bindData(address);
    }

    @Override
    public ConsumerAddressAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.address_list_layout, parent,false);
        return new ConsumerAddressAdapter.OrderViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        ConsumerAddress addressSelected;
        TextView textViewAddressLine,textViewAddressLocalityLandmark,textViewAddressTotal, delete_Address;

        public OrderViewHolder(final View itemView) {
            super(itemView);
            textViewAddressLine = (TextView) itemView.findViewById(R.id.textViewAddressLine);
            textViewAddressLocalityLandmark = (TextView) itemView.findViewById(R.id.textViewAddressLocalityLandmark);
            textViewAddressTotal = (TextView) itemView.findViewById(R.id.textViewAddressTotal);
            delete_Address = (TextView) itemView.findViewById(R.id.delete_Address);

            delete_Address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCtx instanceof OrderActivity){
                        ((OrderActivity) mCtx).deleteAddress(addressSelected);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((OrderActivity)mCtx).addressSelected(addressSelected);
                }
            });
        }

        public void bindData(ConsumerAddress address){
            addressSelected = address;
            textViewAddressLine.setText(address.getAddressLine());
            textViewAddressLocalityLandmark.setText(address.getLocality());
            textViewAddressTotal.setText(address.getCity()+" "+address.getState()+" "+address.getPincode());
        }
    }
}
