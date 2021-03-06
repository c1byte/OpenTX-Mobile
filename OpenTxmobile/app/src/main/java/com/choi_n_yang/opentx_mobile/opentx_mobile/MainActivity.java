package com.choi_n_yang.opentx_mobile.opentx_mobile;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private UsbService usbService;
    private MyHandler mHandler;

    /*
     * Notifications from UsbService will be received here.
     */
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                    Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();
            usbService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };


    private OpenTxMobileApplication m_App;

    private TextView m_statusTextView;
    private TextView display;
    private ImageView m_MainScreen;

    /*
     * This handler will be passed to UsbService. Data received from serial port is displayed through this handler
     */
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT:
                    String data = (String) msg.obj;
                    mActivity.get().m_App.updateReceivedData(data.getBytes());
                    mActivity.get().display.append("SerialPort>>>>>");
                    mActivity.get().display.append(data);
                    mActivity.get().display.append("<<<<<<<<<<SerialPort");
                    mActivity.get().updateChannel();
                    break;
                case UsbService.CTS_CHANGE:
                    Toast.makeText(mActivity.get(), "CTS_CHANGE",Toast.LENGTH_LONG).show();
                    break;
                case UsbService.DSR_CHANGE:
                    Toast.makeText(mActivity.get(), "DSR_CHANGE",Toast.LENGTH_LONG).show();
                    break;
                case UsbService.SYNC_READ:
                    String buffer = (String) msg.obj;
                    mActivity.get().m_App.updateReceivedData(buffer.getBytes());
                    mActivity.get().display.append("SYNC_READ>>>>>");
                    mActivity.get().display.append(buffer);
                    mActivity.get().display.append("<<<<<<<<<<SYNC_READ");
                    mActivity.get().updateChannel();
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_App = (OpenTxMobileApplication) getApplicationContext();
        setContentView(R.layout.activity_main);
        m_statusTextView = (TextView) findViewById(R.id.txtStatusView);

        mHandler = new MyHandler(this);
        display = (TextView) findViewById(R.id.textView);
        display.setMovementMethod(new ScrollingMovementMethod());

        m_MainScreen = (ImageView)findViewById(R.id.imageView);

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mUsbReceiver);
        unbindService(usbConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setFilters();  // Start listening notifications from UsbService
        startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it

//        Bitmap src =  Bitmap.createBitmap(m_App.getMainScreen(), 128, 64, Bitmap.Config.ARGB_8888);

//        m_MainScreen.setImageBitmap(src);

    }

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(this, service);
        bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);
    }

    public void  onClick(View view) {

        this.updateConnectionStatus();

        String test;
        test = "";
        test += "outputs[0] = 1234 \n";
        test += "outputs[1] = 1234 \n";
        test += "outputs[2] = 1234 \n";
        test += "outputs[3] = 1234 \n";
        test += "outputs[4] = 1234 \n";
        test += "outputs[5] = 1234 \n";
        test += "outputs[6] = 1234 \n";
        test += "outputs[7] = 1234 \n";
        test += "outputs[8] = 1234 \n";
        test += "outputs[9] = 1234 \n";
        test += "outputs[10] = 1234 \n";

        m_App.updateReceivedData(test.getBytes());

    }


    public void  onClickSend(View view) throws UnsupportedEncodingException {
        //byte[] CRLP = new byte[1];
        //CRLP[0] = 0x0D;
        //CRLP[1] = 0x0A;
//        String data = "p outputs\n";
        String data = "p displaybuf\n";
        Bitmap src =  Bitmap.createBitmap(m_App.getMainScreen(), 128, 64, Bitmap.Config.ARGB_8888);
        m_MainScreen.setImageBitmap(src);

//        String data = "display0 = 0f0f0f0f0f00f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0\n";
//        m_App.linePaser(data);
//        data = "display1 = 0f0f0f0f0f070f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f\n";
//        m_App.linePaser(data);
//        data = "display2 = 0f0f0f0f0f0f000f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f\n";
//        m_App.linePaser(data);
//        data = "display3 = 0f0f0f0f0f0f0f000f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f\n";
//        m_App.linePaser(data);
//       data = "display4 = 0f0f0f0f0f0f0f0f000f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f\n";
//        m_App.linePaser(data);
//        data = "display5 = 0f0f0f0f0f0f0f0f0f000f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f\n";
//        m_App.linePaser(data);
//        data = "display6 = 0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f\n";
//        m_App.linePaser(data);
//        data = "display7 = 0f0f0f020202020202020202020202020202020202020f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f\n";
//        m_App.linePaser(data);


        usbService.write(data.getBytes());

        //updateChannel();
    }
    public void updateScreen(){
        String data = "p displaybuf\n";
        Bitmap src =  Bitmap.createBitmap(m_App.getMainScreen(), 128, 64, Bitmap.Config.ARGB_8888);
        m_MainScreen.setImageBitmap(src);
        usbService.write(data.getBytes());
    }
    public void onClick_Up(View view){
        String data = "keyinput up\n";
        usbService.write(data.getBytes());
        updateScreen();
    }
    public void onClick_Down(View view){
        String data = "keyinput dwon\n";
        usbService.write(data.getBytes());
        updateScreen();
    }
    public void onClick_Exit(View view){
        String data = "keyinput exit\n";
        usbService.write(data.getBytes());
        updateScreen();
    }


    public void onClick_Enter(View view){
        String data = "keyinput enter\n";
        usbService.write(data.getBytes());
        updateScreen();
    }

    public void onClick_EnterLong(View view){
        String data = "keyinput enterlong\n";
        usbService.write(data.getBytes());
        updateScreen();
    }
    public void onClick_Menu(View view){
        String data = "keyinput menu\n";
        usbService.write(data.getBytes());
        updateScreen();
    }
    public void onClick_MenuLong(View view){
        String data = "keyinput menulong\n";
        usbService.write(data.getBytes());
        updateScreen();
    }
    public void onClick_Page(View view){
        String data = "keyinput page\n";
        usbService.write(data.getBytes());
        updateScreen();
    }
    public void onClick_PageLong(View view){
        String data = "keyinput pagelong\n";
        usbService.write(data.getBytes());
        updateScreen();
    }
    public void updateConnectionStatus(){
        String data = "keyinput up\n";
        usbService.write(data.getBytes());
        updateScreen();
    }

    public void updateChannel(){
        TextView ch1 = (TextView) findViewById(R.id.txtCh1);
        TextView ch2 = (TextView) findViewById(R.id.txtCh2);
        TextView ch3 = (TextView) findViewById(R.id.txtCh3);
        TextView ch4 = (TextView) findViewById(R.id.txtCh4);

        ch1.setText("Ch1:" + m_App.getChannel(0));
        ch2.setText("Ch2:" + m_App.getChannel(1));
        ch3.setText("Ch3:" + m_App.getChannel(2));
        ch4.setText("Ch4:" + m_App.getChannel(3));

        Bitmap src =  Bitmap.createBitmap(m_App.getMainScreen(), 128, 64, Bitmap.Config.ARGB_8888);
        m_MainScreen.setImageBitmap(src);

    }

}
