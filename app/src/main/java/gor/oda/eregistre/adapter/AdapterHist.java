package gor.oda.eregistre.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gor.oda.eregistre.R;
import gor.oda.eregistre.models.Historique;

public class AdapterHist extends RecyclerView.Adapter<AdapterHist.ViewHolder> {

    private ArrayList<Historique> historiques;
    private Context context;

    public AdapterHist(Context context, ArrayList<Historique> historiques){
        this.historiques=historiques;
        this.context=context;
    }
    @NonNull
    @Override
    public AdapterHist.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item,viewGroup,false);
        return new AdapterHist.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHist.ViewHolder viewHolder, int i) {
        viewHolder.nom.setText(historiques.get(i).getVisiteur().getNom()+ " "+historiques.get(i).getVisiteur().getPrenoms());
        viewHolder.h_arrive.setText(historiques.get(i).getDateArrive());
        viewHolder.h_depart.setText(historiques.get(i).getDateDepart());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        context);
                builder.setTitle("Motif de la visite");
                builder.setMessage(historiques.get(i).getMotif());
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historiques.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nom;
        private TextView prenoms;
        private TextView motif;
        private TextView h_depart;
        private TextView h_arrive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = (TextView)itemView.findViewById(R.id.nom);
            h_arrive = (TextView)itemView.findViewById(R.id.h_arrive);
            h_depart = (TextView)itemView.findViewById(R.id.h_depart);

        }


    }
}
