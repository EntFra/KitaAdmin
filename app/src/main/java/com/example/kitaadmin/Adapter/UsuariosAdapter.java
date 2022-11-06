package com.example.kitaadmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuariosViewHolder> {
    private final List<Usuarios> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);

        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class UsuariosViewHolder extends RecyclerView.ViewHolder {
        public TextView textoUsuario;
        public ImageButton deleteButton;
        public ImageButton editButton;

        public UsuariosViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textoUsuario = itemView.findViewById(R.id.titleUsuarios);
            deleteButton = itemView.findViewById(R.id.deleteUsuario);
            editButton = itemView.findViewById(R.id.editUsuario);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }

    public UsuariosAdapter(List<Usuarios> list) {
        this.list = list;
    }

    @Override
    public UsuariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_usuarios, parent, false);
        UsuariosViewHolder vh = new UsuariosViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(UsuariosViewHolder holder, int position) {
        Usuarios usuario = list.get(position);
        holder.textoUsuario.setText(usuario.getNombre());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}