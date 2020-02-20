package com.example.currencyconverter.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyconverter.R;
import com.example.currencyconverter.data.model.MyValute;
import com.example.currencyconverter.data.model.ValCurs;
import com.example.currencyconverter.ui.main.OnClickListener;

import java.util.ArrayList;
import java.util.List;


public class AdapterMainActivity extends RecyclerView.Adapter<AdapterMainActivity.Holder> {

    private List<MyValute> myValuteList = new ArrayList<>();
    private OnClickListener onClickListener;
    private String firstCharCode = null;

    public AdapterMainActivity(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

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
        return myValuteList.size();
    }

    public void setValute(ValCurs valCurs, String firstCharCode) {
        myValuteList.clear();

        myValuteList.addAll(valCurs.getValuteList());
        this.firstCharCode = firstCharCode;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView charCodeTextView;
        ImageView imageView;

        Holder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_textView);
            charCodeTextView = itemView.findViewById(R.id.char_code_textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(int position) {
            nameTextView.setText(myValuteList.get(position).getName());
            charCodeTextView.setText(myValuteList.get(position).getCharCode());

            if (firstCharCode != null && firstCharCode == myValuteList.get(position).getCharCode()) {
                imageView.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(v -> onClickListener.onItemClick(position));
        }
    }
}
