package com.jojinhosrightarm.flashlight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Flash extends AppCompatActivity {

    private static final String TAG = "Flash";
    boolean flashStatus;
    InterstitialAd mInterstitialAd;
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "ClickableViewAccessibility"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.flash);

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        SeekBar flashSpeed = findViewById(R.id.flashSpeed);
        Button startFlash = findViewById(R.id.startFlash);
        Button endFlash = findViewById(R.id.endFlash);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch repeat = findViewById(R.id.repeat);

        endFlash.setEnabled(repeat.isChecked());


        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView adView = (AdView) findViewById(R.id.adViewFlash);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }



            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(Flash.this, "Error when loading the add", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onAdFailedToLoad: " + Objects.requireNonNull(loadAdError.getMessage()).toString());
            }



            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

            }

        });

        double[] num = {2.5, 2.3, 2.1, 1.9, 1.7, 1.5, 1.3, 1.1, 0.9, 0.7, 0.5};
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endFlash.setEnabled(repeat.isChecked());
            }
        });

        endFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashStatus = false;
            }
        });
        startFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashStatus = true;
                Log.d(TAG, "onClick: Start flash button pressed");
                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        if (repeat.isChecked()) {
                            while (true) {
                                if (flashStatus) {
                                    try {
                                        runOnUiThread(new Runnable() {
                                            @RequiresApi(api = Build.VERSION_CODES.M)
                                            @Override
                                            public void run() {
                                                try {
                                                    cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                                                } catch (CameraAccessException e) {
                                                    e.printStackTrace();
                                                }
                                                Log.d(TAG, "startFlash: Torch on!");
                                            }
                                        });

                                        makeDelay(num[flashSpeed.getProgress()] * 100);


                                        runOnUiThread(new Runnable() {
                                            @RequiresApi(api = Build.VERSION_CODES.M)
                                            @Override
                                            public void run() {
                                                try {
                                                    cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                                                } catch (CameraAccessException e) {
                                                    e.printStackTrace();
                                                }
                                                Log.d(TAG, "startFlash: Torch off!");

                                            }
                                        });

                                        makeDelay(num[flashSpeed.getProgress()] * 100);

                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    }
                                } else {
                                    break;
                                }
                            }
                        } else {
                            try {
                                runOnUiThread(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void run() {
                                        try {
                                            cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                                        } catch (CameraAccessException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d(TAG, "startFlash: Torch on!");
                                    }
                                });

                                makeDelay(num[flashSpeed.getProgress()] * 100);


                                runOnUiThread(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void run() {
                                        try {
                                            cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                                        } catch (CameraAccessException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d(TAG, "startFlash: Torch off!");

                                    }
                                });

                                makeDelay(num[flashSpeed.getProgress()] * 100);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
    }

    private void makeDelay(double value) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep((long) value);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int menuId = item.getItemId();



        switch (menuId) {
            case R.id.toggleMenu:
                startActivity(new Intent(getApplicationContext(), Toggle.class));
                break;
            case R.id.morseCodeMenu:

                startActivity(new Intent(getApplicationContext(), MorseCode.class));
                break;
        }

        return true;
    }
}
