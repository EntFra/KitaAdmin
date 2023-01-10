package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.R;

import java.util.List;
/**
 * Adapter del recyclerView de grupos que carga los grupos disponibles en la base de datos
 */
public class GruposAdapter extends RecyclerView.Adapter<GruposAdapter.ViewHolderGrupos> {

    private final OnGruposListener onGruposListener;
    private final LayoutInflater inflater;
    List<Grupos> listaGrupos;

    public GruposAdapter(Context context, List<Grupos> listaGrupos, OnGruposListener onGruposListener) {
        this.listaGrupos = listaGrupos;
        this.inflater = LayoutInflater.from(context);
        this.onGruposListener = onGruposListener;
    }

    @NonNull
    @Override
    public ViewHolderGrupos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_grupos, null, false);
        return new ViewHolderGrupos(view, onGruposListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GruposAdapter.ViewHolderGrupos holder, int position) {
        //byte[] imagen = listaGrupos.get(position).getFotoGrupo();
        //Bitmap bitmap = Utils.getImage(imagen);
        //holder.grupoImagen.setImageBitmap(bitmap);

        holder.nombreGrupo.setText(listaGrupos.get(position).getNombreGrupo());
    }

    @Override
    public int getItemCount() {
        return listaGrupos.size();
    }

    //Maneja el click sobre un objeto
    public interface OnGruposListener {
        void onGruposClick(int position);

    }

    public class ViewHolderGrupos extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombreGrupo;
        OnGruposListener onGruposListener;

        public ViewHolderGrupos(@NonNull View itemView, OnGruposListener onGruposListener) {
            super(itemView);
            nombreGrupo = itemView.findViewById(R.id.nombreGrupo);
            this.onGruposListener = onGruposListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onGruposListener.onGruposClick(getAdapterPosition());

        }
    }
}
