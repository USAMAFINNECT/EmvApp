package com.topwise.sdk.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.topwise.sdk.emv.daoutils.entity.uCapk;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "U_CAPK".
*/
public class uCapkDao extends AbstractDao<uCapk, Long> {

    public static final String TABLENAME = "U_CAPK";

    /**
     * Properties of entity uCapk.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Ridindex = new Property(1, String.class, "ridindex", false, "RIDINDEX");
        public final static Property Rid = new Property(2, String.class, "rid", false, "RID");
        public final static Property Rindex = new Property(3, String.class, "rindex", false, "RINDEX");
        public final static Property HashInd = new Property(4, int.class, "hashInd", false, "HASH_IND");
        public final static Property ArithInd = new Property(5, int.class, "arithInd", false, "ARITH_IND");
        public final static Property Modul = new Property(6, String.class, "modul", false, "MODUL");
        public final static Property Exponent = new Property(7, String.class, "exponent", false, "EXPONENT");
        public final static Property ExpDate = new Property(8, String.class, "expDate", false, "EXP_DATE");
        public final static Property CheckSum = new Property(9, String.class, "checkSum", false, "CHECK_SUM");
    }


    public uCapkDao(DaoConfig config) {
        super(config);
    }
    
    public uCapkDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"U_CAPK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"RIDINDEX\" TEXT UNIQUE ," + // 1: ridindex
                "\"RID\" TEXT," + // 2: rid
                "\"RINDEX\" TEXT," + // 3: rindex
                "\"HASH_IND\" INTEGER NOT NULL ," + // 4: hashInd
                "\"ARITH_IND\" INTEGER NOT NULL ," + // 5: arithInd
                "\"MODUL\" TEXT," + // 6: modul
                "\"EXPONENT\" TEXT," + // 7: exponent
                "\"EXP_DATE\" TEXT," + // 8: expDate
                "\"CHECK_SUM\" TEXT);"); // 9: checkSum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"U_CAPK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, uCapk entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String ridindex = entity.getRidindex();
        if (ridindex != null) {
            stmt.bindString(2, ridindex);
        }
 
        String rid = entity.getRid();
        if (rid != null) {
            stmt.bindString(3, rid);
        }
 
        String rindex = entity.getRindex();
        if (rindex != null) {
            stmt.bindString(4, rindex);
        }
        stmt.bindLong(5, entity.getHashInd());
        stmt.bindLong(6, entity.getArithInd());
 
        String modul = entity.getModul();
        if (modul != null) {
            stmt.bindString(7, modul);
        }
 
        String exponent = entity.getExponent();
        if (exponent != null) {
            stmt.bindString(8, exponent);
        }
 
        String expDate = entity.getExpDate();
        if (expDate != null) {
            stmt.bindString(9, expDate);
        }
 
        String checkSum = entity.getCheckSum();
        if (checkSum != null) {
            stmt.bindString(10, checkSum);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, uCapk entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String ridindex = entity.getRidindex();
        if (ridindex != null) {
            stmt.bindString(2, ridindex);
        }
 
        String rid = entity.getRid();
        if (rid != null) {
            stmt.bindString(3, rid);
        }
 
        String rindex = entity.getRindex();
        if (rindex != null) {
            stmt.bindString(4, rindex);
        }
        stmt.bindLong(5, entity.getHashInd());
        stmt.bindLong(6, entity.getArithInd());
 
        String modul = entity.getModul();
        if (modul != null) {
            stmt.bindString(7, modul);
        }
 
        String exponent = entity.getExponent();
        if (exponent != null) {
            stmt.bindString(8, exponent);
        }
 
        String expDate = entity.getExpDate();
        if (expDate != null) {
            stmt.bindString(9, expDate);
        }
 
        String checkSum = entity.getCheckSum();
        if (checkSum != null) {
            stmt.bindString(10, checkSum);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public uCapk readEntity(Cursor cursor, int offset) {
        uCapk entity = new uCapk( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ridindex
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // rid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // rindex
            cursor.getInt(offset + 4), // hashInd
            cursor.getInt(offset + 5), // arithInd
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // modul
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // exponent
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // expDate
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // checkSum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, uCapk entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRidindex(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRindex(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHashInd(cursor.getInt(offset + 4));
        entity.setArithInd(cursor.getInt(offset + 5));
        entity.setModul(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setExponent(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setExpDate(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCheckSum(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(uCapk entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(uCapk entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(uCapk entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
