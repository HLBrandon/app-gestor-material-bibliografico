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
import js4martineztorre.gob.biblioteca.VerCategoriaActivity;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> categorias = new ArrayList<>();

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.CategoriaViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);

        holder.nombre.setText(categoria.getNombre());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerCategoriaActivity.class);
            intent.putExtra("categoria_id", categoria.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }

    static class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreCategoria);
        }
    }

}
