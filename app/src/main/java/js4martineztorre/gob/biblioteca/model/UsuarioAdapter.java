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
import js4martineztorre.gob.biblioteca.VerUsuarioActivity;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios = new ArrayList<>();

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioAdapter.UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);

        holder.nombre.setText(usuario.getNombre() + " " + usuario.getApellido());
        holder.role.setText(usuario.getRole_nombre());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerUsuarioActivity.class);
            intent.putExtra("usuario_id", usuario.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        notifyDataSetChanged();
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, role;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txt_nombre_usuario);
            role = itemView.findViewById(R.id.txt_role);
        }
    }

}