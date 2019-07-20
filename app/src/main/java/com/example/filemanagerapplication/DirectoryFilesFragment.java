package com.example.filemanagerapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryFilesFragment extends Fragment {
    private static final String DIRECTORY_PATH_KEY = "directoryPath";
    private File mDirectory;
    private RecyclerView mFilesRv;
    private FilesAdapter mAdapter;
    private OnRecyclerViewItemClickListener<File> mOnItemClickListener;

    public String getPath() {
        return mDirectory.getPath();
    }

    public static DirectoryFilesFragment newInstance(String directoryPath) throws IOException {
        File directory = new File(directoryPath);

        if (!directory.isDirectory()) {
            throw new IOException("DirectoryFilesFragment.newInstance needs Directory as parameter");
        }
        Bundle args = new Bundle();
        args.putString(DIRECTORY_PATH_KEY, directoryPath);
        DirectoryFilesFragment fragment = new DirectoryFilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mDirectory = new File(getArguments().getString(DIRECTORY_PATH_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_directory_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup adapter
        List<File> files = new ArrayList<>(Arrays.asList(mDirectory.listFiles()));
        mAdapter = new FilesAdapter(files
                , (File file, int position) -> {
            if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(file, position);
        });

        //recycler view
        mFilesRv = view.findViewById(R.id.fragmentDirectoryFiles_rv_files);
        mFilesRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFilesRv.setAdapter(mAdapter);
    }

    private void onFileClicked(View view) {
        //TODO:roll over to main activity
    }

    private boolean onFileLongClicked(View view) {
        //TODO:roll over to main activity
        return false;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<File> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void refresh() {
        Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
    }
}
