/****
 *
 * Emv  Demo follow bellow Steps:
 * step 1.download capk and aid see method downLoadParam,just need do once
 * step 2.download keys see method downLoadKeys ,just need do once
 * step 3.search card  see method checkCard
 * step 4.start emv process after searched ic or rf card
 *      aidl.processPBOC(EmvTransData var1, AidlPbocStartListener callback)
 *      param EmvTransData please see document《topwisesdkinterfaceV3.1》 page 15
 *      param AidlCheckCardListener the callback of emv,
 *      every evm callback is block ,its reference function should be call
 *      to notify the emv process continue.the emv callback follow blew steps:

 *       APP                                        Emv Kernel
 *       |<-----------requestImportAmount------------  |
 *       |------------importAmount------------------>  |
 *
 *     -------------- if card have multi-application ---------
 *       |<-----------requestAidSelect---------------  |
 *       |------------importAidSelect--------------->  |
 *     ------------ if card have multi-application -----------
 *
 *       |<-----------finalAidSelect-----------------  |
 *       |------------importFinalAidSelectRes--------->|
 *       |<-----------onConfirmCardInfo--------------- |
 *       |------------setConfirmCardInfo-------------->|
 *       |<-----------requestImportPin---------------  |
 *       |------------importPin----------------------> |
 *       |<-----------onRequestOnline----------------  |
 *       |------------importOnlineResp---------------> |
 *       |<-----------onTransResult------------------  |
 */



package com.standard.app.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.standard.app.DeviceManager;
import com.standard.app.R;
import com.standard.app.TopApp;
import com.topwise.cloudpos.aidl.emv.level2.AidlEmvL2;
import com.topwise.cloudpos.aidl.emv.level2.EmvTerminalInfo;
import com.topwise.sdk.emv.daoutils.param.AidParam;
import com.topwise.sdk.emv.daoutils.param.CapkParam;
import com.standard.app.storage.ConsumeFieldInfo;
import com.standard.app.util.BCDASCII;
import com.standard.app.util.HexUtil;
import com.topwise.cloudpos.aidl.card.AidlCheckCard;
import com.topwise.cloudpos.aidl.card.AidlCheckCardListener;
import com.topwise.cloudpos.aidl.emv.PCardLoadLog;
import com.topwise.cloudpos.aidl.emv.PCardTransLog;
import com.topwise.cloudpos.aidl.led.AidlLed;
import com.topwise.cloudpos.aidl.magcard.TrackData;
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad;
import com.topwise.cloudpos.aidl.pinpad.GetPinListener;
import com.topwise.cloudpos.data.PinpadConstant;
import com.topwise.sdk.emv.CardType;
import com.topwise.sdk.emv.EmvDeviceManager;
import com.topwise.sdk.emv.EmvManager;
import com.topwise.sdk.emv.EmvTransData;
import com.topwise.sdk.emv.OnEmvProcessListener;


public class MainPageActivity extends Activity implements View.OnClickListener{
    private static final String TAG =  MainPageActivity.class.getSimpleName();

    private static final int DIALOG_EXIT_APP = 100;
    private static final int SEARCH_CARD_TIME = 30000;

    private AlertDialog.Builder mAlerDialog;

    public static ConsumeFieldInfo info;
    private ProgressDialog mProgressDialog;
    private EmvManager aidlPboc ;
    private AidlPinpad aidlPin;
    private int mMainKeyIndex = 0x00;//main key index
    private int mWorkKeyIndex = 0x00;//work key index
    private EmvTransData transData;

    private int showLineNum = 0;

    private LinearLayout linearLayout;
    private ScrollView scrollView;
    private TextView textView1;

    private AidlCheckCard aidlCard;
    private AidlEmvL2 aidlEmvL2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flow);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        linearLayout = (LinearLayout) this.findViewById(R.id.tipLinearLayout);
        scrollView = (ScrollView) this.findViewById(R.id.tipScrollView);
        aidlPboc = EmvManager.getInstance();
        aidlCard = EmvDeviceManager.getInstance().getDetectCardManager();
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_param:
                showResult("download param ....");
                downLoadParam();
                break;
            case R.id.download_keys:
                showResult("download keys ....");
                downLoadKeys();
                break;
            case R.id.emv_process:
                showResult("Use IC Or RF Card....");
                checkCard();
                break;

            default:
                break;
        }
    }



    public void onAddAid(View v) {
        showResult("onAddAid ....");
        String aidStr = "9F0607A0000005241010DF0101009F08020140DF1105D84000A800DF1205D84004F800DF130500100000009F1B0400000000DF150400000000DF160199DF170199DF14039F3704DF180100DF1906000000030000DF2006000999999999DF2106000000100000";
//        DBManager.getInstance().getAidDao().addAid(aidStr);
    }


    public void onDeleAid(View v) {
        showResult("onDeleAid ....");
        String rid = "A0000005241010";
//        DBManager.getInstance().getAidDao().deleteAid(rid);
    }

    public void onDeleAllAid(View v) {
        showResult("onDeleAllAid ....");
//        DBManager.getInstance().getAidDao().deleteAllAid();
    }




    @Override
    public void onBackPressed() {
        Log.v(TAG, "onBackPressed");
        showDialogExt(DIALOG_EXIT_APP);
    }
    private void checkCard(){

        try {
            EmvDeviceManager.getInstance().getDetectCardManager().checkCard(true,true,true,SEARCH_CARD_TIME, new AidlCheckCardListener.Stub() {
                @Override
                public void onFindMagCard(TrackData trackData) throws RemoteException {
                      showResult("find mag card");
                }

                @Override
                public void onSwipeCardFail() throws RemoteException {
                    showResult("Card not found try again");
                }

                @Override
                public void onFindICCard() throws RemoteException {
                    transData =  new EmvTransData(CardType.IC, (byte)0, 1, false);
                    aidlPboc.startEmvProcess(transData,new EmvListener());
                }

                @Override
                public void onFindRFCard() throws RemoteException {
                    transData =  new EmvTransData(CardType.RF, (byte)0, 1, false);
                    aidlPboc.startEmvProcess(transData,new EmvListener());

                }

                @Override
                public void onTimeout() throws RemoteException {
                    showResult("Time Out");
                }

                @Override
                public void onCanceled() throws RemoteException {
                    showResult("User cancel search card");

                }

                @Override
                public void onError(int i) throws RemoteException {
                    aidlCard.cancelCheckCard();
                    showResult("check card error "+i);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
      /*  IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mCloseSystemDialogsReceiver, filter);*/


    }

    private void closeLed(){

        AidlLed mAidlLed = DeviceManager.getInstance().getLedManager();
        try {
            if(mAidlLed != null){
                mAidlLed.setLed(0 , false);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showDialogExt(int type) {
        switch (type) {
            case DIALOG_EXIT_APP:
                mAlerDialog = new AlertDialog.Builder(this);
                mAlerDialog.setCancelable(false);
                    mAlerDialog.setMessage("exit app");
                    mAlerDialog.setNegativeButton("No", null);
                    mAlerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                mAlerDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
  //      unregisterReceiver(mCloseSystemDialogsReceiver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(aidlCard != null)
                aidlCard.cancelCheckCard();
            aidlPboc.endEmv();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    private void downLoadParam(){
        // db init
        boolean mIsAidSuccess = TopApp.initAidData(TopApp.mPosApp);
        if(mIsAidSuccess){
            showResult("downLoadParam AID Success");
        }else{
            showResult("downLoadParam AID Fail");
        }

        boolean mIsCapkSuccess = TopApp.initCapkData(TopApp.mPosApp);
        if(mIsCapkSuccess){
            showResult("downLoadParam CAPK Success");
        }else{
            showResult("downLoadParam CAPK Fail");
        }
    }


    private void downLoadKeys(){
        final AidlPinpad pinpadManager = DeviceManager.getInstance().getPinpadManager();
        final byte[] tmk = BCDASCII.hexStringToBytes("89F8B0FDA2F2896B9801F131D32F986D89F8B0FDA2F2896B");
        final byte[] tak = BCDASCII.hexStringToBytes("92B1754D6634EB22");
        final byte[] tpk = BCDASCII.hexStringToBytes("B5E175AC5FD8DD8A03AD23A35C5BAB6B");
        final byte[] trk = BCDASCII.hexStringToBytes("744185122EEC284830694CAD383B4F7A");
        boolean mIsSuccess =false;
        try {
            mIsSuccess = pinpadManager.loadMainkey(mMainKeyIndex, tmk, null);

            mIsSuccess = pinpadManager.loadWorkKey(PinpadConstant.WKeyType.WKEY_TYPE_MAK, mMainKeyIndex, mWorkKeyIndex, tak, null);

            mIsSuccess = pinpadManager.loadWorkKey(PinpadConstant.WKeyType.WKEY_TYPE_PIK, mMainKeyIndex, mWorkKeyIndex, tpk, null);

            mIsSuccess = pinpadManager.loadWorkKey(PinpadConstant.WKeyType.WKEY_TYPE_TDK, mMainKeyIndex, mWorkKeyIndex, trk, null);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(mIsSuccess){
            showResult("Download Keys Success");
        }else{
            showResult("Download Keys Success Fail");
        }

    }

    private  void showResult(final  String tip){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ((showLineNum % 300 == 0) && (showLineNum > 0)) { // 显示够20行的时候重新开始
                    if(linearLayout != null) {
                        linearLayout.removeAllViews();
                    } else {
                        linearLayout = (LinearLayout) findViewById(R.id.tipLinearLayout);
                    }
                    showLineNum = 0;
                }
                showLineNum++;
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.show_item, null);
                textView1 = (TextView) v.findViewById(R.id.tip1);
                textView1.setText(tip);
                textView1.setTextColor(Color.RED);
                textView1.setTextSize(20);
                textView1.setBackgroundResource(R.drawable.background);
                linearLayout.addView(v);
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }


    /*********************
     * the emv callback flow
     */
    private class EmvListener implements OnEmvProcessListener {

        /*******
         * callback 1 requestImportAmount
         * call importAmount notify the emv kernel to continue process
         *
         */
        @Override
        public void requestImportAmount(int arg0) throws RemoteException {
            showResult("requestImportAmount");
            /*****notice importAmount should transfer
             *  correct amount to the emv kernel
             *
             *  *********/
            aidlPboc.importAmount("15");

        }

        /*******
         * callback 2 requestAidSelect
         * if the card have multi-application,in this callback the application should
         * show app-list to  the user ,and select an application, call
         * importAidSelectRes tell emv kernel which application have be selected
         * @param  times
         * @param  arg1 application name list
         */
        @Override
        public void requestAidSelect(int times, String[] arg1)
                throws RemoteException {
            showResult("please choice application");
            String str = "";
            for (int i = 0; i < arg1.length; i++) {
                str += arg1[i];
            }

            aidlPboc.importAidSelectRes(0x01);
        }

        /*******
         * callback 3 requestAidSelect
         * call importFinalAidSelectRes notify the emv kernel to continue process
         * in this callback  ,can set terminal param like show below
         */
        @Override
        public void finalAidSelect() throws RemoteException {
            showResult("finalAidSelect");
//            aidlPboc.setTlv("9F1A", BCDASCII.hexStringToBytes("0156"));
//            aidlPboc.setTlv("5F2A", BCDASCII.hexStringToBytes("0156"));
//            aidlPboc.setTlv("9F3C", BCDASCII.hexStringToBytes("0156"));
            aidlEmvL2 = EmvDeviceManager.getInstance().getEmvL2();
            EmvTerminalInfo emvTerminalInfo =  aidlEmvL2.EMV_GetTerminalInfo();
            if (emvTerminalInfo != null) {
                emvTerminalInfo.setAucTerminalCountryCode(BCDASCII.hexStringToBytes("0156"));
                emvTerminalInfo.setAucTransCurrencyCode(BCDASCII.hexStringToBytes("0156"));
                emvTerminalInfo.setAucTransRefCurrencyCode(BCDASCII.hexStringToBytes("0156"));
                aidlEmvL2.EMV_SetTerminalInfo(emvTerminalInfo);
            }
            aidlPboc.importFinalAidSelectRes(true);
        }

        /*******
         * callback 4 onConfirmCardInfo
         * in onConfirmCardInfo，show card No. to user  if  necessary,
         * and  call importConfirmCardInfoRes notify the emv kernel to
         * continue process after Confirm Card No
         * @param  cardNo the card info see document
         *
         */
        @Override
        public void onConfirmCardInfo(String cardNo) throws RemoteException {
            Log.v(TAG,"onConfirmCardInfo");
            showResult(getString(R.string.card_info) + cardNo);
            showResult(getString(R.string.please_confirm));
            aidlPboc.importConfirmCardInfoRes(true);
        }
        /*******
         * callback 4 requestImportPin
         * in requestImportPin,call getPin function to get pin
         * and call importPin notify the emv kernel to continue process
         * @param  type  03== online pin ,others offline pin
         * @param  lastFlag  valid on offline pin , show whether is the last time to input offline Pin
         *
         */
        @Override
        public void requestImportPin(int type, boolean lastFlag, String amount)
                throws RemoteException {
            Log.v(TAG,"requestImportPin");
            showResult("please input Pin");
            byte pinType ;
            /*******online pin **********/
            if(type == 0x03){
                pinType =0x00;
            }
            /****offline pin****/
            else{
                pinType = 0x01;
            }
            Bundle bundle = getPinParam(pinType);
            aidlPin = DeviceManager.getInstance().getPinpadManager();
            aidlPin.getPin(bundle,new MyGetPinListener());
        }


        /****************
         *if EMV kernel request online process, onRequestOnline will be call .
         * Then the application
         * should call method getTlv to get the EMV tags, then send request message to the host. After host
         * response, the application should call importOnlineResp to notify the EMV kernel
         * to do the second aut
         * @throws RemoteException
         */
        @Override
        public void onRequestOnline() throws RemoteException {
            showResult(getString(R.string.request_online));

            /*******get  EMV tags  here *********/
            String seqNum =  getSeqNum();
            showResult("seqNum "+seqNum);
            String track2 =  getTrack2();
            showResult("track2 "+track2);
            String filed55Data =  getConsume55();
            showResult("filed55Data "+filed55Data);


             /***************************/
             //send the iso8583 data to host here
            /***************************/


            /*******
             * after receive the data from the host ,call importOnlineResp;
             * importOnlineResp
             * param 1 : trans result from host
             * param 2: 39 filed the result from host
             * param 3: 55 filed, the script  from host
             * ************/
            aidlPboc.importOnlineResp(true, "00", null);
        }

        /**********
         *onTransResult the final trans result
         * @param arg0
         * 0x01 : trans allow
         * 0x02 : trans refuse
         * 0x03 : trans stop
         * 0x04 : trans Downgrade
         * @throws RemoteException
         */

        @Override
        public void onTransResult(int arg0) throws RemoteException {
            Log.v(TAG,"onTransResult(+"+ arg0 +")");
            DeviceManager.getInstance().getRFCard().close();
            closeLed();
            switch (arg0) {
                case 0x01:
                    showResult(getString(R.string.allow_trading));
                    break;
                case 0x02:
                    showResult(getString(R.string.Refuse_to_deal));
                    break;
                case 0x03:
                    showResult(getString(R.string.stop_trading));
                    break;
                case 0x04:
                    showResult(getString(R.string.downgrade));
                    break;
                case 0x05:
                case 0x06:
                default:
                    showResult(getString(R.string.unknown_exception));
                    break;
            }
        }


        /**************
         * ignore this callback
         */
        @Override
        public void requestUserAuth(int certType, String certno) throws RemoteException {
            showResult("requestUserAuth");
            aidlPboc.importUserAuthRes(true);
        }
        /**************
         * ignore this callback
         */
        @Override
        public void requestTipsConfirm(String arg0) throws RemoteException {
            showResult("requestTipsConfirm");
            aidlPboc.importMsgConfirmRes(true);
        }


        /**************
         * ignore this callback
         */
        @Override
        public void requestEcashTipsConfirm() throws RemoteException {
            showResult("requestEcashTipsConfirm");
            aidlPboc.importECashTipConfirmRes(false);
        }

        /**************
         * ignore this callback
         */
        @Override
        public void onReadCardTransLog(PCardTransLog[] arg0) throws RemoteException {


        }
        /**************
         * ignore this callback
         */
        @Override
        public void onReadCardOffLineBalance(String arg0, String arg1, String arg2,
                                             String arg3) throws RemoteException {

       }
        /**************
         * ignore this callback
         */
        @Override
        public void onReadCardLoadLog(String arg0, String arg1, PCardLoadLog[] arg2)
                throws RemoteException {


        }

        @Override
        public void onError(int arg0) throws RemoteException {
            showResult("onError " + arg0);
            Log.v(TAG,"onError "+arg0);
            aidlPboc.endEmv();
            if(aidlCard != null)
                aidlCard.cancelCheckCard();
        }
    }

    /********
     *  wkeyid :pin key index;
     *  keytype: pin type 0x01== offline pin ;0x00 = online pin
     *  input_pin_mode :pin  mode   0,4,5,6  mean the  pin len will be 0,4,5,6
     *  if you want to disable bypass ,0 should not  in the  string
     *  pan :card no
     *  the more param pls see document  p28
     * ************/
    private Bundle getPinParam(byte pinType){
        Bundle bundle = new Bundle();
        bundle.putInt("wkeyid", mWorkKeyIndex);
        bundle.putInt("keytype", pinType);
        bundle.putString("pan", "0000000000000000");
        bundle.putString("tips", "RMB:2000.00");
        /*** pin  mode   0,4,5,6  mean the  pin len will be 0,4,5,6
         * if you want to disable bypass ,0 should not  in the  string
         * ***/
        bundle.putString("input_pin_mode","0,4,5,6");
        return  bundle;
    }


    private class MyGetPinListener extends GetPinListener.Stub {
        @Override
        public void onStopGetPin() throws RemoteException {
            //showMessage("您取消了PIN输入");
            showResult("onStopGetPin ");
        }

        @Override
        public void onInputKey(int len, String arg1) throws RemoteException {
            //showMessage(getStar(arg0) + arg1 == null ? "" : arg1);
            Log.v(TAG, "onInputKey " + "len "+ len +" arg1 " +arg1);
            showResult("input pin " +arg1);
        }

        @Override
        public void onError(int errorCode) throws RemoteException {
            Log.v(TAG, "onError " +errorCode);
            showResult("input error code " +errorCode);

            closeLed();
        }

        @Override
        public void onConfirmInput(byte[] arg0) throws RemoteException {
            Log.v(TAG, "input success");
            showResult("get Pin " +HexUtil.bcd2str(arg0));
            /***call importPin notify the emv kernel to continue process ****/
            aidlPboc.importPin(HexUtil.bcd2str(arg0));
        }

        @Override
        public void onCancelKeyPress() throws RemoteException {
            Log.v(TAG, "onCancelKeyPress ");
            showResult("onCancelKeyPress ");
        }
    }

    private String  getSeqNum() {
        Log.i(TAG, "getSeqNum()");
        String[] seqNumTag = new String[]{"5F34"};
        byte[] seqNumTlvList = getTlv(seqNumTag);
        String cardSeqNum = null;

        if (seqNumTlvList != null) {
            cardSeqNum = BCDASCII.bytesToHexString(seqNumTlvList);
            cardSeqNum = cardSeqNum.substring(cardSeqNum.length() - 2, cardSeqNum.length());
        }
        Log.d(TAG, "setSeqNum : " + cardSeqNum);
        return cardSeqNum;

    }

    private String getTrack2() {
        Log.i(TAG, "getTrack2()");
        String[] track2Tag = new String[]{"57"};
        byte[] track2TlvList = getTlv(track2Tag);

        byte[] temp = new byte[track2TlvList.length - 2];
        System.arraycopy(track2TlvList, 2, temp, 0, temp.length);
        String track2 = processTrack2(BCDASCII.bytesToHexString(temp));
        return track2;
    }

    private  String processTrack2(String track) {
        Log.i(TAG, "processTrack2()");
        StringBuilder builder = new StringBuilder();
        String subStr = null;
        String resultStr = null;
        for (int i = 0; i < track.length(); i++) {
            subStr = track.substring(i, i + 1);
            if (!subStr.endsWith("F")) {
                /*if(subStr.endsWith("D")) {
                    builder.append("=");
                } else {*/
                builder.append(subStr);
                /*}*/
            }
        }
        resultStr = builder.toString();
        return resultStr;
    }
    private String  getConsume55() {
            Log.i(TAG, "getConsume55()");
        /*String[] consume55Tag = new String[]{"9F26", "9F27", "9F10", "9F37", "9F36", "95", "9A", "9C", "9F02", "5F2A",
                "82", "9F1A", "9F03", "9F33", "9F34", "9F35", "9F1E", "84", "9F09",
                "91", "71", "72", "DF32", "DF33", "DF34"};*/
            String[] consume55Tag = new String[]{"4F", "82", "95", "9A", "9B", "9C", "5F24",
                    "5F2A", "9F02", "9F03", "9F06", "9F10", "9F12", "9F1A", "9F1C", "9F26",
                    "9F27", "9F33", "9F34", "9F36", "9F37", "C2", "CD", "CE", "C0", "C4",
                    "C7", "C8"};
            byte[] consume55TlvList = getTlv(consume55Tag);
            String  filed55 =BCDASCII.bytesToHexString(consume55TlvList);
            Log.d(TAG, "setConsume55 consume55TlvList : " + filed55);
            return filed55;
        }


    private byte[] getTlv(String[] tags) {
        StringBuilder sb = new StringBuilder();
        for (String tag : tags) {
            byte[] tempByte = aidlPboc.getTlv(tag);
            String strResult = BCDASCII.bytesToHexString(tempByte);
            Log.d(TAG, "temp: " + strResult);
            sb.append(strResult);
        }
        return BCDASCII.hexStringToBytes(sb.toString());
    }
}

