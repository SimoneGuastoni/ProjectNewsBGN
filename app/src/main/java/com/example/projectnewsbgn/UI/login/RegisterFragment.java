package com.example.projectnewsbgn.UI.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.projectnewsbgn.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {

    private TextInputLayout accountName,email,psw,confirmPsw;
    private Button registerBtn;
    private ImageView profilePic;
    private CheckBox rememberCb;
    public final FragmentActivity act = getActivity();
    private boolean checked = false;

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
        profilePic = v.findViewById(R.id.profileIcon);
        registerBtn = v.findViewById(R.id.registerBtn);
        rememberCb = v.findViewById(R.id.rememberCbRegister);
        accountName = v.findViewById(R.id.account);
        email = v.findViewById(R.id.email);
        psw = v.findViewById(R.id.psw);
        confirmPsw = v.findViewById(R.id.confirmPsw);

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
                                checked = true;
                                Bundle result = new Bundle();
                                result.putBoolean("booleankey", checked);
                                requireActivity().getSupportFragmentManager()
                                        .setFragmentResult("bundleKey", result);
                            }
                            Navigation.findNavController(requireView())
                                    .navigate(R.id.action_registerFragment_to_selectionInterestFragment);
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