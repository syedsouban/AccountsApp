package com.syedsauban.accountsapp;

/**
 * Created by Syed on 18-06-2017.
 */

public class Account {
    int id;
    private String AccountName,LinkedWith,EmailOrPhone;
    Account(String AccountName, String LinkedWith, String EmailOrPhone)
    {

        this.AccountName=AccountName;
        this.LinkedWith=LinkedWith;
        this.EmailOrPhone=EmailOrPhone;

    }

    public String getEmailOrPhone() {
        return EmailOrPhone;
    }

    public int getId() {
        return id;
    }

    public String getAccountName() {
        return AccountName;
    }

    public String getLinkedWith() {
        return LinkedWith;
    }



}
