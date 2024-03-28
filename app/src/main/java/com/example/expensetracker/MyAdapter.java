package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Value> valuesList;

    public MyAdapter(List<Value> valuesList) {
        this.valuesList = valuesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expensive_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Value value = valuesList.get(position);
        holder.descriptionTextView.setText(value.getDescription());
        holder.amountTextView.setText(value.getAmount());
        holder.dateTextView.setText(value.getDate());
    }

    @Override
    public int getItemCount() {
        return valuesList.size();
    }


    public void setData(List<Value> valuesList) {
        valuesList.clear();
        valuesList.addAll(valuesList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;
        TextView amountTextView;
        TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.description);
            amountTextView = itemView.findViewById(R.id.amount);
            dateTextView = itemView.findViewById(R.id.date);
        }
    }
}
