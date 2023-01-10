package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.R;
/**
 * Adapter del recyclerView de ayuda que carga los datos del menu ayuda
 */

public class AyudaAdapter extends RecyclerView.Adapter<AyudaAdapter.ViewHolderAyuda> {
    private final OnAyudaListener onAyudaListener;
    String[] listaAyudaTitles;
    String[] listaAyudaDescriptions;


    public AyudaAdapter(Context context, String[] listaAyudaTitles, String[] listaAyudaDescriptions, AyudaAdapter.OnAyudaListener onAyudaListener) {
        this.listaAyudaTitles = listaAyudaTitles;
        this.listaAyudaDescriptions = listaAyudaDescriptions;
        this.onAyudaListener = onAyudaListener;
        LayoutInflater inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AyudaAdapter.ViewHolderAyuda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ayuda, null, false);
        return new ViewHolderAyuda(view, onAyudaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AyudaAdapter.ViewHolderAyuda holder, int position) {
        if (listaAyudaTitles.length != 0 && listaAyudaDescriptions.length != 0) {
            holder.textItemAyudaTitle.setText(listaAyudaTitles[position]);
            holder.textDescription.setText(listaAyudaDescriptions[position]);
        }


    }

    @Override
    public int getItemCount() {
        return listaAyudaTitles.length;
    }

    public interface OnAyudaListener {
        void onAyudaClick(int position);
    }

    public class ViewHolderAyuda extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textItemAyudaTitle;
        TextView textDescription;
        LinearLayout ly;
        OnAyudaListener onAyudaListener;


        public ViewHolderAyuda(@NonNull View itemView, OnAyudaListener onAyudaListener) {
            super(itemView);
            ly = itemView.findViewById(R.id.layoutDescription);
            textItemAyudaTitle = itemView.findViewById(R.id.textHelpItem);
            textDescription = itemView.findViewById(R.id.textDescription);
            this.onAyudaListener = onAyudaListener;
            itemView.setOnClickListener(this);

            textItemAyudaTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int v = (textDescription.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                    TransitionManager.beginDelayedTransition((ly), new AutoTransition());
                    textDescription.setVisibility(v);
                }
            });
        }

        @Override
        public void onClick(View view) {
            onAyudaListener.onAyudaClick(getAdapterPosition());

        }
    }
}
