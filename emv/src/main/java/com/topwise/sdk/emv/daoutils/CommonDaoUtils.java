package com.topwise.sdk.emv.daoutils;


import android.database.Cursor;


import com.topwise.sdk.dao.DaoSession;
import com.topwise.sdk.dao.uAidDao;
import com.topwise.sdk.dao.uCapkDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2021/3/28 22:24
 */
public class CommonDaoUtils<T> {

    private DaoSession mDaoSession;
    private Class<T> entityClass;
    private AbstractDao<T, Long> entityDao;

    public CommonDaoUtils(Class<T> pEntityClass, AbstractDao<T, Long> pEntityDao) {
        DaoManager mManager = DaoManager.getInstance();
        mDaoSession = mManager.getDaoSession();
        entityClass = pEntityClass;
        entityDao = pEntityDao;
    }

    /**
     * 插入记录，如果表未创建，先创建表
     */
    public boolean save(T pEntity) {
        return entityDao.insert(pEntity) != -1;
    }

    /**
     * 插入多条数据，在子线程操作
     */
    public boolean insertMultiple(final List<T> pEntityList) {
        try {
            mDaoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T entity : pEntityList) {
                        mDaoSession.insertOrReplace(entity);
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改一条数据
     */
    public boolean update(T entity) {
        try {
            mDaoSession.update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除单条记录
     */
    public boolean delete(T entity) {
        try {
            //按照id删除
            mDaoSession.delete(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除所有记录
     */
    public boolean deleteAll() {
        try {
            //按照id删除
            mDaoSession.deleteAll(entityClass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * group by
     * Use group query
     * @return
     */
    public List<String[]> getTransInfoGroupByTransType(){

        String sql = "select count(_id),sum(amount),TRANS_TYPE,TRANS_STATE from TRANS_DATA group by TRANS_TYPE ,TRANS_STATE";
        Cursor cursor = mDaoSession.getDatabase().rawQuery(sql, null);
        String[] obj = null;
        List<String[]> result = new ArrayList<String[]>();
        while (cursor.moveToNext()) {
            obj = new String[4];
            obj[0] = cursor.getString(0);
            obj[1] = cursor.getString(1)!= null ? cursor.getString(1) : "0";
            obj[2] = cursor.getString(2);
            obj[3] = cursor.getString(3);


            result.add(obj);
        }
        return result;

    }

    /**
     * 根据主键id查询记录
     */
    public T queryById(long key) {
        return mDaoSession.load(entityClass, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<T> queryByNativeSql(String sql, String[] conditions) {
        return mDaoSession.queryRaw(entityClass, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     */
    public List<T> queryByQueryBuilder(WhereCondition cond, WhereCondition... condMore) {
        QueryBuilder<T> queryBuilder = mDaoSession.queryBuilder(entityClass);
        return queryBuilder.where(cond, condMore).list();
    }


    public T queryByAid(Class<T> entityClass, String vale){
        Query<T> build = mDaoSession.queryBuilder(entityClass).where(uAidDao.Properties.Aid.eq(vale)).build();
        return build.unique();
    }
    public T queryByCapkRid(Class<T> entityClass, String vale, String index){
        Query<T> build = mDaoSession.queryBuilder(entityClass).where(uCapkDao.Properties.Rid.eq(vale) , uCapkDao.Properties.Rindex.eq(index)).build();
        return build.unique();
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    /**
     * 查询所有记录
     */
    public List<T> queryAll() {
        return mDaoSession.loadAll(entityClass);
    }
}