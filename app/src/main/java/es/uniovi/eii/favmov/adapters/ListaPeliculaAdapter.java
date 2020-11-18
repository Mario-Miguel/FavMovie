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
import es.uniovi.eii.favmov.model.Pelicula;

public class ListaPeliculaAdapter extends RecyclerView.Adapter<ListaPeliculaAdapter.PeliculaViewHolder> {


    //Interfaz para manejar el evento de click
    public interface OnItemClickListener{
        void onItemClick(Pelicula pelicula);
    }

    private List<Pelicula> peliculas;
    private final OnItemClickListener listener;

    public ListaPeliculaAdapter(List<Pelicula> peliculas, OnItemClickListener listener) {
        this.peliculas = peliculas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_movies_line, parent, false);
        return new PeliculaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder holder, int position) {
        Pelicula pelicula = peliculas.get(position);

        Log.i("Lista", "Visualiza elemento: "+pelicula);
        holder.bindUser(pelicula, listener);

    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public static class PeliculaViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo;
        private TextView fecha;
        private ImageView imagen;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txTituloPelicula);
            fecha = itemView.findViewById(R.id.txFechaEstreno);
            imagen = itemView.findViewById(R.id.imgPelicula);
        }

        // asignar valores a los componentes
        public void bindUser(final Pelicula pelicula, final OnItemClickListener listener) {
            titulo.setText(pelicula.getTitulo()+" ("+pelicula.getFecha().substring(0,4)+")");
            //fecha.setText(pelicula.getFecha());
            fecha.setText(pelicula.getFecha());
            // cargar imagen

            if(!pelicula.getUrlCaratula().isEmpty())
                Picasso.get().load(pelicula.getUrlCaratula()).into(imagen);
            else
                imagen.setImageResource(R.drawable.pelicula_sin_imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(pelicula);
                }
            });


        }
    }
}


