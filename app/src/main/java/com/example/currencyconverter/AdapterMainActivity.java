package com.example.currencyconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AdapterMainActivity extends RecyclerView.Adapter<AdapterMainActivity.Holder> {

    private ValCurs valCurs;
    List<MyValute> myValuteList = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.valute_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return  myValuteList.size();
    }

    public void setValute(ValCurs valCurs) {
        myValuteList.addAll(valCurs.getValuteList());
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView charCodeTextView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_textView);
            charCodeTextView = itemView.findViewById(R.id.char_code_textView);
        }

        public void bind(int position) {
            nameTextView.setText(myValuteList.get(position).getName());
            charCodeTextView.setText(myValuteList.get(position).getCharCode());
        }
    }
}
