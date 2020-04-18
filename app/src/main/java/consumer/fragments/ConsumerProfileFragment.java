package consumer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import beans.Consumer;
import resources.SaveSharedPreference;
import studio.carwash.com.carwash.R;

/**
 * Created by Dell on 8/14/2019.
 */

public class ConsumerProfileFragment extends Fragment{


    public ConsumerProfileFragment(){}

    public static ConsumerProfileFragment newInstance() {
        ConsumerProfileFragment fragment;
        fragment = new ConsumerProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Subscription");

        View v = inflater.inflate(R.layout.consumer_profile_fragment, container, false);
        return v;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_consumer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
