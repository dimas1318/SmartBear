package com.example.android.smartbear.base.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by parsh on 20.01.2018.
 */

public abstract class BaseRecyclerAdapter<T, V extends BaseRecyclerAdapter.BaseViewHolder> extends RecyclerView.Adapter {

    protected ArrayList<T> mData;

    public BaseRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutIdForType(viewType), parent, false);
        return getViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBind(mData.get(holder.getAdapterPosition()), (V) holder, holder.getAdapterPosition());
    }

    protected abstract void onBind(T bean, V holder, int properPosition);

    @LayoutRes
    protected abstract int getItemLayoutIdForType(int viewType);

    protected abstract V getViewHolder(View v);

    public void add(T item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void append(List<T> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(int position, T data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final T data = mData.remove(fromPosition);
        mData.add(toPosition, data);
        notifyItemMoved(fromPosition, toPosition);
    }

    public T removeItem(int position) {
        final T data = mData.remove(position);
        notifyItemRemoved(position);
        return data;
    }

    public <C extends Collection<? extends T>> void set(C items) {
        mData = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public ArrayList<T> getItems() {
        return mData;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        public View root;

        public BaseViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
