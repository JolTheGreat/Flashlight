  package com.jojinhosrightarm.flashlight;
//THIS FILE WAS LASTLY EDITED ON 

  import android.annotation.SuppressLint;
  import android.content.Context;
  import android.content.Intent;
  import android.content.pm.ActivityInfo;
  import android.hardware.camera2.CameraAccessException;
  import android.hardware.camera2.CameraManager;
  import android.os.Build;
  import android.os.Bundle;
  import android.os.Vibrator;
  import android.util.Log;
  import android.view.Menu;
  import android.view.MenuItem;
  import android.view.View;
  import android.widget.Button;
  import android.widget.EditText;
  import android.widget.Switch;
  import android.widget.TextView;
  import android.widget.Toast;

  import androidx.annotation.NonNull;
  import androidx.annotation.Nullable;
  import androidx.annotation.RequiresApi;
  import androidx.appcompat.app.AppCompatActivity;

  import com.google.android.gms.ads.AdListener;
  import com.google.android.gms.ads.AdRequest;
  import com.google.android.gms.ads.AdView;
  import com.google.android.gms.ads.LoadAdError;
  import com.google.android.gms.ads.MobileAds;

  import java.util.Objects;
  import java.util.concurrent.TimeUnit;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MorseCode extends AppCompatActivity {

    private static final String TAG = "MorseCode";

    int buttonPressed;
    String textEntered;
    boolean vibrationStatus;
    boolean morseCodeSendingStatus;
    CameraManager mCameraManager;
    Vibrator mVibrator;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.morse_code);

        EditText textToTranslate = findViewById(R.id.textToTranslate);
        Button translate = findViewById(R.id.translate);
        Button stopTranslating = findViewById(R.id.stopTranslating);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch vibration = findViewById(R.id.vibration);


        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView adView = (AdView) findViewById(R.id.adViewMorse);
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
                Toast.makeText(MorseCode.this, "Error when loading the add", Toast.LENGTH_SHORT).show();
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

        stopTranslating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morseCodeSendingStatus = false;
                textToTranslate.setEnabled(true);
                translate.setEnabled(true);
                vibration.setEnabled(true);
            }
        });

        vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (morseCodeSendingStatus) {
                    Toast.makeText(MorseCode.this, "Morse code sending still in process.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToTranslate.setEnabled(false);
                if (morseCodeSendingStatus) {
                    Toast.makeText(MorseCode.this, "Morse code sending still in process.", Toast.LENGTH_SHORT).show();
                } else {
                    Button button = (Button) v;
                    String buttonText = button.getText().toString();
                    if (buttonText.equalsIgnoreCase("translate")) {
                        Log.d(TAG, "onClick: Translate pressed");
                        Log.d(TAG, "onClick: The text is " + textToTranslate.getText().toString());
                        textEntered = textToTranslate.getText().toString();
                        buttonPressed = 1;
                    }
                    vibrationStatus = vibration.isChecked();


                    mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    translate.setEnabled(false);
                    vibration.setEnabled(false);


                    final int[] i = {0};
//                new Thread() {
//                    @Override
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//                    }
//                }.start();


                    new Thread() {
                        @Override
                        public void run() {
                            morseCodeSendingStatus = true;
                            final TextView showLettersSending = findViewById(R.id.showLetterSending);
                            super.run();
                            while (i[0]++ < textEntered.split("").length) {

                                if (morseCodeSendingStatus) {
                                    try {
                                        switch (textEntered.split("")[i[0]-1].toLowerCase()) {
                                            case "a":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("A");
                                                    }
                                                });
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "b":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("B");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: b");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "c":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("C");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: c");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "d":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("D");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: d");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "e":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("E");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: e");
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "f":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("F");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: f");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "g":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("G");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: g");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "h" :
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("H");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: h");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "i":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("I");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: i");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "j":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("J");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: j");
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "k":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("K");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: k");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "l":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("L");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: l");
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "m":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("M");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: m");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "n":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("N");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: n");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "o":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("O");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: o");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "p":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("P");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: p");
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "q":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("Q");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: q");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "r":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("R");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: r");
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "s":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("S");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: s");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "t":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("T");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: t");
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "u":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("U");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: u");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "v":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("V");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: v");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "w":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("W");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: w");
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "x":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("X");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: x");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "y":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("Y");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: y");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "z":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("Z");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: z");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                break;
                                            case " ":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("SPACE");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: space");
                                                unitRest(3500);
                                                break;
                                            case "1":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("1");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 1");
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "2":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("2");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 2");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "3":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("3");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 3");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "4":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("4");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 4");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;
                                            case "5":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("5");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 5");
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "6":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("6");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 6");
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "7":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("7");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 7");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "8":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("8");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 8");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "9":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("9");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 9");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                shortLight();
                                                unitRest(1500);
                                                break;
                                            case "0":
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showLettersSending.setText("0");
                                                    }
                                                });
                                                Log.d(TAG, "interpretText: 0");
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(500);
                                                longLight();
                                                unitRest(1500);
                                                break;

                                            default:
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(MorseCode.this, "Text unreadable", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                break;
                                        }
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    }
                                }
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    translate.setEnabled(true);
                                    vibration.setEnabled(true);
                                    showLettersSending.setText("");
                                }
                            });
                        }
                    }.start();

                    textToTranslate.setText("");
                }


            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void longLight() {
        try {
            if (vibrationStatus) {
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], true);
                mVibrator.vibrate(1500);
                unitRest(1500);
                Log.d(TAG, "longLight: Torch on!");
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], false);
                Log.d(TAG, "longLight: Torch off");
                unitRest(1500);
            } else {
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], true);
                Log.d(TAG, "longLight: Torch on!");
                unitRest(1500);
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], false);
                Log.d(TAG, "longLight: Torch off!");
                unitRest(1500);
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void shortLight() {
        try {
            if (vibrationStatus) {
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], true);
                Log.d(TAG, "shortLight: Torch on!");
                mVibrator.vibrate(500);
                unitRest(500);
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], false);
                Log.d(TAG, "shortLight: Torch off!");
                unitRest(500);
            } else {
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], true);
                Log.d(TAG, "shortLight: Torch on!");
                unitRest(500);
                mCameraManager.setTorchMode(mCameraManager.getCameraIdList()[0], false);
                Log.d(TAG, "shortLight: Torch off!");
                unitRest(500);
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void unitRest(long rest) throws InterruptedException {
        try {
            TimeUnit.MILLISECONDS.sleep(rest);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
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
            case R.id.toggleMenu:
                startActivity(new Intent(getApplicationContext(), Toggle.class));
            case R.id.flashMenu:
                startActivity(new Intent(getApplicationContext(), Flash.class));

        }
        return true;
    }
}


