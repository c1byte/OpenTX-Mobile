package com.choi_n_yang.opentx_mobile.opentx_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.util.HexDump;

import java.lang.String;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.util.HexDump.hexStringToByteArray;

public class OpenTxMobileApplication extends Application {

    private final int MAX_CHANNEL = 10;
    private final int m_BitmapColor = BLACK;
    private int[] m_Channel;
    private int[] m_MainScreen;
    private String m_MessageTemp;


//    Bitmap mainBitmap;



    public void updateReceivedData(byte[] data) {
        String message = m_MessageTemp + HexDump.byteArrayToBinaryString(data);
        int MsgCnt;

        m_MessageTemp = "";

        String[] aMessage = message.split("\n");
        if( message.lastIndexOf("\n") < message.length() )
        {
            m_MessageTemp = aMessage[aMessage.length-1];
            MsgCnt = aMessage.length-1;
        }else
        {
            MsgCnt = aMessage.length;
        }

        for(int i=0;i<MsgCnt;i++)
        {
            aMessage[i].trim();
            this.linePaser(aMessage[i]);
        }
    }

    public int[] getMainScreen(){
        return m_MainScreen;
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

        }else if(data.contains("display0"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display0 = temp.trim();
            if( display0.length()> 256 ) {
                display0 = display0.substring(0, 256);
            }else if ( (display0.length() % 2) > 0)
            {
                display0 = display0.substring(0,  display0.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display0);

            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 0;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }else if(data.contains("display1"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display1 = temp.trim();
            if( display1.length()> 256 ) {
                display1 = display1.substring(0, 256);
            }else if ( (display1.length() % 2) > 0)
            {
                display1 = display1.substring(0,  display1.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display1);
            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 1;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }else if(data.contains("display2"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display2 = temp.trim();
            if( display2.length()> 256 ) {
                display2 = display2.substring(0, 256);
            }else if ( (display2.length() % 2) > 0)
            {
                display2 = display2.substring(0,  display2.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display2);
            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 2;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }else if(data.contains("display3"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display3 = temp.trim();
            if( display3.length()> 256 ) {
                display3 = display3.substring(0, 256);
            }else if ( (display3.length() % 2) > 0)
            {
                display3 = display3.substring(0,  display3.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display3);
            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 3;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }else if(data.contains("display4"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display4 = temp.trim();
            if( display4.length()> 256 ) {
                display4 = display4.substring(0, 256);
            }else if ( (display4.length() % 2) > 0)
            {
                display4 = display4.substring(0,  display4.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display4);
            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 4;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }else if(data.contains("display5"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display5 = temp.trim();
            if( display5.length()> 256 ) {
                display5 = display5.substring(0, 256);
            }else if ( (display5.length() % 2) > 0)
            {
                display5 = display5.substring(0,  display5.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display5);
            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 5;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }else if(data.contains("display6"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display6 = temp.trim();
            if( display6.length()> 256 ) {
                display6 = display6.substring(0, 256);
            }else if ( (display6.length() % 2) > 0)
            {
                display6 = display6.substring(0,  display6.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display6);
            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 6;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }else if(data.contains("display7"))
        {
            String temp = data.substring(data.indexOf("=")+1);
            String display7 = temp.trim();
            if( display7.length()> 256 ) {
                display7 = display7.substring(0, 256);
            }else if ( (display7.length() % 2) > 0)
            {
                display7 = display7.substring(0,  display7.length()-1);
            }
            byte[] ByteArray = hexStringToByteArray(display7);
            int maxLength;
            if (ByteArray.length > 128)
            {
                maxLength = 128;
            }else
            {
                maxLength = ByteArray.length;
            }
            for(int i=0 ;i<maxLength;i++)
            {
                final int xline = 7;
                int bitColor = ByteArray[i] & 0xFF;
                updateScreen( bitColor, i, xline);
            }
        }
    }

    public void updateScreen( int bitColor, int i, int xline)
    {
        if( (i+(128*7)+(128*8)*xline) > 128*64 )
        {
            return;
        }
        if( (bitColor & 0x01) > 0 ) {
            m_MainScreen[i+(128*0)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*0)+(128*8)*xline] = WHITE;
        }
        if( (bitColor & 0x02) > 0 ) {
            m_MainScreen[i+(128*1)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*1)+(128*8)*xline] = WHITE;

        }
        if( (bitColor & 0x04) > 0 ) {
            m_MainScreen[i+(128*2)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*2)+(128*8)*xline] = WHITE;

        }
        if( (bitColor & 0x08) > 0 ) {
            m_MainScreen[i+(128*3)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*3)+(128*8)*xline] = WHITE;

        }
        if( (bitColor & 0x10) > 0 ) {
            m_MainScreen[i+(128*4)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*4)+(128*8)*xline] = WHITE;

        }
        if( (bitColor & 0x20) > 0 ) {
            m_MainScreen[i+(128*5)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*5)+(128*8)*xline] = WHITE;

        }
        if( (bitColor & 0x40) > 0 ) {
            m_MainScreen[i+(128*6)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*6)+(128*8)*xline] = WHITE;

        }
        if( (bitColor & 0x80) > 0 ) {
            m_MainScreen[i+(128*7)+(128*8)*xline] = m_BitmapColor;
        }else
        {
            m_MainScreen[i+(128*7)+(128*8)*xline] = WHITE;

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        m_Channel = new int[MAX_CHANNEL];
      //  mainBitmap.createBitmap(128,64);
        m_MainScreen = new int[128*64];


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
