package net.callofdroidy.sharedaccount;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by admin on 04/04/16.
 */
public class FragmentAddEntry extends DialogFragment {
    public static final String TAG = "fragmentAddEntry";

    EditText etAddTitle;
    EditText etAddDate;
    EditText etAddValue;
    Button btnCancel;
    Button btnAdd;

    Firebase ref;

    static FragmentAddEntry newInstance() {
        return new FragmentAddEntry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_entry, container, false);

        etAddTitle = (EditText) v.findViewById(R.id.et_add_title);
        etAddDate = (EditText) v.findViewById(R.id.et_add_date);
        etAddValue = (EditText) v.findViewById(R.id.et_add_value);
        btnAdd = (Button) v.findViewById(R.id.btn_add_add);
        btnCancel = (Button) v.findViewById(R.id.btn_add_cancel);

        ref = ((ActivityMain)getActivity()).refRoot;

        String date = new SimpleDateFormat("MMM dd yyyy", Locale.CANADA).format(new Date());
        etAddDate.setText(date);

        btnAdd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String title = etAddTitle.getText().toString();
                  String date = etAddDate.getText().toString();
                  String value = etAddValue.getText().toString();

                  String inputValidateResult = validateInput(v, title, date, value);

                  if (inputValidateResult.equals("OK")) {
                      //AccountEntry newEntry = new AccountEntry(title, date, value);
                      AccountEntry newEntry = new AccountEntry();
                      ref.push().setValue(newEntry);
                      /*
                      HashMap<String, String> total = new HashMap<String, String>();
                      float currentTotal = ((ActivityMain)getActivity()).currentTotal;
                      currentTotal += Float.parseFloat(value);
                      total.put("total", String.valueOf(currentTotal));
                      ref.child("total").setValue(total);
*/
                      FragmentManager fm = getActivity().getFragmentManager();
                      fm.beginTransaction().remove(FragmentAddEntry.this).commit();
                  } else
                      Snackbar.make(v, inputValidateResult, Snackbar.LENGTH_LONG).setAction("Action", null).show();
              }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                fm.beginTransaction().remove(FragmentAddEntry.this).commit();
            }
        });

        return v;
    }

    private String validateInput(View v, String title, String date, String value){
        if(title.equals("") || date.equals("") || value.equals(""))
            return "Blank Field(s) Not Allowed";
        else if(!date.matches("[A-Za-z]{3}\\s[0-9]{2}\\s[0-9]{4}")){
            return "Date is malformed";
        }else if(!value.matches("\\d+\\.?\\d{0,2}")){
            return "Value is malformed";
        }else
            return "OK";
    }
}
