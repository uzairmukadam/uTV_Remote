package com.uzitech.utvremote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RemoteActivity extends AppCompatActivity {

    ImageButton power, mute, d_up, d_down, d_left, d_right, d_btn, home, back, menu, prev, play, next;

    static String ip_address;
    static int port_number;

    Vibrator vibrator;

    static ImageView connection_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        ip_address = getIntent().getStringExtra("IP");
        port_number = Integer.parseInt(getIntent().getStringExtra("PORT"));


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        power = findViewById(R.id.btn_power);
        mute = findViewById(R.id.btn_mute);
        d_up = findViewById(R.id.d_up);
        d_down = findViewById(R.id.d_down);
        d_left = findViewById(R.id.d_left);
        d_right = findViewById(R.id.d_right);
        d_btn = findViewById(R.id.d_enter);
        home = findViewById(R.id.btn_home);
        back = findViewById(R.id.btn_back);
        menu = findViewById(R.id.btn_menu);
        prev = findViewById(R.id.btn_prev);
        play = findViewById(R.id.btn_play);
        next = findViewById(R.id.btn_next);

        connection_status = findViewById(R.id.connection_status);

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_PWR");
            }
        });

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_MUTE");
            }
        });

        d_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("D_UP");
            }
        });

        d_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("D_DOWN");
            }
        });

        d_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("D_LEFT");
            }
        });

        d_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("D_RIGHT");
            }
        });

        d_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("D_ENTER");
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_HOME");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_BACK");
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_MENU");
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_PREV");
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_PLAY");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("BTN_NEXT");
            }
        });
    }

    public void resetIP(View view) {
        SharedPreferences.Editor preferences = getSharedPreferences("Network Data", MODE_PRIVATE).edit();
        preferences.clear();
        preferences.apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    void buttonClick(String input) {
        vibrator.vibrate(24);
        new SendInput(input).execute();
    }

    public static class SendInput extends AsyncTask<Void, Void, Void> {
        String response = "";
        String btn_input;

        SendInput(String msg) {
            btn_input = msg;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket(ip_address, port_number);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                if (btn_input != null) {
                    dataOutputStream.writeUTF(btn_input);
                }

                response = dataInputStream.readUTF();

            } catch (UnknownHostException e) {
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
}