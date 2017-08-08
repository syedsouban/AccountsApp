package com.syedsauban.accountsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddEditActivity extends AppCompatActivity {


    EditText NameEditText;
    EditText LinkedWithEditText;
    EditText EmailOrPhoneEditText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.delete,menu);
        if(getIntent().getAction()=="com.EDIT")return true;
        else return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.deletebutton:

                AccountsDBHelper accountsDBHelper=new AccountsDBHelper(getApplicationContext());
                String name=getIntent().getStringExtra("name");
                int _id=accountsDBHelper.get_idByName(name);

                int rowsDeleted=accountsDBHelper.getWritableDatabase().delete(AccountsDBHelper.TABLE_NAME,"_id=?",new String[]{""+_id});
                if(rowsDeleted==1)
                {
                    displayToastMessage("Account successfully deleted");
                    startActivity(new Intent(AddEditActivity.this,MainActivity.class));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add);

        Button Sbutton =(Button)findViewById(R.id.savebutton);
        NameEditText=(EditText)findViewById(R.id.name_edittext);
        LinkedWithEditText=(EditText)findViewById(R.id.linked_with_edittext);
        EmailOrPhoneEditText=(EditText)findViewById(R.id.email_address_edittext);
        Intent intent=getIntent();
        final String currentAction=intent.getAction();
        String ButtonString="";
        Sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account newAccount;
                String Name=NameEditText.getText().toString().trim();
                String LinkedWith=LinkedWithEditText.getText().toString().trim();
                String EmailOrPhone=EmailOrPhoneEditText.getText().toString().trim();
                if(Name.equals("")||LinkedWith.equals("")||EmailOrPhone.equals(""))
                {
                    displayToastMessage("One of the fields is empty, Please try again");
                    return;
                }

                newAccount=new Account(Name,LinkedWith,EmailOrPhone);
                AccountsDBHelper dbHelper=new AccountsDBHelper(getApplicationContext());

                if(currentAction=="com.ADD")
                {
                    dbHelper.addAccount(newAccount);
                        startActivity(new Intent(AddEditActivity.this,MainActivity.class));
                }
                else if(currentAction=="com.EDIT") {
                    String name=getIntent().getStringExtra("name");
                    int _id=dbHelper.get_idByName(name);

                    ContentValues AccountValues = new ContentValues();
                    AccountValues.put(AccountsDBHelper.AccName,Name);
                    AccountValues.put(AccountsDBHelper.LinkedWith,LinkedWith);
                    AccountValues.put(AccountsDBHelper.EmailOrPhone,EmailOrPhone);
                    int rowsAffected=dbHelper.getWritableDatabase().update(AccountsDBHelper.TABLE_NAME,AccountValues,"_id=?",new String[]{""+_id});
                    if(rowsAffected>0) {
                        displayToastMessage("Account successfully updated");
                        startActivity(new Intent(AddEditActivity.this,MainActivity.class));
                    }
                }
                }
        });
        if(currentAction=="com.ADD")
        {
            ButtonString="ADD";
            setTitle("Add an Account");
            Sbutton.setText(ButtonString);
        }
        else if(currentAction=="com.EDIT")
        {

            setTitle("Edit an Account");
            Sbutton.setText("UPDATE");
            String name=getIntent().getStringExtra("name");


            AccountsDBHelper accountsDBHelper= new AccountsDBHelper(AddEditActivity.this);
            int _id=accountsDBHelper.get_idByName(name);
            Cursor cursor=accountsDBHelper.getReadableDatabase().rawQuery("SELECT * FROM "+AccountsDBHelper.TABLE_NAME+" WHERE " +
                    "_id=?",new String[]{""+_id});


                if (cursor != null && cursor.moveToFirst()) {
                    NameEditText.setText(cursor.getString(1));
                    LinkedWithEditText.setText(cursor.getString(2));
                    EmailOrPhoneEditText.setText(cursor.getString(3));
                }
                else
                    displayToastMessage("Cursor is null ;(");
        }


    }
    public void displayToastMessage(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
