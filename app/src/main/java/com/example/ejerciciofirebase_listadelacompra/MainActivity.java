package com.example.ejerciciofirebase_listadelacompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.ejerciciofirebase_listadelacompra.adapters.ProductosAdapter;
import com.example.ejerciciofirebase_listadelacompra.configs.Constantes;
import com.example.ejerciciofirebase_listadelacompra.modelos.Producto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejerciciofirebase_listadelacompra.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Producto> productos;

    // Adapter
    private ProductosAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // Database
    private FirebaseDatabase database;
    private DatabaseReference refUser;
    private DatabaseReference refProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        databaseConfig();

        productos = new ArrayList<>();
        adapter = new ProductosAdapter(this, productos, R.layout.producto_model_view, refProductos);
        layoutManager = new LinearLayoutManager(this);

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        cargarProductos();

        binding.fab.setOnClickListener(view -> crearProducto().show());
    }

    private void databaseConfig() {
        database = FirebaseDatabase.getInstance(Constantes.DATABASEURL);
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refProductos = refUser.child("productos_list");
    }

    private AlertDialog crearProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Nuevo Producto");
        builder.setCancelable(false);
        // TENEMOS QUE CARGAR UN LAYOUT
        View alertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.producto_model_alert, null);
        EditText txtNombre = alertView.findViewById(R.id.txtNombreAlert);
        EditText txtCantidad = alertView.findViewById(R.id.txtCantidadAlert);
        EditText txtPrecio = alertView.findViewById(R.id.txtPrecioAlert);
        builder.setView(alertView);

        // CREAR BOTONES
        builder.setNegativeButton("Cancelar" , null);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Producto producto = new Producto(txtNombre.getText().toString(),
                        Integer.parseInt(txtCantidad.getText().toString()),
                        Float.parseFloat(txtPrecio.getText().toString())
                );

                productos.add(producto);
                adapter.notifyItemInserted(productos.size()-1);
                refProductos.setValue(productos);
            }
        });
        return builder.create();
    }

    private void cargarProductos() {
        refProductos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productos.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator< ArrayList<Producto> > gti =
                            new GenericTypeIndicator< ArrayList<Producto> >() {};
                    ArrayList<Producto> temp = snapshot.getValue(gti);
                    productos.addAll(temp);
                    Toast.makeText(MainActivity.this, "Elementos descargados: "+productos.size(), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Botón de menú en el toolbar
     * @param menu The options menu in which you place your items.
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
            finish();
        }else (item.getItemId() == R.id.datosMenu){
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constantes.DATABASE,);
            Intent intent = new Intent(this, LogInActivity.class);

            startActivity(intent);
        }
        return true;
    }
}