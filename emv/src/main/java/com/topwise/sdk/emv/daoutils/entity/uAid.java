package com.topwise.sdk.emv.daoutils.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class uAid {
    @Id
    private Long id;
    /**
     * aid, 应用标志
     */
    @Unique
    private String aid;
    /**
     * 选择标志(PART_MATCH 部分匹配 FULL_MATCH 全匹配)
     */
    private int selFlag;
    /**
     * 终端联机PIN支持能力
     */
    private boolean isAllowOnlinePIN;
    /**
     * 电子现金终端交易限额(9F7F)
     */
    private int ecTTL;
    /**
     * 读卡器非接触CVM限制(DF21)
     */
    private int rdCVMLimit;
    /**
     * 读卡器非接触交易限额(DF20)
     */
    private int rdClssFloorLimit;
    /**
     * 读卡器非接触交易限额标志(DF20)
     */
    private boolean rdClssFloorLimitFlg;
    /**
     * 读卡器非接触脱机最低限额(DF19)
     */
    private long rdClssFLmt;
    /**
     * TTL存在? 1-存在 电子现金终端交易限额（EC Terminal Transaction Limit）(9F7B)
     */
    private int ecTTLFlg;
    /**
     * 是否存在读卡器非接触脱机最低限额
     */
    private int rdClssTxnLimit;

    /**
     * 是否存在读卡器非接触交易限额
     */
    private boolean rdClssTxnLimitFlg;
    /**
     * 是否存在读卡器非接触CVM限额
     */
    private boolean rdCVMLimitFlg;

    /**
     * 目标百分比数
     */
    private int targetPer;
    /**
     * 最大目标百分比数
     */
    private int maxTargetPer;
    /**
     * 是否检查最低限额
     */
    private boolean floorlimitFlg;
    /**
     * 是否进行随机交易选择
     */
    private boolean randTransSel;
    /**
     * 是否进行频度检测
     */
    private boolean velocityCheck;
    /**
     * 最低限额
     */
    private int floorLimit;
    /**
     * 阀值
     */
    private int threshold;
    /**
     * 终端行为代码(拒绝)
     */
    private String tacDenial;
    /**
     * 终端行为代码(联机)
     */
    private String tacOnline;
    /**
     * 终端行为代码(缺省)
     */
    private String tbcDefualt;
    /**
     * 收单行标志־
     */
    private String acquierId;
    /**
     * 终端缺省DDOL
     */
    private String dDOL;
    /**
     * 终端缺省TDOL
     */
    private String tDOL;
    /**
     * 应用版本
     */
    private String version;
    /**
     * 风险管理数据
     */
    private String riskmanData;

    private String kernelType;

    private String riskManData;
    /**
     * DF11
     */
    private String tacDefault;

    //9F4E s merchName
    private String merchName;
    //9F15 s merchCateCode
    private String merchCateCode;
    //9F16 s merchId
    private String merchId;
    //9F1C s termId
    private String termId;
    //5F2A s transCurrCode
    private String transCurrCode;
    //5F36 i transCurrExp
    private int transCurrExp;
    //9F3C s referCurrCode
    private String referCurrCode;
    //9F3D byte referCurrExp
    private int referCurrExp;
    //DF8101 int referCurrCon
    private int referCurrCon;


    @Generated(hash = 1523399000)
    public uAid() {
    }

    @Generated(hash = 1007039918)
    public uAid(Long id, String aid, int selFlag, boolean isAllowOnlinePIN,
            int ecTTL, int rdCVMLimit, int rdClssFloorLimit,
            boolean rdClssFloorLimitFlg, long rdClssFLmt, int ecTTLFlg,
            int rdClssTxnLimit, boolean rdClssTxnLimitFlg, boolean rdCVMLimitFlg,
            int targetPer, int maxTargetPer, boolean floorlimitFlg,
            boolean randTransSel, boolean velocityCheck, int floorLimit,
            int threshold, String tacDenial, String tacOnline, String tbcDefualt,
            String acquierId, String dDOL, String tDOL, String version,
            String riskmanData, String kernelType, String riskManData,
            String tacDefault, String merchName, String merchCateCode,
            String merchId, String termId, String transCurrCode, int transCurrExp,
            String referCurrCode, int referCurrExp, int referCurrCon) {
        this.id = id;
        this.aid = aid;
        this.selFlag = selFlag;
        this.isAllowOnlinePIN = isAllowOnlinePIN;
        this.ecTTL = ecTTL;
        this.rdCVMLimit = rdCVMLimit;
        this.rdClssFloorLimit = rdClssFloorLimit;
        this.rdClssFloorLimitFlg = rdClssFloorLimitFlg;
        this.rdClssFLmt = rdClssFLmt;
        this.ecTTLFlg = ecTTLFlg;
        this.rdClssTxnLimit = rdClssTxnLimit;
        this.rdClssTxnLimitFlg = rdClssTxnLimitFlg;
        this.rdCVMLimitFlg = rdCVMLimitFlg;
        this.targetPer = targetPer;
        this.maxTargetPer = maxTargetPer;
        this.floorlimitFlg = floorlimitFlg;
        this.randTransSel = randTransSel;
        this.velocityCheck = velocityCheck;
        this.floorLimit = floorLimit;
        this.threshold = threshold;
        this.tacDenial = tacDenial;
        this.tacOnline = tacOnline;
        this.tbcDefualt = tbcDefualt;
        this.acquierId = acquierId;
        this.dDOL = dDOL;
        this.tDOL = tDOL;
        this.version = version;
        this.riskmanData = riskmanData;
        this.kernelType = kernelType;
        this.riskManData = riskManData;
        this.tacDefault = tacDefault;
        this.merchName = merchName;
        this.merchCateCode = merchCateCode;
        this.merchId = merchId;
        this.termId = termId;
        this.transCurrCode = transCurrCode;
        this.transCurrExp = transCurrExp;
        this.referCurrCode = referCurrCode;
        this.referCurrExp = referCurrExp;
        this.referCurrCon = referCurrCon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public int getSelFlag() {
        return selFlag;
    }

    public void setSelFlag(int selFlag) {
        this.selFlag = selFlag;
    }

    public boolean isAllowOnlinePIN() {
        return isAllowOnlinePIN;
    }

    public void setAllowOnlinePIN(boolean allowOnlinePIN) {
        isAllowOnlinePIN = allowOnlinePIN;
    }

    public int getEcTTL() {
        return ecTTL;
    }

    public void setEcTTL(int ecTTL) {
        this.ecTTL = ecTTL;
    }

    public int getRdCVMLimit() {
        return rdCVMLimit;
    }

    public void setRdCVMLimit(int rdCVMLimit) {
        this.rdCVMLimit = rdCVMLimit;
    }

    public int getRdClssFloorLimit() {
        return rdClssFloorLimit;
    }

    public void setRdClssFloorLimit(int rdClssFloorLimit) {
        this.rdClssFloorLimit = rdClssFloorLimit;
    }

    public boolean isRdClssFloorLimitFlg() {
        return rdClssFloorLimitFlg;
    }

    public void setRdClssFloorLimitFlg(boolean rdClssFloorLimitFlg) {
        this.rdClssFloorLimitFlg = rdClssFloorLimitFlg;
    }

    public long getRdClssFLmt() {
        return rdClssFLmt;
    }

    public void setRdClssFLmt(long rdClssFLmt) {
        this.rdClssFLmt = rdClssFLmt;
    }

    public int getEcTTLFlg() {
        return ecTTLFlg;
    }

    public void setEcTTLFlg(int ecTTLFlg) {
        this.ecTTLFlg = ecTTLFlg;
    }

    public int getRdClssTxnLimit() {
        return rdClssTxnLimit;
    }

    public void setRdClssTxnLimit(int rdClssTxnLimit) {
        this.rdClssTxnLimit = rdClssTxnLimit;
    }

    public boolean isRdClssTxnLimitFlg() {
        return rdClssTxnLimitFlg;
    }

    public void setRdClssTxnLimitFlg(boolean rdClssTxnLimitFlg) {
        this.rdClssTxnLimitFlg = rdClssTxnLimitFlg;
    }

    public boolean isRdCVMLimitFlg() {
        return rdCVMLimitFlg;
    }

    public void setRdCVMLimitFlg(boolean rdCVMLimitFlg) {
        this.rdCVMLimitFlg = rdCVMLimitFlg;
    }

    public int getTargetPer() {
        return targetPer;
    }

    public void setTargetPer(int targetPer) {
        this.targetPer = targetPer;
    }

    public int getMaxTargetPer() {
        return maxTargetPer;
    }

    public void setMaxTargetPer(int maxTargetPer) {
        this.maxTargetPer = maxTargetPer;
    }

    public boolean isFloorlimitFlg() {
        return floorlimitFlg;
    }

    public void setFloorlimitFlg(boolean floorlimitFlg) {
        this.floorlimitFlg = floorlimitFlg;
    }

    public boolean isRandTransSel() {
        return randTransSel;
    }

    public void setRandTransSel(boolean randTransSel) {
        this.randTransSel = randTransSel;
    }

    public boolean isVelocityCheck() {
        return velocityCheck;
    }

    public void setVelocityCheck(boolean velocityCheck) {
        this.velocityCheck = velocityCheck;
    }

    public int getFloorLimit() {
        return floorLimit;
    }

    public void setFloorLimit(int floorLimit) {
        this.floorLimit = floorLimit;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getTacDenial() {
        return tacDenial;
    }

    public void setTacDenial(String tacDenial) {
        this.tacDenial = tacDenial;
    }

    public String getTacOnline() {
        return tacOnline;
    }

    public void setTacOnline(String tacOnline) {
        this.tacOnline = tacOnline;
    }

    public String getTbcDefualt() {
        return tbcDefualt;
    }

    public void setTbcDefualt(String tbcDefualt) {
        this.tbcDefualt = tbcDefualt;
    }

    public String getAcquierId() {
        return acquierId;
    }

    public void setAcquierId(String acquierId) {
        this.acquierId = acquierId;
    }

    public String getdDOL() {
        return dDOL;
    }

    public void setdDOL(String dDOL) {
        this.dDOL = dDOL;
    }

    public String gettDOL() {
        return tDOL;
    }

    public void settDOL(String tDOL) {
        this.tDOL = tDOL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRiskmanData() {
        return riskmanData;
    }

    public void setRiskmanData(String riskmanData) {
        this.riskmanData = riskmanData;
    }

    public String getKernelType() {
        return kernelType;
    }

    public void setKernelType(String kernelType) {
        this.kernelType = kernelType;
    }

    public String getRiskManData() {
        return riskManData;
    }

    public void setRiskManData(String riskManData) {
        this.riskManData = riskManData;
    }

    public String getTacDefault() {
        return tacDefault;
    }

    public void setTacDefault(String tacDefault) {
        this.tacDefault = tacDefault;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    public String getMerchCateCode() {
        return merchCateCode;
    }

    public void setMerchCateCode(String merchCateCode) {
        this.merchCateCode = merchCateCode;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTransCurrCode() {
        return transCurrCode;
    }

    public void setTransCurrCode(String transCurrCode) {
        this.transCurrCode = transCurrCode;
    }

    public int getTransCurrExp() {
        return transCurrExp;
    }

    public void setTransCurrExp(int transCurrExp) {
        this.transCurrExp = transCurrExp;
    }

    public String getReferCurrCode() {
        return referCurrCode;
    }

    public void setReferCurrCode(String referCurrCode) {
        this.referCurrCode = referCurrCode;
    }

    public int getReferCurrExp() {
        return referCurrExp;
    }

    public void setReferCurrExp(int referCurrExp) {
        this.referCurrExp = referCurrExp;
    }

    public int getReferCurrCon() {
        return referCurrCon;
    }

    public void setReferCurrCon(int referCurrCon) {
        this.referCurrCon = referCurrCon;
    }

    public boolean getIsAllowOnlinePIN() {
        return this.isAllowOnlinePIN;
    }

    public void setIsAllowOnlinePIN(boolean isAllowOnlinePIN) {
        this.isAllowOnlinePIN = isAllowOnlinePIN;
    }

    public boolean getRdClssFloorLimitFlg() {
        return this.rdClssFloorLimitFlg;
    }

    public boolean getRdClssTxnLimitFlg() {
        return this.rdClssTxnLimitFlg;
    }

    public boolean getRdCVMLimitFlg() {
        return this.rdCVMLimitFlg;
    }

    public boolean getFloorlimitFlg() {
        return this.floorlimitFlg;
    }

    public boolean getRandTransSel() {
        return this.randTransSel;
    }

    public boolean getVelocityCheck() {
        return this.velocityCheck;
    }

    public String getDDOL() {
        return this.dDOL;
    }

    public void setDDOL(String dDOL) {
        this.dDOL = dDOL;
    }

    public String getTDOL() {
        return this.tDOL;
    }

    public void setTDOL(String tDOL) {
        this.tDOL = tDOL;
    }
}
