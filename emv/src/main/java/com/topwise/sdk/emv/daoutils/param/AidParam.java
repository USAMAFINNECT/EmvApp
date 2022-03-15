package com.topwise.sdk.emv.daoutils.param;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.topwise.cloudpos.struct.BytesUtil;
import com.topwise.cloudpos.struct.TlvList;
import com.topwise.sdk.emv.daoutils.DaoUtilsStore;
import com.topwise.sdk.emv.daoutils.entity.uAid;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 创建日期：2021/12/14 on 10:03
 * 描述:
 *
 */
public class AidParam extends LoadParam<uAid>{

    private static final String AIDNAME = "icparam/aid.xml";

    @Override
    public boolean DelectAll() {
        return DaoUtilsStore.getInstance().getmUAidDaoUtils().deleteAll();
    }

    /**
     * save all
     */
    @Override
    public boolean saveAll() {
        if (list == null || list.size() == 0) return false;
        long l = System.currentTimeMillis();
        Log.e("","Aid save Aid All start time" + l );
        List<uAid> uAidList = new ArrayList<uAid>();
        for (String aid :list) {
            Log.d("","Aid==" + aid);
            uAid aidParam = saveAid(aid);
            uAidList.add(aidParam);
        }
         boolean bAid = DaoUtilsStore.getInstance().getmUAidDaoUtils().insertMultiple(uAidList);
        Log.e("","Aid save Aid All end time " + (System.currentTimeMillis() - l)+"| bAid is "+bAid);
        return bAid;
    }

    /**
     * save one
     * @param inData
     * @return
     */
    @Override
    public boolean save(String inData) {
        if (TextUtils.isEmpty(inData)) return false;
        Log.d("","Aid==" + inData);
        uAid aidParam = saveAid(inData);
        boolean bAid = DaoUtilsStore.getInstance().getmUAidDaoUtils().save(aidParam);
        Log.d("","bAid==" + bAid);
        return bAid;
    }

    /**
     * 从xml文件 解析List<String>
     * @param context
     * @return
     */
    @Override
    public List<String> init(Context context) {
        long l = System.currentTimeMillis();
        Log.e("","Aid init start " + l);
        try {
            InputStream open = context.getResources().getAssets().open(AIDNAME);
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuidler = null;
            Document doc = null;
            docBuidler = docFact.newDocumentBuilder();
            doc = docBuidler.parse(open);
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getElementsByTagName("aid");

            list = new ArrayList<>();
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                String aidparam = element.getAttribute("aidparam");
                list.add(aidparam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("","AidParam init err");
            return null;
        }
        Log.e("","Aid init end " + (System.currentTimeMillis() - l));
        Log.d("","AidParam init ==" +list.toString());
        return list;
    }

    /**
     * 直接从数据库，读取数据 透传到内核;静态方法
     * @return
     */
    public static List<uAid> getDbEmvAidParamList(){
        long l = System.currentTimeMillis();
        Log.e(TAG,"EmvAidParam getDbEmvAidParamList start " + l);
        List<uAid> uAids = DaoUtilsStore.getInstance().getmUAidDaoUtils().queryAll();
        Log.e(TAG,"EmvAidParam getDbEmvAidParamList end " + (System.currentTimeMillis() - l) + "  " +uAids.size());
        return uAids;
    }

    public static TlvList getTlvList(uAid emvAidParam){
        final String TAG = "Aid,getTlvList";

        TlvList tlvList = new TlvList();
        tlvList.addTlv("9F06",emvAidParam.getAid());
        tlvList.addTlv("DF01",String.format("%02d",emvAidParam.getSelFlag()));
        tlvList.addTlv("DF17",String.format("%02d",emvAidParam.getTargetPer()));
        tlvList.addTlv("DF16",String.format("%02d",emvAidParam.getMaxTargetPer()));
        tlvList.addTlv("9F1B", BytesUtil.int2Bytes(Integer.valueOf(emvAidParam.getFloorLimit() + ""),true));
        tlvList.addTlv("DF19", BytesUtil.hexString2Bytes(String.format("%012d", emvAidParam.getRdClssFloorLimit())));
        tlvList.addTlv("DF20", BytesUtil.hexString2Bytes(String.format("%012d", emvAidParam.getRdClssTxnLimit())));
        tlvList.addTlv("DF21", BytesUtil.hexString2Bytes(String.format("%012d", emvAidParam.getRdCVMLimit())));
        tlvList.addTlv("DF15", BytesUtil.int2Bytes(Integer.valueOf(emvAidParam.getThreshold() + ""),true));
        tlvList.addTlv("DF13",emvAidParam.getTacDenial());
        tlvList.addTlv("DF12",emvAidParam.getTacOnline());
        tlvList.addTlv("DF11",emvAidParam.getTbcDefualt());
        tlvList.addTlv("9F01",emvAidParam.getAcquierId());
        tlvList.addTlv("DF14",emvAidParam.getdDOL());
        tlvList.addTlv("DF8102",emvAidParam.gettDOL());
        tlvList.addTlv("9F09",emvAidParam.getVersion());
        tlvList.addTlv("9F1D",emvAidParam.getRiskmanData());
        tlvList.addTlv("9F4E",emvAidParam.getMerchName());
        tlvList.addTlv("9F15",emvAidParam.getMerchCateCode());
        tlvList.addTlv("9F16",emvAidParam.getTermId());
        tlvList.addTlv("9F1C",emvAidParam.getTermId());
        tlvList.addTlv("5F2A",emvAidParam.getTransCurrCode());
        tlvList.addTlv("5F36",String.format("%02d",emvAidParam.getTransCurrExp()));
        tlvList.addTlv("9F3C",emvAidParam.getReferCurrCode());
        tlvList.addTlv("9F3D",String.format("%02d",emvAidParam.getReferCurrExp()));
        tlvList.addTlv("DF8101", BytesUtil.int2Bytes(emvAidParam.getReferCurrCon(),true));
        tlvList.addTlv("9F7B",String.format("%02d",emvAidParam.getEcTTL()));
        return tlvList;
    }

    public static uAid queryByAid(String aidHex){
        return  DaoUtilsStore.getInstance().getmUAidDaoUtils().queryByAid(uAid.class,aidHex);
    }
}
