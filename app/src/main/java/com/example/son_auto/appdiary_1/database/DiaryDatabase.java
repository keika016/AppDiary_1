package com.example.son_auto.appdiary_1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.son_auto.appdiary_1.model.PageDiary;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 3/5/2018.
 */

public class DiaryDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbdiary_name";
    public static final String TABLE_NAME = "tbdiary_name";

    private static final String DIARY_ID = "tbdiary_id";
    private static final String DIARY_EMOTION = "tbdiary_emotion";
    private static final String DIARY_BACKGROUND = "tbdiary_background";
    private static final String DIARY_EDITTEXTBACKGROUND = "tbdiary_edittext_background";
    private static final String DIARY_DATETIME = "tbdiary_datetime";

    private static final String DIARY_CONTENT = "tbdiary_content";
    private static final String DIARY_FONT = "tbdiary_font";
    private static final String DIARY_STYLE = "tbdiary_style";
    private static final String DIARY_COLOR = "tbdiary_color";
    private static final String DIARY_SIZE = "tbdiary_size";
    private static final String DIARY_POSITION = "tbdiary_position";

    private Context context;

    public DiaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + " (" +
                DIARY_ID + " integer primary key autoincrement, " +
                DIARY_EMOTION + " text," +
                DIARY_BACKGROUND + " text," +
                DIARY_EDITTEXTBACKGROUND + " text," +
                DIARY_DATETIME + " text," +
                DIARY_CONTENT + " text," +
                DIARY_FONT + " text," +
                DIARY_STYLE + " text," +
                DIARY_COLOR + " text," +
                DIARY_SIZE + " text," +
                DIARY_POSITION + " text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addDiary(PageDiary pageDiary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DIARY_EMOTION, pageDiary.getEmotion());
        values.put(DIARY_BACKGROUND, pageDiary.getBackground());
        values.put(DIARY_EDITTEXTBACKGROUND, pageDiary.getEditTextBackGround());
        values.put(DIARY_DATETIME, pageDiary.getDateTime());
        values.put(DIARY_CONTENT, pageDiary.getContent());
        values.put(DIARY_FONT, pageDiary.getFont());
        values.put(DIARY_STYLE, pageDiary.getStyle());
        values.put(DIARY_COLOR, pageDiary.getColor());
        values.put(DIARY_SIZE, pageDiary.getSize());
        values.put(DIARY_POSITION, pageDiary.getPosition());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateDiary(PageDiary pageDiary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DIARY_EMOTION, pageDiary.getEmotion());
        values.put(DIARY_BACKGROUND, pageDiary.getBackground());
        values.put(DIARY_EDITTEXTBACKGROUND, pageDiary.getEditTextBackGround());
        values.put(DIARY_DATETIME, pageDiary.getDateTime());
        values.put(DIARY_CONTENT, pageDiary.getContent());
        values.put(DIARY_FONT, pageDiary.getFont());
        values.put(DIARY_STYLE, pageDiary.getStyle());
        values.put(DIARY_COLOR, pageDiary.getColor());
        values.put(DIARY_SIZE, pageDiary.getSize());
        values.put(DIARY_POSITION, pageDiary.getPosition());

        db.update(TABLE_NAME, values, DIARY_ID + "=?", new String[]{pageDiary.getId() + ""});
        db.close();
    }

    public void xoaPageDiary(int idPageDiary) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, DIARY_ID + " =? ", new String[]{idPageDiary + ""});
        db.close();
    }


    public ArrayList<PageDiary> getAllPageDiary() {
        ArrayList<PageDiary> listPage = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.isBeforeFirst()) {
            cursor.close();
            db.close();
            return null;//cửa hàng không tồn tại
        } else {
            do {
                PageDiary p = new PageDiary();
                p.setId(Integer.parseInt(cursor.getString(0)));
                p.setEmotion(cursor.getString(1));
                p.setBackground(cursor.getString(2));
                p.setEditTextBackGround(cursor.getString(3));
                p.setDateTime(cursor.getString(4));
                p.setContent(cursor.getString(5));

                p.setFont(cursor.getString(6));
                p.setStyle(cursor.getString(7));
                p.setColor(cursor.getString(8));
                p.setSize(cursor.getString(9));
                p.setPosition(cursor.getString(10));

                listPage.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listPage;
    }

    public boolean checkDBHaveItem() {
        boolean b = false;
        String query = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.isBeforeFirst()) {
            //cửa hàng không tồn tại
        } else {
            b = true;
        }
        cursor.close();
        db.close();
        return b;
    }
}
