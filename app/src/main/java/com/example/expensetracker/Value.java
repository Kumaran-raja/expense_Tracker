package com.example.expensetracker;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Value{

    String description;
    String amount;
    String date;

    public Value(listPage listPage, List<Value> dataList) {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Value(String description, String amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }


}
