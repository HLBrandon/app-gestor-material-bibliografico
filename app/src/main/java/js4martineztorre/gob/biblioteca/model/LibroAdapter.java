package js4martineztorre.gob.biblioteca.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import js4martineztorre.gob.biblioteca.R;
import js4martineztorre.gob.biblioteca.VerLibroActivity;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {

    private List<Libro> libros = new ArrayList<>();

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroAdapter.LibroViewHolder holder, int position) {
        Libro libro = libros.get(position);

        holder.titulo.setText(libro.getTitulo());
        holder.autor.setText(libro.getAutor_nombre());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerLibroActivity.class);
            intent.putExtra("libro_id", libro.getId());
            intent.putExtra("role_id", libro.getRole_id());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        notifyDataSetChanged();
    }

    static class LibroViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, autor;

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txt_titulo_libro);
            autor = itemView.findViewById(R.id.txt_autor_nombre);
        }
    }
}

