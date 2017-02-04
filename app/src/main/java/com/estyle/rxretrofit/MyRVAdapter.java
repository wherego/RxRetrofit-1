package com.estyle.rxretrofit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estyle.rxretrofit.databinding.ItemFoodBinding;

import java.util.ArrayList;
import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private List<FoodBean.DataBean> datas;

    private OnChildClickListener onChildClickListener;
    private OnChildLongClickListener onChildLongClickListener;

    public MyRVAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    private int getLayoutId() {
        return R.layout.item_food;
    }

    public void addDatas(List<FoodBean.DataBean> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(FoodBean.DataBean data) {
        datas.add(data);
        notifyDataSetChanged();
    }

    public List<FoodBean.DataBean> getDatas() {
        return datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFoodBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), parent, false);
        View itemView = binding.getRoot();
        ViewHolder holder = new ViewHolder(itemView);
        initItemViewListener(itemView);
        holder.setBinding(binding);
        return holder;
    }

    private void initItemViewListener(View itemView) {
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemFoodBinding binding = holder.getBinding();
        binding.setBean(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View view) {
        int position;
        switch (view.getId()) {
            default:
                if (onChildClickListener != null) {
                    position = ((RecyclerView) view.getParent()).getChildLayoutPosition(view);
                    onChildClickListener.onChildClick(position);
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        int position;
        switch (view.getId()) {
            default:
                if (onChildLongClickListener != null) {
                    position = ((RecyclerView) view.getParent()).getChildLayoutPosition(view);
                    onChildLongClickListener.onChildLongClick(position);
                }
                break;
        }
        return true;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFoodBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ItemFoodBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemFoodBinding binding) {
            this.binding = binding;
        }
    }

    public interface OnChildClickListener {
        public void onChildClick(int position);
    }

    public interface OnChildLongClickListener {
        public void onChildLongClick(int position);
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public void setOnChildLongClickListener(OnChildLongClickListener onChildLongClickListener) {
        this.onChildLongClickListener = onChildLongClickListener;
    }

}