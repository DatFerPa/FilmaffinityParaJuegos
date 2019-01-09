package com.filmaffinityparajuegos.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.filmaffinityparajuegos.R;
import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.MyViewHolder>{

    private List<VideojuegoBase> juegosParaComentario;

    public ComentarioAdapter(List<VideojuegoBase> juegosBase){this.juegosParaComentario = juegosBase;}

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_comentarios,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        VideojuegoBase juego = juegosParaComentario.get(position);
        holder.usuarioComent.setText(juego.getNombre_usuario());
        holder.comentario.setText(juego.getComentario());
    }

    @Override
    public int getItemCount() {
        return juegosParaComentario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView comentario;
        public TextView usuarioComent;
        public MyViewHolder(View itemView) {
            super(itemView);
            comentario = (TextView)itemView.findViewById(R.id.comentario);
            usuarioComent = (TextView)itemView.findViewById(R.id.usuarioComentador);
        }
    }

}
