package consumer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import studio.carwash.com.carwash.ConsumerOrderStartActivity;
import studio.carwash.com.carwash.R;

/**
 * Created by Dell on 8/13/2019.
 */

public class ConsumerHomeFragment extends Fragment{

    Button startOrderButton;


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

        startOrderButton = (Button) view.findViewById(R.id.btn_book_order);


        startOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent( getContext(), ConsumerOrderStartActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
