package net.callofdroidy.sharedaccount;

/**
 * Created by admin on 04/04/16.
 */
public class AccountEntry {
    private String title;
    private String date;
    private String value;

    /*
    public AccountEntry(String title, String date, String value){
        this.title = title;
        this.date = date;
        this.value = value;
    }
    */

    public AccountEntry(){}

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getValue(){
        return value;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String data){
        this.date = data;
    }

    public void setValue(String value){
        this.value = value;
    }
}
