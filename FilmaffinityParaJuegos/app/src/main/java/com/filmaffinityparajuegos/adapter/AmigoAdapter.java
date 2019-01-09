package com.filmaffinityparajuegos.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.filmaffinityparajuegos.R;
import com.filmaffinityparajuegos.data.Usuario;

import java.util.List;

public class AmigoAdapter extends RecyclerView.Adapter<AmigoAdapter.MyViewHolder> {

    private List<Usuario> usuarios;

    public AmigoAdapter (List<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public MyViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.NombreAmigo);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_amigos,parent,false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.nombre.setText(usuario.getName());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
