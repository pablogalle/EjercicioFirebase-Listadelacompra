package com.example.ejerciciofirebase_listadelacompra.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejerciciofirebase_listadelacompra.R;
import com.example.ejerciciofirebase_listadelacompra.modelos.Producto;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoVH> {
    private Context context;
    private List<Producto> objects;
    private int cardLayout;
    private DatabaseReference refDatabase;

    public ProductosAdapter(Context context, List<Producto> objects, int cardLayout, DatabaseReference refUser) {
        this.context = context;
        this.objects = objects;
        this.cardLayout = cardLayout;
        this.refDatabase = refUser;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoView = LayoutInflater.from(context).inflate(cardLayout, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        productoView.setLayoutParams(layoutParams);
        return new ProductoVH(productoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        Producto producto = objects.get(position);
        holder.lblNombre.setText(producto.getNombre());
        holder.lblCantidad.setText(String.valueOf(producto.getCantidad()));
        holder.lblPrecio.setText(String.valueOf(producto.getPrecioMoneda()));

        holder.btnDelete.setOnClickListener(view ->{
            objects.remove(producto);
            //notifyItemRemoved(holder.getAdapterPosition());
            refDatabase.setValue(objects);
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ProductoVH extends RecyclerView.ViewHolder {
        TextView lblNombre;
        TextView lblCantidad;
        TextView lblPrecio;
        ImageButton btnDelete;
        public ProductoVH(@NonNull View itemView) {
            super(itemView);
            lblNombre = itemView.findViewById(R.id.lblNombreCard);
            lblCantidad = itemView.findViewById(R.id.lblCantidadCard);
            lblPrecio = itemView.findViewById(R.id.lblPrecioCard);
            btnDelete = itemView.findViewById(R.id.btnDeleteCard);
        }
    }
}
