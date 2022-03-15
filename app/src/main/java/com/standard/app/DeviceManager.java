package com.standard.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.camera.AidlCameraScanCode;
import com.topwise.cloudpos.aidl.card.AidlCheckCard;
import com.topwise.cloudpos.aidl.emv.level2.AidlAmex;
import com.topwise.cloudpos.aidl.emv.level2.AidlEmvL2;
import com.topwise.cloudpos.aidl.emv.level2.AidlEntry;
import com.topwise.cloudpos.aidl.emv.level2.AidlPaypass;
import com.topwise.cloudpos.aidl.emv.level2.AidlPaywave;
import com.topwise.cloudpos.aidl.emv.level2.AidlPure;
import com.topwise.cloudpos.aidl.emv.level2.AidlQpboc;
import com.topwise.cloudpos.aidl.iccard.AidlICCard;
import com.topwise.cloudpos.aidl.led.AidlLed;
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.rfcard.AidlRFCard;
import com.topwise.cloudpos.aidl.shellmonitor.AidlShellMonitor;
import com.topwise.cloudpos.aidl.system.AidlSystem;
import com.topwise.sdk.emv.EmvDeviceManager;

/**
 * @author xukun
 * @version 1.0.0
 * @date 18-6-8
 *
 * All Device mode manager ,include Printer ,Pinpad ,
 * IC RF Magnetic card ,Beep.before get the mode handle ,should
 * bind usdk service first .
 */

public class DeviceManager {

    private static String DEVICE_SERVICE_PACKAGE_NAME = "com.android.topwise.topusdkservice";
    private static String DEVICE_SERVICE_CLASS_NAME = "com.android.topwise.topusdkservice.service.DeviceService";
    private static String ACTION_DEVICE_SERVICE = "topwise_cloudpos_device_service";

    private static DeviceManager mDeviceServiceManager;


    private static Context mContext;
    private static AidlDeviceService mDeviceService;

    public DeviceManager(Context context) {
        mContext = context;
    }

    public static DeviceManager getInstance() {
        if (null == mDeviceServiceManager) {
            mDeviceServiceManager = new DeviceManager(mContext);
        }
        return mDeviceServiceManager;
    }

    public   boolean bindService() {
        Log.i("jeremy","");

        Intent intent = new Intent();
        intent.setAction(ACTION_DEVICE_SERVICE);
        intent.setClassName(DEVICE_SERVICE_PACKAGE_NAME, DEVICE_SERVICE_CLASS_NAME);

        try {
            boolean bindResult = mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            Log.i("jeremy","bindResult = " + bindResult);
            return bindResult;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void unBindDeviceService() {
        Log.i("jeremy","");

        try {
            mContext.unbindService(mConnection);
        } catch (Exception e) {
            Log.i("jeremy","unbind DeviceService service failed : " + e);
        }
    }

    private static  ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mDeviceService = AidlDeviceService.Stub.asInterface(service);
            Log.i("topwise","onServiceConnected  :  " + mDeviceService);
            EmvDeviceManager.getInstance().init(mDeviceService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("jeremy","onServiceDisconnected  :  " + mDeviceService);
            mDeviceService = null;
        }
    };

    public IBinder getSystemService() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getSystemService();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlSystem getSystemManager() {

        AidlSystem aidlSystem = AidlSystem.Stub.asInterface(getSystemService());
        return aidlSystem;
    }

    public IBinder getPinPad() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPinPad(0);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlPinpad getPinpadManager() {

        AidlPinpad aidlPinpad = AidlPinpad.Stub.asInterface(getPinPad());
        return aidlPinpad;
    }

    public AidlLed getLedManager() {

        AidlLed aidlLed = AidlLed.Stub.asInterface(getLed());

        return aidlLed;
    }

    public IBinder getLed() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getLed();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    public IBinder getPrinter() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPrinter();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlPrinter getPrintManager() {

        AidlPrinter aidlPrinter = AidlPrinter.Stub.asInterface(getPrinter());
        return aidlPrinter;
    }

    public IBinder getCheckCard(){
        try {
            if (mDeviceService != null) {
                return mDeviceService.getCheckCard();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlCheckCard getDetectCardManager() {

        AidlCheckCard aidlCheckCard = AidlCheckCard.Stub.asInterface(getCheckCard());
        return aidlCheckCard;
    }


    public AidlRFCard getRFCard() {

        AidlRFCard aidlRFCard = AidlRFCard.Stub.asInterface(getRFIDReader());
        return aidlRFCard;
    }

    public IBinder getRFIDReader() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getRFIDReader();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IBinder getPSAMReader(int devid) {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPSAMReader(devid);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IBinder getSerialPort(int port) {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getSerialPort(port);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlShellMonitor getShellMonitor() {
        try {
            if (mDeviceService != null) {
                return AidlShellMonitor.Stub.asInterface(mDeviceService.getShellMonitor());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IBinder getCPUCard() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getCPUCard();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IBinder getPedestal() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPedestal();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlICCard getICCardMoniter() {
        try {
            if (mDeviceService != null) {
                return AidlICCard.Stub.asInterface(mDeviceService.getInsertCardReader());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlRFCard getRFCardMoniter() {
        try {
            if (mDeviceService != null) {
                return AidlRFCard.Stub.asInterface(mDeviceService.getRFIDReader());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlCameraScanCode getScanAidl(){
        try {
            if (mDeviceService != null) {
                return AidlCameraScanCode.Stub.asInterface(mDeviceService.getCameraManager());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    public AidlEmvL2 getEmvL2() {
        try {
            if (mDeviceService != null) {
                return AidlEmvL2.Stub.asInterface(mDeviceService.getL2Emv());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlPure getL2Pure() {
        try {
            if (mDeviceService != null) {
                return AidlPure.Stub.asInterface(mDeviceService.getL2Pure());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlPaypass getL2Paypass() {
        try {
            if (mDeviceService != null) {
                return AidlPaypass.Stub.asInterface(mDeviceService.getL2Paypass());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlPaywave getL2Paywave() {
        try {
            if (mDeviceService != null) {
                return AidlPaywave.Stub.asInterface(mDeviceService.getL2Paywave());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlEntry getL2Entry() {
        try {
            if (mDeviceService != null) {
                return AidlEntry.Stub.asInterface(mDeviceService.getL2Entry());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlAmex getL2Amex() {
        try {
            if (mDeviceService != null) {
                return AidlAmex.Stub.asInterface(mDeviceService.getL2Amex());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AidlQpboc getL2Qpboc() {
        try {
            if (mDeviceService != null) {
                return AidlQpboc.Stub.asInterface(mDeviceService.getL2Qpboc());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
