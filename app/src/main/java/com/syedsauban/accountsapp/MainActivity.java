package com.syedsauban.accountsapp;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Cursor AccountsCursor;
    AccountsDBHelper accountsDBHelper= new AccountsDBHelper(this);

    AccountsAdapter accountsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.myFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction("com.ADD");
                startActivity(intent);

            }
        });
        AccountsCursor= accountsDBHelper.getReadableDatabase().query(AccountsDBHelper.TABLE_NAME,null,null,null,null,null,null);
        accountsAdapter= new AccountsAdapter(this,AccountsCursor,0);
        ListView listView= (ListView)findViewById(R.id.list_view);
        View EmptyView=findViewById(R.id.emptyview);
        listView.setEmptyView(EmptyView);
        listView.setAdapter(accountsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name=(TextView)view.findViewById(R.id.name_textview);

                Intent intent=new Intent();
                intent.setAction("com.EDIT");
                intent.putExtra("position",position);
                intent.putExtra("name",name.getText());
                startActivity(intent);

            }

        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        AccountsCursor=accountsDBHelper.getReadableDatabase().query(AccountsDBHelper.TABLE_NAME,null,null,null,null,null,null);

        accountsAdapter.swapCursor(AccountsCursor);
        accountsAdapter.notifyDataSetChanged();
    }
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_search_menu, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {

                    AccountsDBHelper accountsDBHelper=new AccountsDBHelper(getApplicationContext());
                    Cursor cursor=accountsDBHelper.getReadableDatabase().rawQuery("SELECT * FROM AccountsTable WHERE AccountName LIKE '%"+s+"%' OR LinkedWith LIKE '%"+s+"%'",null);
                    accountsAdapter.swapCursor(cursor);
                    accountsAdapter.notifyDataSetChanged();





                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    AccountsDBHelper accountsDBHelper = new AccountsDBHelper(getApplicationContext());
                    Cursor cursor = accountsDBHelper.getReadableDatabase().rawQuery("SELECT * FROM AccountsTable WHERE AccountName LIKE '%"+s+"%' OR LinkedWith LIKE '%"+s+"%'",null);
                    accountsAdapter.swapCursor(cursor);
                    accountsAdapter.notifyDataSetChanged();

                    return false;
                }
            });

        }

        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.info:
                startActivity(new Intent(MainActivity.this,AboutTheApp.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void displayToastMessage(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
