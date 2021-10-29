package com.jojinhosrightarm.flashlight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Toggle extends AppCompatActivity {

    private static final String TAG = "Toggle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.toggle);

        Button toggle = (Button) findViewById(R.id.toggler);
        toggle.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                if (button.getText().toString().equalsIgnoreCase("on")) {
                    try {
                        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    button.setText("OFF");
                } else if (button.getText().toString().equalsIgnoreCase("off")) {
                    try {
                        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    button.setText("ON");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItem = item.getItemId();

        switch (menuItem) {
            case R.id.flashMenu:
                startActivity(new Intent(getApplicationContext(), Flash.class));
                break;
            case R.id.morseCodeMenu:
                startActivity(new Intent(getApplicationContext(), MorseCode.class));
                break;

        }

        return true;
    }
}
