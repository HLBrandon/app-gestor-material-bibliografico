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
import js4martineztorre.gob.biblioteca.VerLectorActivity;

public class LectorAdapter extends RecyclerView.Adapter<LectorAdapter.LectorViewHolder> {

    private List<Lector> lectores = new ArrayList<>();

    @NonNull
    @Override
    public LectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lector, parent, false);
        return new LectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectorAdapter.LectorViewHolder holder, int position) {
        Lector lector = lectores.get(position);

        holder.nombre.setText(lector.getNombre() + " " + lector.getApellido());
        holder.correo.setText(lector.getCorreo());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerLectorActivity.class);
            intent.putExtra("lector_id", lector.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lectores.size();
    }

    public void setLectores(List<Lector> lectores) {
        this.lectores = lectores;
        notifyDataSetChanged();
    }

    static class LectorViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, correo;

        public LectorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txt_nombre_lector);
            correo = itemView.findViewById(R.id.txt_correo);
        }
    }
}