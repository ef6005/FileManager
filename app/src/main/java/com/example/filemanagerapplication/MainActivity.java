package com.example.filemanagerapplication;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//single activity application
//MainActivity manages all fragment transactions
public class MainActivity extends AppCompatActivity {
    private static final int WRITE_PERMISSION_REQUEST_CODE = 1001;

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
        givePermissionBtn.setOnClickListener(this::beginRequestPermission);
        emptyStateViewGroup.setVisibility(View.GONE);

        beginRequestPermission(null);
        //do nothing here that needs permission
    }

    private void beginRequestPermission(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //do not show permission emptyState
                //TODO:### show another empty staye if needed
                emptyStateViewGroup.setVisibility(View.GONE);
                beginActivity();
                return;
            } else {//show emptyState
                emptyStateViewGroup.setVisibility(View.VISIBLE);
                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void beginActivity() {
        setTitleBar("Permission Granted");
    }

    private void setTitleBar(String title) {
        TextView titleBarTv = findViewById(R.id.main_tv_titleBar);
        titleBarTv.setText(title);
    }
}
