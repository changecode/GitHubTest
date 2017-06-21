package com.java.proxyhttp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DataSendThread extends Thread {

	private InputStream isOut;  
    private OutputStream osIn;  
    private long totalDownload=0l;
    private Socket socketIn;  
    private Socket socketOut;  

    DataSendThread(InputStream isOut, OutputStream osIn) {  
        this.isOut = isOut;  
        this.osIn = osIn;  
    }  

    @Override  
    public void run() {  
        byte[] buffer = new byte[4096];  
        try {  
            int len;  
            while ((len = isOut.read(buffer)) != -1) {  
                if (len > 0) {  
                    // logData(buffer, 0, len);  
                    osIn.write(buffer, 0, len);  
                    osIn.flush();  
                    totalDownload+=len;  
                }  
                if (socketIn.isOutputShutdown() || socketOut.isClosed()) {  
                    break;  
                }  
            }  
        } catch (Exception e) {}  
    }  
}  
