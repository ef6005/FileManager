package com.example.filemanagerapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
//    private OnRecyclerViewItemClickListener<File> mOnItemClickListener;

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
        mAdapter = new FilesAdapter(files, new OnRecyclerViewItemClickListener<File>() {
            @Override
            public void onItemClick(File file) {
                //TODO:###implement multi select mode

                //click on file or directory
                ((MainActivity) getActivity()).showDirectoryOrFile(file, true);
            }

            @Override
            public void onItemLongClicked(File file, int position) {
                //TODO:###implement multi select mode

                //TODO:#Long click on file or directory
            }
        });

        //recycler view
        mFilesRv = view.findViewById(R.id.fragmentDirectoryFiles_rv_files);
        mFilesRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFilesRv.setAdapter(mAdapter);
    }



    public void refresh() {
        Log.i("hamid", "refresh: " + getPath());
    }
}
