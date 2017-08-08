package com.syedsauban.accountsapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syed on 18-06-2017.
 */

public class AccountsAdapter extends CursorAdapter {

    Context RContext;
    LayoutInflater mInflater;
    public AccountsAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
        RContext=context;
        mInflater=LayoutInflater.from(this.RContext);
        }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        AccountsViewHolder accountsViewHolder=new AccountsViewHolder();
        View listItemView;


            listItemView=mInflater.inflate(R.layout.list_item,parent,false);
            accountsViewHolder.NameOfAccount=(TextView) listItemView.findViewById(R.id.name_textview);
            accountsViewHolder.LinkedWith=(TextView)listItemView.findViewById(R.id.linkedwith_textview);
            accountsViewHolder.EmailOrPhone=(TextView)listItemView.findViewById(R.id.email_or_phone_textview);
            accountsViewHolder.Shortform=(TextView)listItemView.findViewById(R.id.shortform);
            listItemView.setTag(accountsViewHolder);
            return listItemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        AccountsViewHolder accountsViewHolder=(AccountsViewHolder)view.getTag();
        if(cursor.getPosition()%2!=0) {
            view.setBackgroundResource(R.drawable.listitemviewbg2);
            accountsViewHolder.NameOfAccount.setTextColor(Color.parseColor("#000000"));
            accountsViewHolder.LinkedWith.setTextColor(Color.parseColor("#000000"));
            accountsViewHolder.EmailOrPhone.setTextColor(Color.parseColor("#000000"));
        }
            else {
            accountsViewHolder.NameOfAccount.setTextColor(Color.parseColor("#FFFFFF"));
            accountsViewHolder.LinkedWith.setTextColor(Color.parseColor("#FFFFFF"));
            accountsViewHolder.EmailOrPhone.setTextColor(Color.parseColor("#FFFFFF"));
            view.setBackgroundResource(R.drawable.listitemviewbg);
        }

       accountsViewHolder.NameOfAccount.setText(cursor.getString(1));
        accountsViewHolder.LinkedWith.setText(cursor.getString(2));
        accountsViewHolder.EmailOrPhone.setText(cursor.getString(3));

        char short_char=cursor.getString(1).charAt(0);
        String shortString=""+short_char;
        accountsViewHolder.Shortform.setText(shortString);
    }


}
