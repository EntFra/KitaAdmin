package com.example.kitaadmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Adapter del recyclerView de usuarios, carga la información disponible en la base de datos
 */
public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuariosViewHolder> {
    private final List<Usuarios> list;
    private final List<Usuarios> listaSearch;
    //Filtra la lista para mostrar el resultado de la entrada del usuario en la vista
    private final Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Usuarios> listaFiltrada = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(listaSearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Usuarios usuario : listaSearch) {
                    if (usuario.getNombre().toLowerCase().contains(filterPattern)) {
                        listaFiltrada.add(usuario);
                    }
                }
            }

            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;

            return resultado;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    private OnItemClickListener listener;

    public UsuariosAdapter(List<Usuarios> list) {
        this.list = list;
        listaSearch = new ArrayList<>(list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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

    public Filter getFilter() {
        return searchFilter;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);

        void onEditClick(int position);
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
}