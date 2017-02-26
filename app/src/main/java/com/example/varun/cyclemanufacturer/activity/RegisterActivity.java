package com.example.varun.cyclemanufacturer.activity;

import com.example.varun.cyclemanufacturer.model.User;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.varun.cyclemanufacturer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button finalcreateaccount;
    EditText name, email, password;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("users");

        mFirebaseAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email2);
        password = (EditText) findViewById(R.id.password2);

        //Registration process
        findViewById(R.id.finalcreateaccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = name.getText().toString();
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                nameString = nameString.trim();
                emailString = emailString.trim();
                passwordString = passwordString.trim();

                createAccount(emailString, passwordString, nameString);
            }
        });
    }

    private void createAccount(final String emailString, String passwordString, final String nameString) {
        //If a field is empty then user is asked to fill all the field
        if (passwordString.isEmpty() || emailString.isEmpty() || nameString.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(R.string.signup_error_message)
                    .setTitle(R.string.signup_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            //else store new user's credentials on the server
            mFirebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            User user = new User(nameString, task.getResult().getUser());
                            myRef.child(task.getResult().getUser().getUid()).setValue(user);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
        }
    }
}
