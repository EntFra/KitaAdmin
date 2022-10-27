package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;

import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolderUsuarios> {

        List<Usuarios> listaUsuarios;
        private final UsuariosAdapter.OnUsuariosListener onUsuariosListener;
        private final LayoutInflater inflater;


        public interface OnUsuariosListener {
            void onUsuariosClick(int position);
        }

    public UsuariosAdapter(Context context, List<Usuarios> listaUsuarios, UsuariosAdapter.OnUsuariosListener onUsuariosListener) {
            this.listaUsuarios = listaUsuarios;
            this.inflater = LayoutInflater.from(context);
            this.onUsuariosListener = onUsuariosListener;
        }

        @NonNull
        @Override
        public UsuariosAdapter.ViewHolderUsuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_usuarios, null, false);
            return new ViewHolderUsuarios(view, onUsuariosListener);
        }

        @Override
        public void onBindViewHolder(@NonNull UsuariosAdapter.ViewHolderUsuarios holder, int position) {
            if(listaUsuarios.get(position).getNombre()!=null){
                holder.nombreUsuario.setText(listaUsuarios.get(position).getNombre());
            }

        }

        @Override
        public int getItemCount() {
            return listaUsuarios.size();
        }

        protected class ViewHolderUsuarios extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView nombreUsuario;
            UsuariosAdapter.OnUsuariosListener onUsuariosListener;
            ImageButton deleteButton;
            ImageButton updateButton;
            ImageButton addButton;

            public ViewHolderUsuarios(@NonNull View itemView, UsuariosAdapter.OnUsuariosListener onUsuariosListener) {
                super(itemView);
                nombreUsuario = itemView.findViewById(R.id.titleUsuarios);
                this.onUsuariosListener = onUsuariosListener ;
                itemView.setOnClickListener(this);

                updateButton = itemView.findViewById(R.id.editUsuario);
                addButton = itemView.findViewById(R.id.addUsuario);
                deleteButton = itemView.findViewById(R.id.deleteUsuario);
            }

            @Override
            public void onClick(View view) { 
                
                if(view.equals(deleteButton)){
                    deleteUsuario(getAdapterPosition());
                }else if(view.equals(addButton)){
                    addUsuario();
                }else if(view.equals(updateButton)){
                    editUsuario(getAdapterPosition());
                }
                onUsuariosListener .onUsuariosClick(getAdapterPosition()); }

             }

    private void editUsuario(int adapterPosition) {
    }

    private void addUsuario() {
    }

    private void deleteUsuario(int adapterPosition) {
                    
            }


}