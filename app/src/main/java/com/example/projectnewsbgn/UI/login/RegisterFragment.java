package com.example.projectnewsbgn.UI.login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.projectnewsbgn.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {

    private TextInputLayout accountName,email,psw,confirmPsw;
    private CheckBox rememberCb;
    private LinearProgressIndicator progressIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);

    }

    @Override
    public void onViewCreated(View OnCreateView,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getView();
        Button registerBtn = v.findViewById(R.id.registerBtn);
        rememberCb = v.findViewById(R.id.rememberCbRegister);
        accountName = v.findViewById(R.id.account);
        email = v.findViewById(R.id.email);
        psw = v.findViewById(R.id.psw);
        confirmPsw = v.findViewById(R.id.confirmPsw);
        progressIndicator = v.findViewById(R.id.progressIndicator);

       registerBtn.setOnClickListener(view -> {
           progressIndicator.setVisibility(View.VISIBLE);
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
                                prepareDataForRegister(true,accountString,emailString,pswString);
                                Navigation.findNavController(requireView())
                                        .navigate(R.id.action_registerFragment_to_selectionInterestFragment);
                            }
                            else {
                                prepareDataForRegister(false,accountString,emailString,pswString);
                                Navigation.findNavController(requireView())
                                        .navigate(R.id.action_registerFragment_to_selectionInterestFragment);
                            }

                        }
                        else {
                            progressIndicator.setVisibility(View.GONE);
                            confirmPsw.setError(getString(R.string.PasswordErrorMatch));
                        }
                        }
                    else {
                        progressIndicator.setVisibility(View.GONE);
                        psw.setError(getString(R.string.InvalidPassword2));
                    }
                    }
                else {
                    progressIndicator.setVisibility(View.GONE);
                    email.setError(getString(R.string.InvalidEmail2));
                }
                }
            else {
                progressIndicator.setVisibility(View.GONE);
                accountName.setError(getString(R.string.InvalidName));
            }
        });

    }

    private void prepareDataForRegister(boolean checked, String accountString, String emailString, String pswString) {
        Bundle rememberResult = new Bundle();
        rememberResult.putBoolean("booleankey", checked);
        rememberResult.putString("namekey",accountString);
        rememberResult.putString("emailkey",emailString);
        rememberResult.putString("pswkey",pswString);
        requireActivity().getSupportFragmentManager()
                .setFragmentResult("bundleKey", rememberResult);
    }

    private boolean comparePsw(String pswString, String pswStringConf) {
        return pswString.equals(pswStringConf);
    }

    private boolean controlPsw(String pswStringConf) {
        return !pswStringConf.equals("") && pswStringConf.length() >= 6;
    }

    private boolean controlEmailString(String emailString) {
        return !emailString.isEmpty() && Patterns.EMAIL_ADDRESS
                .matcher(emailString).matches();
    }

    private boolean controlAccountString(String accountString) {
        return !accountString.equals("");
    }
}