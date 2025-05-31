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
import js4martineztorre.gob.biblioteca.VerPrestamoActivity;

public class PrestamoAdapter extends RecyclerView.Adapter<PrestamoAdapter.PrestamoViewHolder> {

    private List<Prestamo> prestamos = new ArrayList<>();

    @NonNull
    @Override
    public PrestamoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prestamo, parent, false);
        return new PrestamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrestamoViewHolder holder, int position) {
        Prestamo prestamo = prestamos.get(position);

        holder.titulo.setText(prestamo.getLibro_titulo());
        holder.isbn.setText(prestamo.getLibro_isbn());
        holder.nombre.setText(prestamo.getLector_nombre() + " " + prestamo.getLector_apellido());
        holder.correo.setText(prestamo.getLector_correo());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerPrestamoActivity.class);
            intent.putExtra("prestamo_id", prestamo.getId());
            intent.putExtra("retraso", prestamo.getRetraso());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return prestamos.size();
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
        notifyDataSetChanged();
    }

    static class PrestamoViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, isbn, nombre, correo;

        public PrestamoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txt_titulo);
            isbn = itemView.findViewById(R.id.txt_isbn);
            nombre = itemView.findViewById(R.id.txt_nombre);
            correo = itemView.findViewById(R.id.txt_correo);
        }
    }
}