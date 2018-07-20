package com.choi_n_yang.opentx_mobile.opentx_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.driver.UsbSerialPort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int ch1 = OpenTxGlobal.getInstance().getChannel(0);
//        int ch2 = OpenTxGlobal.getInstance().getChannel(1);
//        int ch3 = OpenTxGlobal.getInstance().getChannel(2);
//        int ch4 = OpenTxGlobal.getInstance().getChannel(3);
//        ImageView stickA = (ImageView) findViewById(R.id.StickAView);
//        ImageView stickB = (ImageView) findViewById(R.id.StickBView);
//        stickA.setY(ch1);
//        stickA.setX(ch2);
//        stickB.setY(ch3);
//        stickB.setX(ch4);

        setContentView(R.layout.activity_main);

    }

    private OpenTxMobileApplication applicationClass;
    private UsbSerialPort port;
    public void  onClick(View view) {

        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivity(intent);
        applicationClass = (OpenTxMobileApplication) getApplicationContext();

        port = applicationClass.getUsbSerialPort();
        SerialConsoleActivity.show(this, port);

    }



    public void onConnect(View view){

        applicationClass = (OpenTxMobileApplication) getApplicationContext();
        applicationClass.openUsbSerialPort();
}

    public void onDisConnect(View view){
        applicationClass = (OpenTxMobileApplication) getApplicationContext();
        applicationClass.closeUsbSerialPort();
    }
}
