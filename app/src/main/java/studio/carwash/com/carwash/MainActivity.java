package studio.carwash.com.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

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

public class MainActivity extends AppCompatActivity {

    public  ProgressDialog progressDialog;

    @InjectView(R.id.input_phoneNumberOtp) EditText input_phoneNumberOtp;
    @InjectView(R.id.input_otp) EditText input_otp;
    @InjectView(R.id.btn_sendOtp) Button btn_sendOtp;
    @InjectView(R.id.btn_verifyOtp) Button btn_verifyOtp;

    Gson gson = new Gson();
    String phoneNumber, otp;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;
    String consumerString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        FirebaseApp.initializeApp(this);
        progressDialog =  CarConstant.getProgressDialog(MainActivity.this,"Logging in...");


        if(SaveSharedPreference.getIsUserLoggedIn(MainActivity.this)){
            Intent intent = new Intent(getApplicationContext(),ConsumerActivity.class);
            startActivity(intent);
            finish();
        } else {
            input_otp.setVisibility(View.GONE);
            btn_verifyOtp.setVisibility(View.GONE);
            setUpOtpLogin();
        }
        getSupportActionBar().hide();
    }

    public void setUpOtpLogin(){
        StartFirebaseLogin();
        btn_sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = input_phoneNumberOtp.getText().toString();
                if( phoneNumber != null){
                    logInwithPhoneNumber(phoneNumber);
                } else {
                    input_phoneNumberOtp.setError("Please Enter Phone Number!");
                }


            }
        });

        btn_verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = input_otp.getText().toString();
                if( otp != null){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                    SigninWithPhone(credential);
                } else {
                    input_otp.setError("Please Enter Value!");
                }

            }
        });
    }

    public void logInwithPhoneNumber(String number){
        RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
        Call<Map<String, Object>> call = restInvokerService.doLoginByNumber(number);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map = response.body();
                if( map.get("resCode").equals(200.0)){
                    String ss = map.get("data").toString();
                    Consumer con = gson.fromJson(ss, Consumer.class);
                    if(con.getConsumerId() == 0){
                        Toast.makeText(MainActivity.this,"The Phone number is not registered with us.",Toast.LENGTH_SHORT).show();
                    } else {
                        consumerString = ss;
                    }
                    sendFinalOtp();
                } else {
                    Toast.makeText(MainActivity.this,"Some problem occurred!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                btn_sendOtp.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Some Error in creating account, try again after some time.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendFinalOtp(){
        phoneNumber = input_phoneNumberOtp.getText().toString();
        if(phoneNumber != null){
            phoneNumber = "+91" + phoneNumber;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,70,
                    TimeUnit.SECONDS,
                    MainActivity.this,
                    mCallback
            );
            Log.d("ras","code otp call function");
        }
    }

    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
            Log.d("ras","code otp call qsac"+auth);
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d("ras","onVerificationCompleted");
                saveConsumerObjAndLogin();
                Toast.makeText(MainActivity.this,"verification completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d("ras","onVerificationFailed");
                Toast.makeText(MainActivity.this,"verification failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d("ras","onCodeSent");
                input_otp.setVisibility(View.VISIBLE);
                btn_verifyOtp.setVisibility(View.VISIBLE);

                input_phoneNumberOtp.setVisibility(View.GONE);
                btn_sendOtp.setVisibility(View.GONE);

                verificationCode = s;
                Toast.makeText(MainActivity.this,"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("ras","isSuccessful");
                            Toast.makeText(MainActivity.this,"Correct OTP",Toast.LENGTH_LONG).show();
                            saveConsumerObjAndLogin();
                        } else {
                            Log.d("ras","isSuccessfulUn success");
                            Toast.makeText(MainActivity.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void saveConsumerObjAndLogin() {
        Log.d("ras","saveConsumerObjAndLogin");
        auth.signOut();
        if(consumerString != null){
            SaveSharedPreference.setConsumerObj(MainActivity.this, consumerString);
            SaveSharedPreference.setIsUserLoggedIn(MainActivity.this);
            Intent intent = new Intent(getApplicationContext(), ConsumerActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d("ras",phoneNumber);
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            intent.putExtra("registerPhoneNumber",phoneNumber);
            startActivity(intent);
            finish();
        }

    }

}
