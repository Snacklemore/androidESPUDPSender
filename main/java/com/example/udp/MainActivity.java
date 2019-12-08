package com.example.udp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.concurrent.Future;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    public EditText TxtEditBtn1 ;
    public EditText TxtEditBtn2;
    public EditText TxtEditBtn3;
    public EditText TxtEditBtn4;
    public EditText TxtEditBtn5 ;
    public EditText TxtEditBtn6;
    public EditText TxtEditIP ;
    public EditText TxtEditPort;
    public EditText TxtLocalPortEdit;
    public TextView txtV;

    public EditText TxtEditMessageBox;
    public Button Cancel;
    public Button Button1;
    public Button Button2;
    public Button Button3;
    public Button Button4;
    public Button ButtonSetButton;
    public Button ButtonSendMessage;
    public Button ButtonListen;
    Listen threadRefListen;
    Context mContext;
    Context ActiContext;
    private boolean isListening= false;

    SharedPreferences sharedPreferences;
    public void Save()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String db = TxtEditIP.getText().toString();
        editor.putString("LocalPort",TxtLocalPortEdit.getText().toString());
        editor.putString("IP",TxtEditIP.getText().toString());
        editor.putString("Port",TxtEditPort.getText().toString());
        editor.putString("Btn1",Button1.getText().toString());
        editor.putString("Btn2",Button2.getText().toString());
        editor.putString("Btn3",Button3.getText().toString());
        editor.putString("Btn4",Button4.getText().toString());
        editor.commit();

    }
    private void runSendingTask(String name,String message,String IP,String Port)
    {
        SendMessageUDP task = new SendMessageUDP(message,IP,Port);
        TManager.getManagerInstance().runTask(task);
    }
    private void runListeningTask(String ip, String port, Context mContext, TextView txtV)
    {
        Listen task = new Listen(ip,port,mContext,txtV);
        //threadRefListen = task;
        TManager.getManagerInstance().submitTask(task);
        isListening = true;

    }
    private void stopListeningTask()
    {
        if (isListening)
        TManager.getManagerInstance().Cancel();
        else
            return;

    }

    private boolean validateEditTxtBoxes(){
        String LocalPort = TxtLocalPortEdit.getText().toString();
        String IP = TxtEditIP.getText().toString();
        String Port = TxtEditPort.getText().toString();
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?(\\.\\d+)?(\\.\\d+)?");



        boolean isNumericLocalPort = pattern.matcher(LocalPort).matches();
        boolean isNumericIP = pattern.matcher(IP).matches();
        boolean isNumericPort = pattern.matcher(Port).matches();
        if(isNumericIP && isNumericLocalPort && isNumericPort)
        {
            return true;
        }

        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String IP_Key = "IP";
        String PORT_KEY = "Port";
        sharedPreferences =getSharedPreferences("settings",Context.MODE_PRIVATE);


        ActiContext = this;
        mContext = getApplicationContext();
        Cancel = (Button) findViewById(R.id.buttonCancel);
        TxtLocalPortEdit = (EditText) findViewById(R.id.TxtEditLocalPort);
        TxtEditBtn1 = (EditText) findViewById(R.id.editTextBtn1);
        TxtEditBtn2 = (EditText) findViewById(R.id.editTextBtn2);

        TxtEditBtn3 = (EditText) findViewById(R.id.editTextBtn3);

        TxtEditBtn4 = (EditText) findViewById(R.id.editTextBtn4);
        txtV = (TextView) findViewById(R.id.textViewRec);
        TxtEditBtn1 = (EditText) findViewById(R.id.editTextBtn1);
        TxtEditIP = (EditText) findViewById(R.id.editTextIP);

        TxtEditPort= (EditText) findViewById(R.id.editTextPort);
        TxtEditMessageBox = (EditText) findViewById(R.id.editText7);
        Button1 = (Button) findViewById(R.id.button1);
        Button2 = (Button) findViewById(R.id.button2);
        Button3 = (Button) findViewById(R.id.button3);
        Button4 = (Button) findViewById(R.id.button4);
        ButtonSetButton = (Button) findViewById(R.id.SetButtons);
        ButtonSendMessage = (Button) findViewById(R.id.SendButton);
        ButtonListen = (Button) findViewById(R.id.ButtonListen);
        if (sharedPreferences.contains(IP_Key))
        {
            TxtEditIP.setText(sharedPreferences.getString(IP_Key,""));
        }
        if (sharedPreferences.contains(PORT_KEY))
        {
            TxtEditPort.setText(sharedPreferences.getString(PORT_KEY,""));
        }
        if (sharedPreferences.contains("Btn1"))
        {
            Button1.setText(sharedPreferences.getString("Btn1",""));
        }
        if (sharedPreferences.contains("Btn2"))
        {
            Button2.setText(sharedPreferences.getString("Btn2",""));
        }
        if (sharedPreferences.contains("Btn3"))
        {
            Button3.setText(sharedPreferences.getString("Btn3",""));
        }
        if (sharedPreferences.contains("Btn4"))
        {
            Button4.setText(sharedPreferences.getString("Btn4",""));
        }
        if(sharedPreferences.contains("LocalPort"))
        {
            TxtLocalPortEdit.setText(sharedPreferences.getString("LocalPort",""));
        }




        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopListeningTask();
                ButtonListen.setEnabled(true);
            }
        });
        ButtonSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button1.setText(TxtEditBtn1.getText());
                Button2.setText(TxtEditBtn2.getText());
                Button3.setText(TxtEditBtn3.getText());
                Button4.setText(TxtEditBtn4.getText());

            }
        });

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTxtBoxes()){
                    Toast.makeText(getApplicationContext(),Button1.getText(),Toast.LENGTH_LONG).show();
                    runSendingTask("Send",Button1.getText().toString(),TxtEditIP.getText().toString(),TxtEditPort.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(),("No valid IP/Port"),Toast.LENGTH_LONG).show();

                };



            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTxtBoxes())
                {
                    Toast.makeText(getApplicationContext(),Button2.getText(),Toast.LENGTH_LONG).show();
                    runSendingTask("Send",Button2.getText().toString(),TxtEditIP.getText().toString(),TxtEditPort.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),("No valid IP/Port"),Toast.LENGTH_LONG).show();

                }


            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTxtBoxes())
                {
                    Toast.makeText(getApplicationContext(),Button3.getText(),Toast.LENGTH_LONG).show();
                    runSendingTask("Send",Button3.getText().toString(),TxtEditIP.getText().toString(),TxtEditPort.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),("No valid IP/Port"),Toast.LENGTH_LONG).show();

                }


            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTxtBoxes())
                {
                    Toast.makeText(getApplicationContext(),Button4.getText(),Toast.LENGTH_LONG).show();
                    runSendingTask("Send",Button4.getText().toString(),TxtEditIP.getText().toString(),TxtEditPort.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),("No valid IP/Port"),Toast.LENGTH_LONG).show();

                }


            }
        });
        ButtonListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTxtBoxes())
                {   runListeningTask(TxtEditIP.getText().toString(),TxtLocalPortEdit.getText().toString(),mContext,txtV);
                    ButtonListen.setEnabled(false);
                    Toast.makeText(mContext,"Listening",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),("No valid IP/Port"),Toast.LENGTH_LONG).show();

                }



            }
        });
        ButtonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTxtBoxes())
                {
                    Toast.makeText(mContext,"Sending",Toast.LENGTH_LONG).show();
                    runSendingTask("Send",TxtEditMessageBox.getText().toString(),TxtEditIP.getText().toString(),TxtEditPort.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),("No valid IP/Port"),Toast.LENGTH_LONG).show();

                }




            }
        });



    }
    @Override
    public void onStop()
    {
        super.onStop();
        Save();
    }
}

