package com.example.udp;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static android.content.Context.WIFI_SERVICE;

public class Listen  implements Runnable{
    private boolean shutdown = true;
    String IP;
    int Port;
    int messageCounter = 0;
    private TextView txtV;
    private Context mContext;
    DatagramSocket Socket;
    public Listen(String ip, String port, Context mContext, TextView txtV)
    {


        Port = Integer.parseInt(port);
        IP = wifiIpAddress(mContext);
        this.mContext = mContext;
        this.txtV = txtV;
    }
    protected String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }


    public void setShutdown()
    {
        shutdown = false;
        txtV.setText("Canceled Listening");
    }

    @Override
    public void run()
    {



        try {
            Socket = new DatagramSocket(Port);
            Socket.setSoTimeout(2000);
            DatagramPacket packet;
            txtV.setText("Started Listening..");







        while (shutdown)
        {

            try{
                byte[] buf = new byte[256];

                packet = new DatagramPacket(buf,buf.length);
                Socket.receive(packet);
                messageCounter++;
                String text = new String(buf,0,packet.getLength());
                txtV.setText("No:"+messageCounter+"IP:>"+IP+"*: "+text);

            }catch (SocketTimeoutException te)
            {
                if ( Thread.currentThread().isInterrupted())
                {
                    Socket.close();
                    setShutdown();
                }
                continue;
            }
        }





        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
