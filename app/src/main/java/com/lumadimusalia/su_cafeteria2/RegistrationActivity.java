package com.lumadimusalia.su_cafeteria2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userFirstName, userLastName, userEmailAddress, userPassword, userConfirmPassword;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    String firstname, lastname, email, password, confirmpassword;
    private static final String TAG = "RegistrationActivity";
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        UserProfile userProfile = new UserProfile("Lumie","Wangari","lumiewangari@gmail.com");
        mDatabaseReference = mDatabase.getReference().child("FirstName");
        mDatabaseReference.setValue("Lumie");
        mDatabaseReference = mDatabase.getReference().child("LastName");
        mDatabaseReference.setValue("Wangari");
        mDatabaseReference = mDatabase.getReference().child("EmailAddress");
        mDatabaseReference.setValue("lumiewangari@gmail.com");

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    //Upload Data to the Database
                    String user_email = userEmailAddress.getText().toString().trim();// trim() is to remove all the white spaces that the user might have entered.
                    String user_password = userPassword.getText().toString().trim();
                    String user_confirmpassword = userConfirmPassword.getText().toString().trim();

                    //the data collected will be stored in the FireBase database excluding the password for user security.
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        //sends data to the database.
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //sendEmailVerification();
                                sendUserData();
                                Toast.makeText(RegistrationActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();// shows success message
                                finish();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));// takes the user to the login page.
                            }else{
                                System.out.println("Sign up error "+ task.getException().toString());
                                Toast.makeText(RegistrationActivity.this, "Registration Failed.Please try again.", Toast.LENGTH_SHORT).show();// shows error message
                            }


                        }
                    });



                }


            }
        });
        //when the user clicks on the "Already signed in?" button
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }
    //setting up user interface views
    private void setupUIViews(){
        userFirstName = (EditText) findViewById(R.id.etUserFirstName);
        userLastName = (EditText) findViewById(R.id.etUserLastName);
        userEmailAddress = (EditText) findViewById(R.id.etUserEmailAddress);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        userConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);


    }
    private Boolean validate(){
        Boolean result = false;


         firstname = userFirstName.getText().toString();
         lastname = userLastName.getText().toString();
         email = userEmailAddress.getText().toString();
         password = userPassword.getText().toString();
         confirmpassword = userConfirmPassword.getText().toString();

        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()){
            //if the details are empty, an error message is viewed.

            Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show();//shows message to enter all details required.
        }else{
            result=true;
        }


        return result;
    }


    private void sendEmailVerification(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Log.d(TAG, "Email sent");
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(firstname,lastname,email);
        myRef.setValue(userProfile);
    }



}
