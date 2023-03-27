package com.controlvet.notific;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.notific.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;


public class Login extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.nombre);
        mPasswordField = findViewById(R.id.contraseña);

        mAuth = FirebaseAuth.getInstance();

        Button signInButton = findViewById(R.id.btniniciar);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        TextView signUpTextView = findViewById(R.id.btnregistrar);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, registrarse.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Email es necesario");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Password es necesario");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Autenticacion exitosa", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

