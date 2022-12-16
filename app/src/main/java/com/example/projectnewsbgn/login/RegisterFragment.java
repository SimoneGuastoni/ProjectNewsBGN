package com.example.projectnewsbgn.login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.object.Account;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);

    }
    private TextInputLayout accountName,email,psw,confirmPsw;
    private Button registerBtn;
    private ImageView profilePic;
    private CheckBox rememberCb;
    public static final String SHARED_PREFS ="SharedPrefs";
    public final FragmentActivity act = getActivity();





    @Override
    public void onViewCreated(View OnCreateView,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_register);
        View v = getView();
        profilePic = v.findViewById(R.id.profileIcon);
        registerBtn = v.findViewById(R.id.registerBtn);
        rememberCb = v.findViewById(R.id.rememberCbRegister);
        accountName = v.findViewById(R.id.account);
        email = v.findViewById(R.id.email);
        psw = v.findViewById(R.id.psw);
        confirmPsw = v.findViewById(R.id.confirmPsw);

// forzare l'activityLauncher per gestire le immagini su un fragment è probabilemnte una
// cattiva idea
        /*profilePic.setOnClickListener(view -> {
            UserAccessActivity.mGetContent.launch("image/*");
        });*/

       registerBtn.setOnClickListener(view -> {
            String emailString,pswString,pswStringConf,accountString;

            accountString = accountName.getEditText().getText().toString();
            emailString = email.getEditText().getText().toString();
            pswString = psw.getEditText().getText().toString();
            pswStringConf = confirmPsw.getEditText().getText().toString();

            if(controlAccountString(accountString)){
                if (controlEmailString(emailString)){
                    if(controlPsw(pswString) && controlPsw(pswStringConf)){
                        if (comparePsw(pswString,pswStringConf)) {
                            if (rememberCb.isChecked()) {
                                Toast.makeText(act, "You will be remembered", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = act.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name","true");
                                editor.apply();
                                Intent goToSelectInterest = new Intent(act, SelectionInterestActivity.class);
                                Account account = new Account(accountString,emailString,pswString,"",false,false,false);
                                goToSelectInterest.putExtra("user",account);
                                /* goToSelectInterest.putExtra("name",accountString);
                                goToSelectInterest.putExtra("email",emailString);
                                goToSelectInterest.putExtra("psw",pswString);
                                goToSelectInterest.putExtra("icon",profilePic.get);*/
                                startActivity(goToSelectInterest);
                                act.finish();
                            } else {
                                Toast.makeText(act, "You will not be remembered", Toast.LENGTH_SHORT).show();
                                Intent goToSelectInterest = new Intent(act, SelectionInterestActivity.class);
                                Account account = new Account(accountString,emailString,pswString,"",false,false,false);
                                goToSelectInterest.putExtra("user",account);
                                startActivity(goToSelectInterest);
                                act.finish();
                            }
                        }
                        else
                            confirmPsw.setError("Passwords doesn't match");
                        }
                    else
                        psw.setError("Invalid password");
                    }
                else
                    email.setError("Invalid email");
                }
            else
                accountName.setError("Invalid account name");
        });

    }

    private boolean comparePsw(String pswString, String pswStringConf) {
        if (pswString.equals(pswStringConf))
            return true;
        else
            return false;
    }

    private boolean controlPsw(String pswStringConf) {
        if (pswStringConf.equals(""))
            return false;
        else
            return true;
    }

    private boolean controlEmailString(String emailString) {
        if(emailString.equals(""))
            return false;
        else
            return true;
    }

    private boolean controlAccountString(String accountString) {
        if(accountString.equals(""))
            return false;
        else
            return true;
    }
}