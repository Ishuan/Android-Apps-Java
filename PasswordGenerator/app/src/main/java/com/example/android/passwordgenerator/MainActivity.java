package com.example.android.passwordgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    ExecutorService threadPool;
    ProgressDialog pb;
    Dialog dialog;
    CharSequence[] passCharSequence;

    TextView passGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar countSeek = findViewById(R.id.countSeekBar);
        SeekBar lengthSeek = findViewById(R.id.lengthSeekBar);
        final TextView passCount = findViewById(R.id.passwordCountText);
        final TextView passLength = findViewById(R.id.passwordLengthText);
        Button asyncBtn = findViewById(R.id.taskBtn);
        Button threadBtn = findViewById(R.id.threadBtn);
        passGen = findViewById(R.id.genPass);

        pb = new ProgressDialog(this);
        pb.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pb.setCancelable(false);
        pb.setIndeterminate(false);
        pb.setTitle(getResources().getString(R.string.prgrsDlgText));
        pb.setMax(Integer.parseInt(passCount.getText().toString()));

        countSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int minVal = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passCount.setText(String.valueOf(minVal + progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                passCount.setText(String.valueOf(minVal));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lengthSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int minVal = 8;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passLength.setText(String.valueOf(minVal + progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                passLength.setText(String.valueOf(minVal));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        threadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadPool = Executors.newFixedThreadPool(2);
                threadPool.execute(new threadPass(passCount.getText().toString(),
                        passLength.getText().toString()));
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case threadPass.STOP:
                        pb.dismiss();
                        passCharSequence = msg.getData().getStringArrayList(threadPass.LIST_KEY)
                                .toArray(new CharSequence[msg.getData()
                                        .getStringArrayList(threadPass.LIST_KEY).size()]);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder
                                (MainActivity.this);
                        alertDialogBuilder.setTitle("Passwords")
                                .setItems(passCharSequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                passGen.setText(passCharSequence[which]);
                            }
                        });
                        alertDialogBuilder.setCancelable(false).show();
                        break;

                    case threadPass.START:
                        pb.setProgress(0);
                        pb.setMax(Integer.parseInt(passCount.getText().toString()));
                        pb.show();
                        break;

                    case threadPass.PROGRESS:
                        pb.incrementProgressBy(1);
                        break;
                }
                return false;
            }
        });

        asyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new asyncPass(Integer.parseInt(passCount.getText().toString()))
                        .execute(passLength.getText().toString(), passCount.getText().toString());
            }
        });
    }

    class threadPass implements Runnable {

        static final int START = 0x00;
        static final int STOP = 0x01;
        static final int PROGRESS = 0x02;
        static final String LIST_KEY = "passList";

        String numberOfPass;
        String lengthOfPass;
        ArrayList<String> passList = new ArrayList<>();
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();

        threadPass(String numnerOfPass, String lengthOfPass) {
            this.numberOfPass = numnerOfPass;
            this.lengthOfPass = lengthOfPass;
        }

        @Override
        public void run() {

            Message startMsg = new Message();
            startMsg.what = START;
            handler.sendMessage(startMsg);

            for (int i = 0; i < Integer.parseInt(numberOfPass); i++) {
                passList.add(Util.getPassword(Integer.parseInt(lengthOfPass)));
                Message progressMsg = new Message();
                progressMsg.what = PROGRESS;
                bundle.putInt("listSize", Integer.parseInt(numberOfPass));
                progressMsg.setData(bundle);
                handler.sendMessage(progressMsg);
            }


            Message stopMsg = new Message();
            bundle2.putStringArrayList(LIST_KEY, passList);
            stopMsg.what = STOP;
            stopMsg.setData(bundle2);
            handler.sendMessage(stopMsg);
        }
    }

    class asyncPass extends AsyncTask<String, Integer, ArrayList<String>> {

        int numberOfPass;

        ProgressDialog progressD = new ProgressDialog(MainActivity.this);

        public asyncPass(int numberOfPass) {
            this.numberOfPass = numberOfPass;
        }

        @Override
        protected void onPreExecute() {

            progressD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressD.setMessage(getResources().getString(R.string.prgrsDlgText));
            progressD.setCancelable(false);
            progressD.setMax(numberOfPass);
            progressD.setIndeterminate(false);
            progressD.show();
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            progressD.dismiss();
            final CharSequence[] charSequences = s.toArray(new CharSequence[s.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Passwords").setItems(charSequences,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    passGen.setText(charSequences[which]);
                }
            });
            builder.setCancelable(false).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressD.incrementProgressBy(1);
        }

        @Override
        protected ArrayList<String> doInBackground(String... str) {
            ArrayList<String> passList = new ArrayList<>();
            int numberOfPass = Integer.parseInt(str[1]);
            int lengthOfPass = Integer.parseInt(str[0]);
            for (int i = 0; i < numberOfPass; i++) {
                passList.add(Util.getPassword(lengthOfPass));
                publishProgress(i);
            }
            return passList;
        }
    }
}