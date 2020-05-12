package studio.carwash.com.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import beans.Carwasher;
import beans.Consumer;
import butterknife.ButterKnife;
import butterknife.InjectView;
import resources.CarConstant;
import resources.RestClient;
import resources.RestInvokerService;
import resources.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("registerPhoneNumber");
        Log.d("rassignup",phoneNumber);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        getSupportActionBar().hide();
    }

    public void signup() {
        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog =  CarConstant.getProgressDialog(this,"Creating Account");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        // TODO: Implement your own signup logic here.

        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Consumer consumer = new Consumer();
        consumer.setEmail(email);
        consumer.setName(name);
        consumer.setPhoneNumber(phoneNumber);
        consumer.setPassword("P@$$word");
        Call<Map<String, Object>> call = restInvokerService.createConsumer(consumer);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                if( map.get("resCode").equals(200.0)){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    saveConsmerByPhone();
                } else {
                    progressDialog.dismiss();
                    onSignupFailed();
                }

            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                progressDialog.dismiss();
                onSignupFailed();
            }
        });
    }

    public void saveConsmerByPhone(){
        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Call<Map<String, Object>> call = restInvokerService.doLoginByNumber(phoneNumber);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                if( map.get("resCode").equals(200.0)){
                    String ss = map.get("data").toString();
                    SaveSharedPreference.setConsumerObj(SignupActivity.this, ss);
                    SaveSharedPreference.setIsUserLoggedIn(SignupActivity.this);
                    setResult(RESULT_OK, null);
                    Intent intent = new Intent(getApplicationContext(), ConsumerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some Error in creating account, try again after some time.",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed.", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("please set a name of at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        return valid;
    }
}
