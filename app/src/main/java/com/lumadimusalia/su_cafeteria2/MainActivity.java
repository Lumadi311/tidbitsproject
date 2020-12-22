package com.lumadimusalia.su_cafeteria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText EmailAddress;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;//the login button will be disabled when the counter is depleted.
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;
    private ImageView userProfilePic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        userRegistration = (TextView) findViewById(R.id.tvRegister);
        forgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        userProfilePic = (ImageView) findViewById(R.id.ivProfile);

        //logical code for the counter process.
        Info.setText("No of attempts remaining:5");

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        /*if (user != null){
            finish();
           // startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }*/

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(EmailAddress.getText().toString().trim(), Password.getText().toString().trim());// validates the information given.
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class)); //send the unregistered user from the login page to the registration page if they have not registered
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Password2Activity.class));
            }
        });

    }

    private void validate(String userEmailAddress, String userPassword) {
        progressDialog.setMessage("Enjoy Your Meal!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmailAddress, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }else{
                    System.out.println("Login error "+ task.getException().toString());
                    Toast.makeText(MainActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    counter--;
                    Info.setText("No of attempts remaining:"+ counter);
                    if (counter == 0){
                        Login.setEnabled(false);
                    }
                }

            }
        });

    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, HomeActivity.class));

//      if(emailflag){
//          finish();
//          startActivity(new Intent(MainActivity.this, HomeActivity.class));
//       }else{
//           Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
//           firebaseAuth.signOut();
//       }
    }
}
