package gor.oda.eregistre.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gor.oda.eregistre.R;
import gor.oda.eregistre.models.Academicien;

class AdapterAcad extends RecyclerView.Adapter<AdapterAcad.ViewHolder> {

    private ArrayList<Academicien> academiciens;
    private Context context;

    public AdapterAcad(Context context, ArrayList<Academicien> academiciens){
        this.academiciens=academiciens;
        this.context=context;
    }
    @NonNull
    @Override
    public AdapterAcad.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.acad_list_item,viewGroup,false);
        return new AdapterAcad.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAcad.ViewHolder viewHolder, int i) {

        viewHolder.nom.setText(academiciens.get(i).getNom());
        viewHolder.prenoms.setText(academiciens.get(i).getPrenom());
        viewHolder.matricule.setText(academiciens.get(i).getMatricule());


    }

    @Override
    public int getItemCount() {
        return academiciens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nom;
        private TextView prenoms;
        private TextView matricule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = (TextView)itemView.findViewById(R.id.nom);
            prenoms = (TextView)itemView.findViewById(R.id.prenom);
            matricule = (TextView)itemView.findViewById(R.id.matricule);
        }
    }
}
