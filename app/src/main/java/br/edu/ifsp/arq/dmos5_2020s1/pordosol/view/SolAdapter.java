package br.edu.ifsp.arq.dmos5_2020s1.pordosol.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.pordosol.R;
import br.edu.ifsp.arq.dmos5_2020s1.pordosol.model.Sol;

public class SolAdapter extends RecyclerView.Adapter<SolAdapter.SolViewHolder> {

    private List<Sol> sol;

    public SolAdapter(@NonNull List<Sol> sol) {
        this.sol = sol;
    }

    @NonNull
    @Override
    public SolAdapter.SolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sol_adapter, parent, false);
        SolViewHolder viewHolder = new SolViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SolAdapter.SolViewHolder holder, int position) {
        holder.sunriseTextView.setText(sol.get(position).getSunrise());
        holder.sunsetTextView.setText(sol.get(position).getSunrise());
    }

    class SolViewHolder extends RecyclerView.ViewHolder {

        private final TextView sunriseTextView;
        private final TextView sunsetTextView;

        public SolViewHolder(@NonNull View itemView) {
            super(itemView);
            sunriseTextView = itemView.findViewById(R.id.textview_sunrise);
            sunsetTextView = itemView.findViewById(R.id.textview_sunset);
        }
    }

    @Override
    public int getItemCount() {
        if (sol != null){
            return sol.size();
        }
        return 0;
    }
}