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
import js4martineztorre.gob.biblioteca.VerAutorActivity;

public class AutorAdapter extends RecyclerView.Adapter<AutorAdapter.AutorViewHolder> {

    private List<Autor> autores = new ArrayList<>();

    @NonNull
    @Override
    public AutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new AutorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AutorAdapter.AutorViewHolder holder, int position) {
        Autor autor = autores.get(position);

        holder.nombre.setText(autor.getNombre());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerAutorActivity.class);
            intent.putExtra("autor_id", autor.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return autores.size();
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
        notifyDataSetChanged();
    }

    static class AutorViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;

        public AutorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreCategoria);
        }
    }

}