package cn.ucai.day07_02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by yao on 2016/4/19.
 */
public class UserDao extends SQLiteOpenHelper {
    static final String DB_NAME = "users.db";
    static final String TB_NAME = "user";
    static final String COL_ID = "id";
    static final String COL_NAME = "name";
    static final String COL_SEX = "sex";
    static final String COL_BIRTHDAY = "birthday";
    static final String COL_EMAIL = "email";

    public UserDao(Context context,int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TB_NAME + "("
                + COL_ID + " integer primary key autoincrement,"
                + COL_NAME + " varchar,"
                + COL_SEX + " varchar,"
                + COL_BIRTHDAY + " datetext,"
                + COL_EMAIL + " varchar)";
        db.execSQL(sql);
        ContentValues values = new ContentValues();
        values.put(COL_NAME, "张飞");
        values.put(COL_SEX, "男");
        values.put(COL_BIRTHDAY, "1989-7-7");
        values.put(COL_EMAIL, "zf@qq.com");
        db.insert(TB_NAME, null, values);

        values = new ContentValues();
        values.put(COL_NAME, "王菲");
        values.put(COL_SEX, "女");
        values.put(COL_BIRTHDAY, "1992-7-7");
        values.put(COL_EMAIL, "wf@qq.com");
        db.insert(TB_NAME, null, values);

        values = new ContentValues();
        values.put(COL_NAME, "刘亦菲");
        values.put(COL_SEX, "女");
        values.put(COL_BIRTHDAY, "1989-12-7");
        values.put(COL_EMAIL, "lyf@qq.com");
        db.insert(TB_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 插入一条记录
     * @param user：用户对象
     * @return 插入是否成功
     */
    public boolean insertUser(UserBean user) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME,user.getName());
        values.put(COL_SEX, user.getSex());
        values.put(COL_BIRTHDAY, user.getBirthday());
        values.put(COL_EMAIL, user.getEmail());
        final SQLiteDatabase db = getWritableDatabase();
        final long id = db.insert(TB_NAME, null, values);
        return id!=-1;
    }

    public ArrayList<UserBean> queryAll() {
        ArrayList<UserBean> list = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.query(TB_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String sex = cursor.getString(cursor.getColumnIndex(COL_SEX));
            String birthday = cursor.getString(cursor.getColumnIndex(COL_BIRTHDAY));
            String email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
            UserBean user = new UserBean(id, name, sex, birthday, email);
            list.add(user);
        }
        return list;
    }

    public ArrayList<UserBean> queryByName(String name2) {
        ArrayList<UserBean> list = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.query(TB_NAME, null, COL_NAME + " like ?", new String[]{"%" + name2 + "%"}, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String sex = cursor.getString(cursor.getColumnIndex(COL_SEX));
            String birthday = cursor.getString(cursor.getColumnIndex(COL_BIRTHDAY));
            String email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
            UserBean user = new UserBean(id, name, sex, birthday, email);
            list.add(user);
        }
        return list;
    }

    public boolean delUser(String name) {

        return true;
    }

    public boolean udpateUser(UserBean user) {

        return true;
    }
}
