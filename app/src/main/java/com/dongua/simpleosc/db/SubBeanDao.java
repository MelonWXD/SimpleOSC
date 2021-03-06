package com.dongua.simpleosc.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.dongua.simpleosc.bean.SubBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SUB_BEAN".
*/
public class SubBeanDao extends AbstractDao<SubBean, Long> {

    public static final String TABLENAME = "SUB_BEAN";

    /**
     * Properties of entity SubBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property DbID = new Property(0, Long.class, "dbID", true, "_id");
        public final static Property Id = new Property(1, int.class, "id", false, "ID");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property PubDate = new Property(3, String.class, "pubDate", false, "PUB_DATE");
        public final static Property PubDateLong = new Property(4, long.class, "pubDateLong", false, "PUB_DATE_LONG");
        public final static Property Title = new Property(5, String.class, "title", false, "TITLE");
        public final static Property Authorid = new Property(6, int.class, "authorid", false, "AUTHORID");
        public final static Property CommentCount = new Property(7, int.class, "commentCount", false, "COMMENT_COUNT");
        public final static Property Type = new Property(8, int.class, "type", false, "TYPE");
    }


    public SubBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SubBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SUB_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: dbID
                "\"ID\" INTEGER NOT NULL UNIQUE ," + // 1: id
                "\"AUTHOR\" TEXT," + // 2: author
                "\"PUB_DATE\" TEXT," + // 3: pubDate
                "\"PUB_DATE_LONG\" INTEGER NOT NULL ," + // 4: pubDateLong
                "\"TITLE\" TEXT," + // 5: title
                "\"AUTHORID\" INTEGER NOT NULL ," + // 6: authorid
                "\"COMMENT_COUNT\" INTEGER NOT NULL ," + // 7: commentCount
                "\"TYPE\" INTEGER NOT NULL );"); // 8: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SUB_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SubBean entity) {
        stmt.clearBindings();
 
        Long dbID = entity.getDbID();
        if (dbID != null) {
            stmt.bindLong(1, dbID);
        }
        stmt.bindLong(2, entity.getId());
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String pubDate = entity.getPubDate();
        if (pubDate != null) {
            stmt.bindString(4, pubDate);
        }
        stmt.bindLong(5, entity.getPubDateLong());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }
        stmt.bindLong(7, entity.getAuthorid());
        stmt.bindLong(8, entity.getCommentCount());
        stmt.bindLong(9, entity.getType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SubBean entity) {
        stmt.clearBindings();
 
        Long dbID = entity.getDbID();
        if (dbID != null) {
            stmt.bindLong(1, dbID);
        }
        stmt.bindLong(2, entity.getId());
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String pubDate = entity.getPubDate();
        if (pubDate != null) {
            stmt.bindString(4, pubDate);
        }
        stmt.bindLong(5, entity.getPubDateLong());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }
        stmt.bindLong(7, entity.getAuthorid());
        stmt.bindLong(8, entity.getCommentCount());
        stmt.bindLong(9, entity.getType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SubBean readEntity(Cursor cursor, int offset) {
        SubBean entity = new SubBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // dbID
            cursor.getInt(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // pubDate
            cursor.getLong(offset + 4), // pubDateLong
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // title
            cursor.getInt(offset + 6), // authorid
            cursor.getInt(offset + 7), // commentCount
            cursor.getInt(offset + 8) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SubBean entity, int offset) {
        entity.setDbID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.getInt(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPubDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPubDateLong(cursor.getLong(offset + 4));
        entity.setTitle(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAuthorid(cursor.getInt(offset + 6));
        entity.setCommentCount(cursor.getInt(offset + 7));
        entity.setType(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SubBean entity, long rowId) {
        entity.setDbID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SubBean entity) {
        if(entity != null) {
            return entity.getDbID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SubBean entity) {
        return entity.getDbID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
