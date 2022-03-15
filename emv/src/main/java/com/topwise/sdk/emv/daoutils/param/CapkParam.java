package com.topwise.sdk.emv.daoutils.param;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.topwise.sdk.emv.daoutils.DaoUtilsStore;
import com.topwise.sdk.emv.daoutils.entity.uCapk;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * 创建日期：2021/12/14 on 9:49
 * 描述:
 *
 */
public class CapkParam extends LoadParam<uCapk>{
    private static final String CAPKNAME = "icparam/capk.xml";

    @Override
    public boolean DelectAll() {
        return DaoUtilsStore.getInstance().getmUCapkDaoUtils().deleteAll();
    }

    @Override
    public boolean saveAll() {
        if (list == null || list.size() == 0) return false;
        long l = System.currentTimeMillis();
        Log.e(TAG,"Capk saveAll start time " + l );
        List<uCapk> uCapkList = new ArrayList<uCapk>();
        for (String capk:list) {
            Log.d(TAG,"Capk==000" + capk);
            uCapk capkParam = saveCapk(capk);
            uCapkList.add(capkParam);
        }
        boolean bCapk = DaoUtilsStore.getInstance().getmUCapkDaoUtils().insertMultiple(uCapkList);
        Log.e("","Aid saveAll end time " + (System.currentTimeMillis() - l)+"| bCapk is "+bCapk);
        return bCapk;
    }

    @Override
    public boolean save(String capk) {
        if (TextUtils.isEmpty(capk)) return false;
        uCapk capkParam = saveCapk(capk);
        boolean bCapk =  DaoUtilsStore.getInstance().getmUCapkDaoUtils().save(capkParam);
        Log.d(TAG,"Capk==" + bCapk);
        return bCapk;
    }

    @Override
    public List<String> init(Context context) {
        long l = System.currentTimeMillis();
        Log.e(TAG,"Capk init start " + l);
        try {
            String name = CAPKNAME;
            Log.e(TAG,"Capk init name " + name);
            InputStream open = context.getResources().getAssets().open(name);
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuidler = null;
            Document doc = null;
            docBuidler = docFact.newDocumentBuilder();
            doc = docBuidler.parse(open);
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getElementsByTagName("capk");
            list = new ArrayList<>();
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                String aidparam = element.getAttribute("capkparam");
                list.add(aidparam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"CapkParam init err");
            return null;
        }
        Log.e(TAG,"Capk init end" + (System.currentTimeMillis() - l));
        Log.e(TAG,"CapkParam init ==" +list.toString());
        return list;
    }

    /**
     * 直接从数据库，读取数据 透传到内核;静态方法
     * @return
     */
    public static List<uCapk> getDbEmvCapkParamList(){
        long l = System.currentTimeMillis();
        List<uCapk> uCapks = DaoUtilsStore.getInstance().getmUCapkDaoUtils().queryAll();
        return uCapks;
    }

    public static uCapk queryByRid(String ridHex,String rIndex){
        return  DaoUtilsStore.getInstance().getmUCapkDaoUtils().queryByCapkRid(uCapk.class,ridHex,rIndex);
    }

}


