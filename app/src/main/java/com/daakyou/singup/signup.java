package com.daakyou.singup;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.daakyou.MainActivity;
import com.daakyou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class signup extends AppCompatActivity {
    EditText fullname,phone,password,otp;
    Button signup,register;
    TextView show,signin;
    String onbackpress="signup";
    private String mverificationid,code;
    FirebaseAuth ath;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    int i=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get ids
        fullname=(EditText)findViewById(R.id.fullname);
        phone=(EditText)findViewById(R.id.mobile);
        password=(EditText)findViewById(R.id.password);
        signup=(Button)findViewById(R.id.register);
        register=(Button)findViewById(R.id.signup);
        show=(TextView)findViewById(R.id.show);
        signin=(TextView)findViewById(R.id.signin);
        otp=(EditText)findViewById(R.id.otp);

        showall();

        ath=FirebaseAuth.getInstance();


    }

    public void actions(View view) {
        switch (view.getId())
        {
            case R.id.register :
                if(!checkvalidation(fullname,phone,password))
                {
                    sendVerificationCode(phone.getText().toString());
                    hideall();
                    i=1;
                }
                else 
                {
                    Toast.makeText(this, "You can't registerd", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.signup:
                if(!TextUtils.isEmpty(otp.getText().toString()))
                {
                    verifyverificationcode(otp.getText().toString());
                }
                break;
            case R.id.signin :

                break;

        }
    }

    private void hideall()
    {
        fullname.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);
        signup.setEnabled(false);
        show.setVisibility(View.GONE);
        signin.setVisibility(View.GONE);
        otp.setVisibility(View.VISIBLE);
        register.setEnabled(true);
        register.setVisibility(View.VISIBLE);


    }

    private void showall()
    {
        fullname.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);
        signup.setEnabled(true);
        show.setVisibility(View.VISIBLE);
        signin.setVisibility(View.VISIBLE);
        otp.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        register.setEnabled(false);

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
    public void onBackPressed() {
        if(onbackpress.equals("signup") && i==1)
        {
            showall();
            i=0;
        }
        else {
            super.onBackPressed();
        }

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //getting code by sms
            code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                otp.setText(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mverificationid = s;
            resendingToken = forceResendingToken;
        }
    };

    private void verifyverificationcode(String otp)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mverificationid,otp);

        // signing user
        signinwithcredentila(credential);
    }

    private void signinwithcredentila(PhoneAuthCredential credential) {
        ath.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Intent start=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(start);
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        Toast.makeText(getApplicationContext(), "Otp is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
