package com.example.filemanagerapplication;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

//single activity application
//MainActivity manages all fragment transactions
public class MainActivity extends AppCompatActivity {
    private static final int WRITE_PERMISSION_REQUEST_CODE = 1001;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private int pageCounter = 0;

    public Button givePermissionBtn;
    public TextView emptyStateTv;
    public TextView titleBarTv;
    public ViewGroup emptyStateViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find views
        givePermissionBtn = findViewById(R.id.main_btn_givePermission);
        titleBarTv = findViewById(R.id.main_tv_titleBar);
        emptyStateViewGroup = findViewById(R.id.main_cl_emptyStateGroup);

        //init views
        givePermissionBtn.setOnClickListener(this::askPermissionAndBeginActivity);
        emptyStateViewGroup.setVisibility(View.GONE);

        askPermissionAndBeginActivity(null);
        //do nothing here that needs permission
    }

    private void askPermissionAndBeginActivity(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST_CODE);
            } else {//we have permission here: or inside onRequestPermissionResult()
                //do not show permission emptyState
                //TODO:### show another empty state if needed
                emptyStateViewGroup.setVisibility(View.GONE);
                File file = android.os.Environment.getExternalStorageDirectory();
                showDirectoryOrFile(file, false);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//we have permission here
                askPermissionAndBeginActivity(null);
                return;
            } else {//show emptyState
                emptyStateViewGroup.setVisibility(View.VISIBLE);
                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setTitleBar(String title) {
        TextView titleBarTv = findViewById(R.id.main_tv_titleBar);
        titleBarTv.setText(title);
    }

    public void showDirectoryOrFile(File directory, boolean addToBackStack) {
        if (directory.isFile()) {
            //TODO:###show file via intents
            Toast.makeText(this, directory.getName() + "is clicked to open", Toast.LENGTH_SHORT).show();
        } else
        //replace new fragment to show directory files
        //check if directory parameter is  really directory or file
        if (directory.isDirectory()) {
            //set title bar to existing path
            setTitleBar(directory.getPath());

            DirectoryFilesFragment directoryFilesFragment = null;
            try {
                directoryFilesFragment = DirectoryFilesFragment.newInstance(directory.getPath());
            } catch (IOException e) {
                Log.e(LOG_TAG, "showDirectoryOrFile(): " + e.getMessage());
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fl_fragmentContainer, directoryFilesFragment);

            if (addToBackStack)
                fragmentTransaction.addToBackStack(null);
            else
                pageCounter++;

            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            pageCounter--;

        if (pageCounter >= 0) {
            DirectoryFilesFragment activeFragment = (DirectoryFilesFragment) getSupportFragmentManager().findFragmentById(R.id.main_fl_fragmentContainer);
            setTitleBar(activeFragment.getPath());
            activeFragment.refresh();
        }
    }
}
