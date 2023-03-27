package com.controlvet.notific;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notific.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class registrarse extends AppCompatActivity {

    private EditText nombre, correo, contrasena;
    private Button btnregistrar;
    private FirebaseAuth autentificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        autentificacion = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contraseña);
        btnregistrar = findViewById(R.id.btnregister);


        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre_usuario = nombre.getText().toString().trim();
                String correo_usuario = correo.getText().toString().trim();
                String contrasena_usuario = contrasena.getText().toString().trim();

                if (TextUtils.isEmpty(nombre_usuario)) {
                    Toast.makeText(getApplicationContext(), "Ingresa un nombre de usuario", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(correo_usuario)) {
                    Toast.makeText(getApplicationContext(), "Ingresa un correo electrónico", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(contrasena_usuario)) {
                    Toast.makeText(getApplicationContext(), "Ingresa una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                autentificacion.createUserWithEmailAndPassword(correo_usuario, contrasena_usuario)
                        .addOnCompleteListener(registrarse.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = autentificacion.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nombre_usuario).build();
                                    user.updateProfile(profileUpdates);
                                    Toast.makeText(registrarse.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(registrarse.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(registrarse.this, "El registro ha fallado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
