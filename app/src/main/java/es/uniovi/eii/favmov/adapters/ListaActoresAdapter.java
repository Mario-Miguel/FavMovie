package es.uniovi.eii.favmov.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.uniovi.eii.favmov.R;
import es.uniovi.eii.favmov.model.Actor;

public class ListaActoresAdapter extends RecyclerView.Adapter<ListaActoresAdapter.ActorViewHolder> {


    //Interfaz para manejar el evento de click
    public interface OnItemClickListener{
        void onItemClick(Actor actor);
    }

    private List<Actor> actores;
    private final OnItemClickListener listener;

    public ListaActoresAdapter(List<Actor> actores, OnItemClickListener listener) {
        this.actores = actores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_actors_line, parent, false);
        return new ActorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actores.get(position);

        Log.i("Lista", "Visualiza elemento: "+actor);
        holder.bindUser(actor, listener);

    }

    @Override
    public int getItemCount() {
        return actores.size();
    }

    public static class ActorViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView personaje;
        private ImageView imagen;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_actor);
            personaje = itemView.findViewById(R.id.nombre_personaje);
            imagen = itemView.findViewById(R.id.imagen_actor);
        }

        // asignar valores a los componentes
        public void bindUser(final Actor actor, final OnItemClickListener listener) {
            nombre.setText(actor.getNombre());
            //fecha.setText(pelicula.getFecha());
            personaje.setText(actor.getNombrePersonaje());
            // cargar imagen
            Picasso.get().load(actor.getUrlImagenActor()).into(imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(actor);
                }
            });


        }
    }
}


