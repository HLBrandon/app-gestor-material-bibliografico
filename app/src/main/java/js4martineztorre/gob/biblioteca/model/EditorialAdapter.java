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
import js4martineztorre.gob.biblioteca.VerEditorialActivity;

public class EditorialAdapter extends RecyclerView.Adapter<EditorialAdapter.EditorialViewHolder> {

    private List<Editorial> editoriales = new ArrayList<>();

    @NonNull
    @Override
    public EditorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new EditorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditorialAdapter.EditorialViewHolder holder, int position) {
        Editorial editorial = editoriales.get(position);

        holder.nombre.setText(editorial.getNombre());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerEditorialActivity.class);
            intent.putExtra("editorial_id", editorial.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return editoriales.size();
    }

    public void setEditoriales(List<Editorial> editoriales) {
        this.editoriales = editoriales;
        notifyDataSetChanged();
    }

    static class EditorialViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;

        public EditorialViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreCategoria);
        }
    }

}