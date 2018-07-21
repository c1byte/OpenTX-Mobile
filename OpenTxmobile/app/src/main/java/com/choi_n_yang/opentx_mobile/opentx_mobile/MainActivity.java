package com.choi_n_yang.opentx_mobile.opentx_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.driver.UsbSerialPort;

public class MainActivity extends AppCompatActivity {

    private OpenTxMobileApplication m_App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_App = (OpenTxMobileApplication) getApplicationContext();

        setContentView(R.layout.activity_main);

    }

    private UsbSerialPort port;
    public void  onClick(View view) {

        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivity(intent);

        port = m_App.getUsbSerialPort();
        SerialConsoleActivity.show(this, port);

    }



    public void onConnect(View view) {
        TextView statusTextView;
        statusTextView = (TextView) findViewById(R.id.txtStatusView);

        m_App.openUsbSerialPort();

        if (m_App.getUsbSerialPort() == null) {
            statusTextView.setText("Serial device is not connected");
        } else {
            statusTextView.setText("Serial device: " + m_App.getUsbSerialPort().getClass().getSimpleName());
        }
    }
    public void onDisConnect(View view){

        m_App.closeUsbSerialPort();
    }
}
