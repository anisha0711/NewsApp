package com.example.newsapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private final ArrayList<CategoryRVModal> categoryRVModals;
    private final CategoryClickInterface categoryClickInterface;


    public CategoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModals, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModals = categoryRVModals;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryRVModal categoryRVModal = categoryRVModals.get(position);
        holder.categoryTV.setText(categoryRVModal.getCategoryImageUrl());
        Picasso.get().load(categoryRVModal.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(v -> categoryClickInterface.OnCategoryClick(position));

    }

    @Override
    public int getItemCount() {
        return categoryRVModals.size();
    }
    public interface CategoryClickInterface{
        void OnCategoryClick(int position);
    }

    public interface CategorClickInterface {
        void onCategoryClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView categoryTV;
        private final ImageView categoryIV;
        @SuppressLint("CutPasteId")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.idIVCategory);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
        }
    }
}

