package com.choi_n_yang.opentx_mobile.opentx_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.widget.Toast;

import com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.util.HexDump;

import java.lang.String;

public class OpenTxMobileApplication extends Application {

    private final int MAX_CHANNEL = 10;
    private int[] m_Channel;


    public void updateReceivedData(byte[] data) {
        final String message = HexDump.byteArrayToBinaryString(data);
        String[] aMessage = message.split("\n");
        for(int i=0;i<aMessage.length;i++)
        {
            aMessage[i].trim();
            this.linePaser(aMessage[i]);
        }
    }
    public void linePaser(String data){
        if(data.contains("outputs[0]"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            m_Channel[0] = ATOI(temp.trim());

        }else if(data.contains("outputs[1]"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            m_Channel[1] = ATOI(temp.trim());

        }else if(data.contains("outputs[2]"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            m_Channel[2] = ATOI(temp.trim());

        }else if(data.contains("outputs[3]"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            m_Channel[3] = ATOI(temp.trim());

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        m_Channel = new int[MAX_CHANNEL];

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public void setChannel( int[] chnl )
    {
        for(int i=0; i< MAX_CHANNEL;i++)
        {
            m_Channel[i] = chnl[i];
        }
    }
    public void setChannel( int Chnl, int idx)
    {
        if( idx < MAX_CHANNEL) {
            m_Channel[idx] = Chnl;
        }
    }

    public int getChannel( int idx ){
        return m_Channel[idx];
    }

    public void debug(Object obj, String title, String msg){
        final Activity activity = (Activity)obj;

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(activity);
        alertdialog.setMessage(msg);
        alertdialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(activity, "confirmed", Toast.LENGTH_SHORT);
            }
        });
        AlertDialog alert = alertdialog.create();
        alert.setTitle(title);
        alert.show();

    }

    public static int ATOI(String sTmp)
    {
        String tTmp = "0", cTmp = "";

        sTmp = sTmp.trim();
        for(int i=0;i < sTmp.length();i++)
        {
            cTmp = sTmp.substring(i,i+1);
            if(cTmp.equals("0") ||
                    cTmp.equals("1") ||
                    cTmp.equals("2") ||
                    cTmp.equals("3") ||
                    cTmp.equals("4") ||
                    cTmp.equals("5") ||
                    cTmp.equals("6") ||
                    cTmp.equals("7") ||
                    cTmp.equals("8") ||
                    cTmp.equals("9")) tTmp += cTmp;
            else if(cTmp.equals("-") && i==0)
                tTmp = "-";
            else
                break;
        }

        return(Integer.parseInt(tTmp));
    }


}
