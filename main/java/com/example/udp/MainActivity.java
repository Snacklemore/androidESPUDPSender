package com.example.udp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText TxtEditBtn1 ;
    public EditText TxtEditBtn2;
    public EditText TxtEditBtn3;
    public EditText TxtEditBtn4;
    public EditText TxtEditBtn5 ;
    public EditText TxtEditBtn6;
    public EditText TxtEditIP ;
    public EditText TxtEditPort;
    public TextView txtV;

    public EditText TxtEditMessageBox;
    public Button Button1;
    public Button Button2;
    public Button Button3;
    public Button Button4;
    public Button ButtonSetButton;
    public Button ButtonSendMessage;
    public Button ButtonListen;
    Listen threadRefListen;
    Context mContext;

    private void runSendingTask(String name,String message,String IP,String Port)
    {
        SendMessageUDP task = new SendMessageUDP(message,IP,Port);
        TManager.getManagerInstance().runTask(task);
    }
    private void runListeningTask(String ip, String port, Context mContext, TextView txtV)
    {
        Listen task = new Listen(ip,port,mContext,txtV);
        //threadRefListen = task;
        TManager.getManagerInstance().runTask(task);
    }
    private void stopListeningTask()
    {
        TManager.getManagerInstance().WorkQueue.remove(threadRefListen);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
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


        ButtonSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button1.setText(TxtEditBtn1.getText());
            }
        });
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),Button1.getText(),Toast.LENGTH_LONG).show();
                runSendingTask("Send",Button1.getText().toString(),TxtEditIP.getText().toString(),TxtEditPort.getText().toString());

            }
        });
        ButtonListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Listening",Toast.LENGTH_LONG).show();
                runListeningTask(TxtEditIP.getText().toString(),TxtEditPort.getText().toString(),mContext,txtV);

            }
        });

    }
}
