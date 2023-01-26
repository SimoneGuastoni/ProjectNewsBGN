package com.example.projectnewsbgn.UI.login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Repository.AccountReposiroty.IAccountRepositoryWithLiveData;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {

    private TextInputLayout accountName,email,psw,confirmPsw;
    private Button registerBtn;
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
        registerBtn = v.findViewById(R.id.registerBtn);
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
                            confirmPsw.setError("Passwords doesn't match");
                        }
                        }
                    else {
                        progressIndicator.setVisibility(View.GONE);
                        psw.setError("Invalid password");
                    }
                    }
                else {
                    progressIndicator.setVisibility(View.GONE);
                    email.setError("Invalid email");
                }
                }
            else {
                progressIndicator.setVisibility(View.GONE);
                accountName.setError("Invalid account name");
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
        if (pswString.equals(pswStringConf))
            return true;
        else
            return false;
    }

    private boolean controlPsw(String pswStringConf) {
        if (pswStringConf.equals("") || pswStringConf.length()<6)
            return false;
        else
            return true;
    }

    private boolean controlEmailString(String emailString) {
        if (!emailString.isEmpty() && Patterns.EMAIL_ADDRESS
                .matcher(emailString).matches())
            return true;
        else
            return false;
    }

    private boolean controlAccountString(String accountString) {
        if(accountString.equals(""))
            return false;
        else
            return true;
    }
}