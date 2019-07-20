package com.example.filemanagerapplication;

public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(T data);

    void onItemLongClicked(T data, int position);
}
