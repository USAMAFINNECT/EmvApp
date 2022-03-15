package com.standard.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.topwise.sdk.emv.daoutils.DaoManager;
import com.topwise.sdk.emv.daoutils.param.AidParam;
import com.topwise.sdk.emv.daoutils.param.CapkParam;
import com.topwise.sdk.emv.daoutils.param.LoadParam;
import com.topwise.tool.api.ITool;
import com.topwise.tool.api.convert.IConvert;
import com.topwise.tool.api.packer.IPacker;
import com.topwise.tool.impl.TopTool;


public class TopApp extends Application{
	private static final String TAG = TopApp.class.getSimpleName();
	public static TopApp mPosApp;
	public static DeviceManager deviceManager;
	public static IConvert convert;
	public static ITool iTool;
	public static IPacker packer;
	@Override
	public void onCreate() {
		super.onCreate();

		Log.i(TAG, "onCreate");
		mPosApp = this;
		deviceManager = new DeviceManager(mPosApp);
		DeviceManager.getInstance().bindService();
		//DBManager.getInstance().init(this);
		// init uDaoManager
		DaoManager.getInstance().init(mPosApp);
		init();
	}
   public static Application getApp(){
		return  mPosApp;
   }

	/**
	 * init tool
	 */
	public static void init(){
		iTool = TopTool.getInstance(mPosApp);
		convert = iTool.getConvert();
		packer = iTool.getPacker();
	}

	public static boolean initAidData(final Context mContext){
		LoadParam aidParam = new AidParam();
		aidParam.init(mContext);
		Log.d(TAG,"init AidParam====");
		return aidParam.saveAll();
	}

	public static boolean initCapkData(final Context mContext){
		LoadParam capkParam = new CapkParam();
		capkParam.init(mContext);
		Log.d(TAG,"init CapkParam====");
	 	return capkParam.saveAll();
	}

}
