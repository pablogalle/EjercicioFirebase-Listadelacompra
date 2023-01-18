package com.example.ejerciciofirebase_listadelacompra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ejerciciofirebase_listadelacompra.configs.Constantes;
import com.example.ejerciciofirebase_listadelacompra.modelos.DatosPersonales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

public class DatosPersonalesActivity extends AppCompatActivity {

    private TextView lblNombre;
    private TextView lblApellidos;
    private TextView lblTelefono;
    private DatosPersonales datosPersonales;

    // Database
    private FirebaseDatabase database;
    private DatabaseReference refDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        inicializarVista();

        database = (FirebaseDatabase) getIntent().getExtras().getSerializable(Constantes.DATABASE);
        refDatos = database.getReference("datos_personales");

        refDatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    GenericTypeIndicator<DatosPersonales> gti =
                            new GenericTypeIndicator<DatosPersonales>() {};
                    DatosPersonales dp = snapshot.getValue(gti);
                    if (dp == null){
                        rellenarDatos().show();
                    }else {
                        datosPersonales = dp;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private AlertDialog rellenarDatos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Datos Personales");
        builder.setCancelable(false);
        // TENEMOS QUE CARGAR UN LAYOUT
        View alertView = LayoutInflater.from(this).inflate(R.layout.datos_personales_alert, null);

        EditText txtNombre = alertView.findViewById(R.id.txtNombreDatosAlert);
        EditText txtApellidos = alertView.findViewById(R.id.txtApellidosDatosAlert);
        EditText txtTelefono = alertView.findViewById(R.id.txtTelefonoDatosAlert);
        builder.setView(alertView);

        // CREAR BOTONES
        builder.setNegativeButton("Cancelar" , null);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DatosPersonales datosPersonales1 = new DatosPersonales(
                        txtNombre.getText().toString(),
                        txtApellidos.getText().toString(),
                        txtTelefono.getText().toString());
                refDatos.setValue(datosPersonales1);

            }
        });
        return builder.create();
    }

    private void inicializarVista() {
        lblNombre.findViewById(R.id.lblNombreDatos);
        lblApellidos.findViewById(R.id.lblApellidosDatos);
        lblTelefono.findViewById(R.id.lblTelefonoDatos);
    }
}