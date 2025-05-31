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
import js4martineztorre.gob.biblioteca.VerHistorialActivity;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<Prestamo> historial = new ArrayList<>();

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Prestamo prestamo = historial.get(position);

        holder.titulo.setText(prestamo.getLibro_titulo());
        holder.isbn.setText(prestamo.getLibro_isbn());
        holder.nombre.setText(prestamo.getLector_nombre() + " " + prestamo.getLector_apellido());
        holder.correo.setText(prestamo.getLector_correo());
        holder.fechaPrestamo.setText("Préstamo: " + prestamo.getFecha_prestamo());
        holder.fechaDevolucion.setText("Devolución: " + prestamo.getFecha_devolucion());
        holder.estatus.setText(prestamo.getEstatus() == 1 ? "Prestado" : "Devuelto");
        holder.perdido.setText(prestamo.getPerder() == 1 ? "Perdido" : "");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerHistorialActivity.class);
            intent.putExtra("prestamo_id", prestamo.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historial.size();
    }

    public void setHistorial(List<Prestamo> historial) {
        this.historial = historial;
        notifyDataSetChanged();
    }

    static class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, isbn, nombre, correo, fechaPrestamo, fechaDevolucion, estatus, perdido;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txt_titulo);
            isbn = itemView.findViewById(R.id.txt_isbn);
            nombre = itemView.findViewById(R.id.txt_nombre);
            correo = itemView.findViewById(R.id.txt_correo);
            fechaPrestamo = itemView.findViewById(R.id.txt_fecha_prestamo);
            fechaDevolucion = itemView.findViewById(R.id.txt_fecha_devolucion);
            estatus = itemView.findViewById(R.id.txt_estatus);
            perdido = itemView.findViewById(R.id.txt_perdido);
        }
    }

}