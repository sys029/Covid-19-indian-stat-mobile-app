package com.example.covid19india.ui.india;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19india.R;

import java.util.ArrayList;
import java.util.List;

class CovidIndiaAdapter extends RecyclerView.Adapter<CovidIndiaAdapter.ViewHolder> implements Filterable {

    private List<CovidIndia> covidIndias;
    private List<CovidIndia> covidIndiasFull;
    private Context context;

    public CovidIndiaAdapter(List<CovidIndia> covidIndias, Context context) {
        this.covidIndias = covidIndias;
        covidIndiasFull = new ArrayList<>(covidIndias);
        this.context = context;
    }

    @NonNull
    @Override
    public CovidIndiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_india_states,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidIndiaAdapter.ViewHolder holder, int position) {
        CovidIndia covidIndia=covidIndias.get(position);
        holder.tvStateTotalCases.setText(covidIndia.getmCases());
        holder.tvStateName.setText(covidIndia.getmCovidState());
    }

    @Override
    public int getItemCount() {
        return covidIndias.size()-2;
    }



    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView tvStateTotalCases,tvStateName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStateTotalCases=itemView.findViewById(R.id.tvStateTotalCases);
            tvStateName=itemView.findViewById(R.id.tvStateName);

        }
    }

    @Override
    public Filter getFilter() {
        return covidIndiaFilter;
    }

    private Filter covidIndiaFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CovidIndia> filteredCovidIndia=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredCovidIndia.addAll(filteredCovidIndia);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (CovidIndia itemCovidCountry:covidIndiasFull){
                    if (itemCovidCountry.getmCovidState().toLowerCase().contains(filterPattern)){
                        filteredCovidIndia.add(itemCovidCountry);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values=filteredCovidIndia;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            covidIndias.clear();
            covidIndias.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
