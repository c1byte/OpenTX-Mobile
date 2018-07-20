package com.choi_n_yang.opentx_mobile.opentx_mobile;

import android.app.Application;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.driver.UsbSerialDriver;
import com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.driver.UsbSerialPort;
import com.choi_n_yang.opentx_mobile.opentx_mobile.usbserial.driver.UsbSerialProber;

import java.util.ArrayList;
import java.util.List;

public class OpenTxMobileApplication extends Application {

    private UsbManager m_UsbManager;
    private UsbSerialPort m_port;

    @Override
    public void onCreate() {
        super.onCreate();
        m_UsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public UsbManager getUsbManager() { return m_UsbManager;}
    public void openUsbSerialPort() {
        final List<UsbSerialDriver> drivers =
                UsbSerialProber.getDefaultProber().findAllDrivers(m_UsbManager);
        final List<UsbSerialPort> UsbSerialPorts = new ArrayList<UsbSerialPort>();
        for (final UsbSerialDriver driver : drivers) {
            final List<UsbSerialPort> ports = driver.getPorts();
            UsbSerialPorts.addAll(ports);
        }
        m_port = UsbSerialPorts.get(0);

    }
    public void closeUsbSerialPort() {
//        m_port.close();
    }
    public UsbSerialPort getUsbSerialPort() {
        return m_port;
    }


}
