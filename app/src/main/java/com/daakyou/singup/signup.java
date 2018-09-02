package com.daakyou.singup;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

import com.daakyou.R;

public class signup extends AppCompatActivity implements verifyotp.OnFragmentInteractionListener {
    EditText fullname,phone,password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get ids
        fullname=(EditText)findViewById(R.id.fullname);
        phone=(EditText)findViewById(R.id.mobile);
        password=(EditText)findViewById(R.id.password);




    }

    public void actions(View view) {
        switch (view.getId())
        {
            case R.id.register :
                if(!checkvalidation(fullname,phone,password))
                {
                    Bundle sendcredential=new Bundle();
                    sendcredential.putString("fullname",fullname.getText().toString());
                    sendcredential.putString("phone",phone.getText().toString());
                    sendcredential.putString("password",password.getText().toString());
                    verifyotp frag=new verifyotp();
                    frag.setArguments(sendcredential);
                  getSupportFragmentManager().beginTransaction().replace(R.id.container,frag).commit();
                }
                else 
                {
                    Toast.makeText(this, "You can't registerd", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signin :

                break;

        }
    }

    public boolean checkvalidation(EditText fullname, EditText Phone, EditText password)
    {
      String name=fullname.getText().toString();
      String phone=Phone.getText().toString();
      String passcode=password.getText().toString();
      Boolean empty=true;
      if(TextUtils.isEmpty(name))
      {
          fullname.setError("Name can't be blank");
          fullname.requestFocus();
      }

      else if(TextUtils.isEmpty(phone))
      {
          Phone.setError("Phone No can't be blank");
          Phone.requestFocus();
      }

      else if(TextUtils.isEmpty(passcode))
      {
          password.setError("Password can't be blank");
          password.requestFocus();
      }


      else
      {
         if(phone.length()>10 || phone.length()<10)
         {
             Phone.setError("Enter a valid number ");
             Phone.requestFocus();
         }

         else if(passcode.length()<6 || passcode.length()>15)
         {
             password.setError("Password should be between 6-15 characters");
             password.requestFocus();
         }
         else {
             empty = false;
         }
      }
      return empty;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
