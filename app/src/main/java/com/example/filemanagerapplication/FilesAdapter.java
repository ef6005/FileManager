package com.example.filemanagerapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesViewHolder> {
    private List<File> mFiles = new ArrayList<>();
    private OnRecyclerViewItemClickListener<File> mOnItemListener;

    public FilesAdapter(List<File> files, OnRecyclerViewItemClickListener<File> onClickListener) {
        if (files != null)
            this.mFiles = files;
        this.mOnItemListener = onClickListener;
    }

    @NonNull
    @Override
    public FilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FilesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_file, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilesViewHolder filesViewHolder, int i) {
        filesViewHolder.bindFile(mFiles.get(i));
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class FilesViewHolder extends RecyclerView.ViewHolder {
        private ImageView fileIconIv;
        private TextView fileNameTv;
        private ViewGroup mViewGroup;

        public FilesViewHolder(@NonNull View itemView) {
            super(itemView);

            fileIconIv = itemView.findViewById(R.id.itemFile_iv_fileIcon);
            fileNameTv = itemView.findViewById(R.id.itemFile_tv_fileName);
            mViewGroup = (ViewGroup) itemView;
        }

        public void bindFile(File file) {
            fileNameTv.setText(file.getName());

            if (file.isFile()) {
                fileIconIv.setImageResource(R.drawable.ic_file_white_80dp);
            } else if (file.isDirectory()) {
                fileIconIv.setImageResource(R.drawable.ic_directory_yellow400_80dp);
            }

            mViewGroup.setOnClickListener(view -> mOnItemListener.onItemClick(mFiles.get(getAdapterPosition())));
            mViewGroup.setOnLongClickListener(view -> {
                mOnItemListener.onItemLongClicked(mFiles.get(getAdapterPosition()), getAdapterPosition());
                return true;
            });
        }
    }

}
