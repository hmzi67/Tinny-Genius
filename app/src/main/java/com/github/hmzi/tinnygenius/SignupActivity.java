package com.github.hmzi.tinnygenius;



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

import com.github.hmzi.tinnygenius.Classes.ProgressStatus;
import com.github.hmzi.tinnygenius.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    // declaring
    EditText inputEmail, inputPassword, inputConfirmPassword, inputFullname;
    Button register;
    TextView alreadyHaveAccount;
    ImageView goBack;
    String fullNamePattern = "[a-zA-Z ]+";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressStatus progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;


    // onCreate default method when we creating activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // initialization
        inputFullname = findViewById(R.id.editTextNameAddress);
        inputEmail = findViewById(R.id.editTextEmail);
        inputPassword = findViewById(R.id.userPassword);
        inputConfirmPassword = findViewById(R.id.userPasswordConfirm);
        register = findViewById(R.id.buttonSU);
        goBack = findViewById(R.id.goBack);
        alreadyHaveAccount = findViewById(R.id.already_account_link);
        progressDialog = new ProgressStatus(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // click listeners
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String userName = inputFullname.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String confirmPass = inputConfirmPassword.getText().toString().trim();

        if(!userName.matches(fullNamePattern)) {
            inputFullname.setError("Enter correct name");
        }
        else if(!email.matches(emailPattern)){
            inputEmail.setError("Enter a correct Email");
        }
        else if (password.isEmpty() || password.length() < 8)
        {
            inputPassword.setError("Enter at least 8 alphanumeric keys for password");
        }
        else if (!password.equals(confirmPass))
        {
            inputConfirmPassword.setError("Password not matched");
        }
        else {
            progressDialog.setTitle("Please wait while Registration...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //Creating account
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                            progressDialog.dismiss();

                            // data saved in real time database
                            Users registerUser = new Users(mAuth.getCurrentUser().getUid(), userName, email, "", "");
                            databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(registerUser);

                            sendUserToNextActivity();

                            Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}