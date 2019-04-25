package com.CNPM.letcook.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.CNPM.letcook.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMaking extends RecyclerView.Adapter<AdapterMaking.RecyclerViewHolder> {

    private List<String> Makings ;

    public AdapterMaking(List<String> makings) {
        Makings = makings;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaking;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaking = itemView.findViewById(R.id.txtMaking);
        }
    }


    @NonNull
    @Override
    public AdapterMaking.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_item_making, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMaking.RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.txtMaking.setText(Makings.get(i));
    }

    @Override
    public int getItemCount() {
        return Makings.size();
    }


}
