package com.topwise.sdk.emv.daoutils;

/**
 * 作者：  on 2021/3/28 22:25
 */

import com.topwise.sdk.dao.uAidDao;
import com.topwise.sdk.dao.uCapkDao;
import com.topwise.sdk.emv.daoutils.entity.uAid;
import com.topwise.sdk.emv.daoutils.entity.uCapk;

/**
 * 初始化、存放及获取DaoUtils
 */
public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance = new DaoUtilsStore();

    private CommonDaoUtils<uAid> mUAidDaoUtils;
    private CommonDaoUtils<uCapk> mUCapkDaoUtils;

    public static DaoUtilsStore getInstance() {
        return instance;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();

        //aid
        uAidDao _aidDao = mManager.getDaoSession().getUAidDao();
        mUAidDaoUtils = new CommonDaoUtils<>(uAid.class, _aidDao);

        //capk
        uCapkDao _capkDao = mManager.getDaoSession().getUCapkDao();
        mUCapkDaoUtils = new CommonDaoUtils<>(uCapk.class, _capkDao);

    }

    public CommonDaoUtils<uAid> getmUAidDaoUtils() {
        return mUAidDaoUtils;
    }

    public CommonDaoUtils<uCapk> getmUCapkDaoUtils() {
        return mUCapkDaoUtils;
    }
}
