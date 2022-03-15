package com.topwise.sdk.emv.daoutils.param;

import android.content.Context;
import android.util.Log;

import com.topwise.sdk.emv.daoutils.entity.uAid;
import com.topwise.sdk.emv.daoutils.entity.uCapk;
import com.topwise.tool.api.convert.IConvert;
import com.topwise.tool.api.packer.ITlv;
import com.topwise.tool.api.packer.TlvException;
import com.topwise.tool.impl.TopTool;

import java.util.List;

/**
 * 创建日期：2021/4/21 on 10:02
 * 描述: 逻辑 可以从xml 读取文件，到数据库，也可以单独加载到数据库
 */
public abstract class LoadParam<T> {
    protected static final String TAG = LoadParam.class.getSimpleName();

    protected List<String> list;
    public abstract boolean DelectAll();

    /**
     * 保存到数据库
     * @return
     */
    public abstract boolean saveAll();

    /**
     * 单独保存记录
     * @param inData
     * @return
     */
    public abstract boolean save(String inData);
    /**
     * 从xml读取list
     * @param context
     * @return
     */
    public abstract List<String> init(Context context);


    /**
     * 解析保存
     * @param aid
     * @return
     */
    protected uAid saveAid(String aid){
        ITlv tlv = TopTool.getInstance(null).getPacker().getTlv();
        IConvert convert = TopTool.getInstance(null).getConvert();
        ITlv.ITlvDataObjList aidTlvList = null;
        ITlv.ITlvDataObj tlvDataObj;
        uAid aidParam;
        byte[] value = null;
        byte[] bytes = convert.strToBcd(aid, IConvert.EPaddingPosition.PADDING_LEFT);
        try {
            aidTlvList = tlv.unpack(bytes);
        } catch (TlvException e) {
            e.printStackTrace();
        }
        aidParam = new uAid();
        // 9f06 AID
        tlvDataObj = aidTlvList.getByTag(0x9f06);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setAid(convert.bcdToStr(value));
            }
        }
        // DF01
        tlvDataObj = aidTlvList.getByTag(0xDF01);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setSelFlag(Integer.parseInt(convert.bcdToStr(value)));
            }
        }
        // 9F08
        tlvDataObj = aidTlvList.getByTag(0x9f08);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setVersion(convert.bcdToStr(value));
            }
        }

        // DF11
        tlvDataObj = aidTlvList.getByTag(0xDF11);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTbcDefualt(convert.bcdToStr(value));
            }
        }

        // DF12
        tlvDataObj = aidTlvList.getByTag(0xDF12);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTacOnline(convert.bcdToStr(value));
            }
        }

        // DF13
        tlvDataObj = aidTlvList.getByTag(0xDF13);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTacDenial(convert.bcdToStr(value));
            }
        }

        // 9F1B
        tlvDataObj = aidTlvList.getByTag(0x9F1B);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setFloorLimit(Integer.parseInt(convert.bcdToStr(value)));
                aidParam.setFloorlimitFlg(true);
            }
        }

        // DF15
        tlvDataObj = aidTlvList.getByTag(0xDF15);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setThreshold(Integer.parseInt(convert.bcdToStr(value)));
            }
        }

        // DF16
        tlvDataObj = aidTlvList.getByTag(0xDF16);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setMaxTargetPer(value[0]);
            }
        }

        // DF17
        tlvDataObj = aidTlvList.getByTag(0xDF17);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTargetPer(value[0]);
            }
        }

        // DF14
        tlvDataObj = aidTlvList.getByTag(0xDF14);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setDDOL(convert.bcdToStr(value));
            }
        }

        // DF18
        tlvDataObj = aidTlvList.getByTag(0xDF18);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
               // aidParam.setAllowOnlinePIN();
            }
        }

        // 9F7B
        tlvDataObj = aidTlvList.getByTag(0x9F7B);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setEcTTL(Integer.parseInt(convert.bcdToStr(value)));
                aidParam.setEcTTLFlg(1);
            }
        }

        // DF19
        tlvDataObj = aidTlvList.getByTag(0xDF19);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setRdClssFloorLimit(Integer.parseInt(convert.bcdToStr(value)));
                aidParam.setRdClssFloorLimitFlg(true);
            }
        }

        // DF20
        tlvDataObj = aidTlvList.getByTag(0xDF20);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setRdClssTxnLimit(Integer.parseInt(convert.bcdToStr(value)));
                aidParam.setRdClssTxnLimitFlg(true);
            }
        }

        // DF21
        tlvDataObj = aidTlvList.getByTag(0xDF21);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setRdCVMLimit(Integer.parseInt(convert.bcdToStr(value)));
                aidParam.setRdCVMLimitFlg(true);
            }
        }

        //DF8102 tDol
        tlvDataObj = aidTlvList.getByTag(0xDF8102);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTDOL(convert.bcdToStr(value));

            }
        }
        //9F1D riskManData
        tlvDataObj = aidTlvList.getByTag(0x9F1D);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setRiskmanData(convert.bcdToStr(value));
            }
        }
        //9F01 s acquierId
        tlvDataObj = aidTlvList.getByTag(0x9F01);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setAcquierId(convert.bcdToStr(value));
            }
        }
        //9F4E s merchName
        tlvDataObj = aidTlvList.getByTag(0x9F4E);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setMerchName(convert.bcdToStr(value));
            }
        }
        //9F15 s merchCateCode
        tlvDataObj = aidTlvList.getByTag(0x9F15);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setMerchCateCode(convert.bcdToStr(value));
            }
        }
        //9F16 s merchId
        tlvDataObj = aidTlvList.getByTag(0x9F16);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setMerchId(convert.bcdToStr(value));
            }
        }
        //9F1C s termId
        tlvDataObj = aidTlvList.getByTag(0x9F1C);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTermId(convert.bcdToStr(value));
            }
        }
        //5F2A s transCurrCode
        tlvDataObj = aidTlvList.getByTag(0x5F2A);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTransCurrCode(convert.bcdToStr(value));
            }
        }
        //5F36 i transCurrExp
        tlvDataObj = aidTlvList.getByTag(0xDF8101);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setTransCurrExp(value[0]);
            }
        }
        //9F3C s referCurrCode
        tlvDataObj = aidTlvList.getByTag(0x9F3C);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setReferCurrCode(convert.bcdToStr(value));
            }
        }
        //9F3D byte referCurrExp
        tlvDataObj = aidTlvList.getByTag(0x9F3D);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setReferCurrExp(value[0]);
            }
        }
        //DF8101 int referCurrCon
        tlvDataObj = aidTlvList.getByTag(0xDF8101);
        if (tlvDataObj != null) {
            value = tlvDataObj.getValue();
            if (value != null && value.length > 0) {
                aidParam.setReferCurrCon(Integer.valueOf(convert.bcdToStr(value)));
            }
        }
        Log.e(TAG,"mUAidDaoUtils uAid  ==" + aidParam.toString());

        return aidParam;
    }

    /**
     *
     * @param capk
     * @return
     */
    protected uCapk saveCapk(String capk){

        try {
            ITlv tlv = TopTool.getInstance(null).getPacker().getTlv();
            IConvert convert = TopTool.getInstance(null).getConvert();
            ITlv.ITlvDataObjList capkTlvList;
            ITlv.ITlvDataObj tlvDataObj;
            uCapk capkParam;
            byte[] value = null;
            Log.d(TAG,"Capk==" + capk);
            byte[] bytes = convert.strToBcd(capk, IConvert.EPaddingPosition.PADDING_LEFT);
            capkTlvList = tlv.unpack(bytes);
            capkParam = new uCapk();

            // 9f06 RID
            tlvDataObj = capkTlvList.getByTag(0x9f06);
            if (tlvDataObj != null) {
                byte[] value9f06 = tlvDataObj.getValue();
                if (value9f06 != null && value9f06.length > 0) {
                    capkParam.setRid( convert.bcdToStr(value9f06));
                    // 9F2201
                    tlvDataObj = capkTlvList.getByTag(0x9f22);
                    if (tlvDataObj != null) {
                        byte[] value9f22 = tlvDataObj.getValue();
                        if (value9f22 != null && value9f22.length > 0) {
                            capkParam.setRindex(convert.bcdToStr(value9f22));
                            // add setRidindex
                            capkParam.setRidindex(convert.bcdToStr(value9f06)+ convert.bcdToStr(value9f22));
                        }
                    }
                }
            }
           /* // 9F2201
            tlvDataObj = capkTlvList.getByTag(0x9f22);
            if (tlvDataObj != null) {
                value = tlvDataObj.getValue();
                if (value != null && value.length > 0) {
                    capkParam.setRindex(convert.bcdToStr(value));
                }
            }*/
            // DF02
            tlvDataObj = capkTlvList.getByTag(0xDF02);
            if (tlvDataObj != null) {
                value = tlvDataObj.getValue();
                if (value != null && value.length > 0) {
                    capkParam.setModul(convert.bcdToStr(value));
                }
            }
            // DF03
            tlvDataObj = capkTlvList.getByTag(0xDF03);
            if (tlvDataObj != null) {
                value = tlvDataObj.getValue();
                if (value != null && value.length > 0) {
                    capkParam.setCheckSum(convert.bcdToStr(value));
                }
            }
            // DF06
            tlvDataObj = capkTlvList.getByTag(0xDF06);
            if (tlvDataObj != null) {
                value = tlvDataObj.getValue();
                if (value != null && value.length > 0) {
                    capkParam.setHashInd(Integer.parseInt(convert.bcdToStr(value)));
                }
            }
            // DF04
            tlvDataObj = capkTlvList.getByTag(0xDF04);
            if (tlvDataObj != null) {
                value = tlvDataObj.getValue();
                if (value != null && value.length > 0) {
                    capkParam.setExponent(convert.bcdToStr(value));
                }
            }
            // DF05
            tlvDataObj = capkTlvList.getByTag(0xDF05);
            if (tlvDataObj != null) {
                value = tlvDataObj.getValue();
                if (value != null && value.length > 0) {
                    capkParam.setExpDate(convert.bcdToStr(value));
                }
            }
            // DF07
            tlvDataObj = capkTlvList.getByTag(0xDF07);
            if (tlvDataObj != null) {
                value = tlvDataObj.getValue();
                if (value != null && value.length > 0) {
                    capkParam.setArithInd(Integer.parseInt(convert.bcdToStr(value)));
                }
            }
            Log.d(TAG,"ridindex =" + capkParam.toString());
            return capkParam;
        } catch (TlvException e) {
            e.printStackTrace();
            return null;
        }
    }

}
