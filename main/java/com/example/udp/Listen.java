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
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class Listen  implements Runnable{
    private boolean shutdown = true;
    String IP;
    int Port;
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
    }

    @Override
    public void run()
    {



        try {
            Socket = new DatagramSocket(Port);
            DatagramPacket packet;







        while (shutdown)
        {


                byte[] buf = new byte[256];

                packet = new DatagramPacket(buf,buf.length);
                Socket.receive(packet);
                String text = new String(buf,0,packet.getLength());
                txtV.setText("DEVICEIP:"+IP+text);





        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
