package com.topwise.sdk.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.topwise.sdk.emv.daoutils.entity.uAid;
import com.topwise.sdk.emv.daoutils.entity.uCapk;

import com.topwise.sdk.dao.uAidDao;
import com.topwise.sdk.dao.uCapkDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig uAidDaoConfig;
    private final DaoConfig uCapkDaoConfig;

    private final uAidDao uAidDao;
    private final uCapkDao uCapkDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        uAidDaoConfig = daoConfigMap.get(uAidDao.class).clone();
        uAidDaoConfig.initIdentityScope(type);

        uCapkDaoConfig = daoConfigMap.get(uCapkDao.class).clone();
        uCapkDaoConfig.initIdentityScope(type);

        uAidDao = new uAidDao(uAidDaoConfig, this);
        uCapkDao = new uCapkDao(uCapkDaoConfig, this);

        registerDao(uAid.class, uAidDao);
        registerDao(uCapk.class, uCapkDao);
    }
    
    public void clear() {
        uAidDaoConfig.clearIdentityScope();
        uCapkDaoConfig.clearIdentityScope();
    }

    public uAidDao getUAidDao() {
        return uAidDao;
    }

    public uCapkDao getUCapkDao() {
        return uCapkDao;
    }

}
