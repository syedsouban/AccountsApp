package com.syedsauban.accountsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syed on 18-06-2017.
 */

public class AccountsDBHelper extends SQLiteOpenHelper {


    public static final String TABLE_NAME="AccountsTable";
    public static final String id="_id";
    public static final String AccName="AccountName";
    public static final String LinkedWith="LinkedWith";
    public static final String EmailOrPhone="EmailOrPhone";
        Context mcontext;
    public AccountsDBHelper(Context context) {

        super(context, TABLE_NAME, null, 1);
        mcontext=context;
    }

    public void onCreate(SQLiteDatabase db) {
        String CreateTableCommand="CREATE TABLE " +TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+AccName+" TEXT,"
                +LinkedWith+" TEXT,"+EmailOrPhone+" TEXT DEFAULT NULL)";
            db.execSQL(CreateTableCommand);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE "+TABLE_NAME);
        onCreate(db);
    }
    public long addAccount(Account account)
    {
        int id=account.getId();
        String AccName=account.getAccountName();
        String LinkedWith=account.getLinkedWith();
        String EmailOrPhone=account.getEmailOrPhone();
        SQLiteDatabase AccountsDB=this.getWritableDatabase();
        ContentValues AccountDetails=new ContentValues();
        AccountDetails.put(this.AccName,AccName);
        AccountDetails.put(this.LinkedWith,LinkedWith);
        AccountDetails.put(this.EmailOrPhone,EmailOrPhone);
        long ROWID=AccountsDB.insert(this.TABLE_NAME,null,AccountDetails);
            return ROWID;
    }
    public int get_idByName(String name)
    {
        name=name.trim();
        AccountsDBHelper accountsDBHelper=new AccountsDBHelper(mcontext);
        Cursor cursor=accountsDBHelper.getReadableDatabase().rawQuery("SELECT * FROM "+AccountsDBHelper.TABLE_NAME+" WHERE AccountName=?",
                new String[]{name});
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
