package com.example.udp;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SendMessageUDP implements Runnable{
    public String Messsage;
    public String Port;
    public String IP;
    private volatile boolean shutdown = true;
    public SendMessageUDP(String message,String ip,String port){
        Messsage = message;
        IP = ip;
        Port = port;


    }
    public void setShutdown(){
        shutdown = false;
    }
    @Override
    public void run()
    {

        while(shutdown)
        {
            try {
                DatagramSocket udpSocket = new DatagramSocket(Integer.parseInt(Port,10));
                InetAddress serverAddr = InetAddress.getByName(IP);
                byte[] buf = Messsage.getBytes();
                DatagramPacket packet = new DatagramPacket(buf,buf.length,serverAddr,Integer.parseInt(Port,10));
                udpSocket.send(packet);
                udpSocket.close();
                setShutdown();
            }catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }



    }
}
