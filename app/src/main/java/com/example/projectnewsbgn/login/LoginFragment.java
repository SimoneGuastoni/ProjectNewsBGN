package com.example.projectnewsbgn.login;

import static android.content.Context.MODE_PRIVATE;

import static com.example.projectnewsbgn.login.UserAccessActivity.COUNTRY;
import static com.example.projectnewsbgn.login.UserAccessActivity.SHARED_PREFS;
import static com.example.projectnewsbgn.login.UserAccessActivity.SHARED_PREFS_COUNTRY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.homepage.MainActivity;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment {

    private TextInputLayout email, psw;
    private CheckBox rememberCb;
    private Button loginBtn;
    private TextView forgotPswTxt, createAccountTxt;
    private ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
    RegisterFragment registerFragment = new RegisterFragment();

    public LoginFragment() {
        super(R.layout.fragment_login);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View onCreateView,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getView();
        email = v.findViewById(R.id.emailLayout);
        psw = v.findViewById(R.id.pswLayout);
        rememberCb = v.findViewById(R.id.rememberCb);
        loginBtn = v.findViewById(R.id.loginBtn);
        forgotPswTxt = v.findViewById(R.id.forgotPswText);
        createAccountTxt = v.findViewById(R.id.registerText);

        clearCountry();

        createAccountTxt.setOnClickListener(view -> {
            getView().findViewById(R.id.action_loginFragment_to_registerFragment);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ua_fragment_container_view, registerFragment).commit();

        });
        forgotPswTxt.setOnClickListener(view -> {
            getView().findViewById(R.id.action_loginFragment_to_forgotPasswordFragment);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ua_fragment_container_view, forgotPasswordFragment).commit();

        });

        loginBtn.setOnClickListener(view -> {
            String emailString = email.getEditText().getText().toString();
            String pswString = psw.getEditText().getText().toString();
            Boolean booleanEmail = false, booleanPsw = false;


            booleanEmail = checkEmail(emailString);
            booleanPsw = checkPsw(pswString);

            if (booleanEmail) {
                if (booleanPsw) {
                    if (rememberCb.isChecked()) {
                        Toast.makeText(getActivity(), "You will be remembered", Toast.LENGTH_SHORT).show();
                        Intent goToHome = new Intent(getActivity(), MainActivity.class);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", "true");
                        editor.apply();
                        startActivity(goToHome);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "You will not be remembered", Toast.LENGTH_SHORT).show();
                        Intent goToHome = new Intent(getActivity(), MainActivity.class);
                        startActivity(goToHome);
                        getActivity().finish();
                    }
                } else
                    psw.setError("Invalid Password");
            } else
                email.setError("Invalid E-mail");

        });

    }

    private void clearCountry() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_COUNTRY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COUNTRY,"it");
        editor.apply();
    }

        private boolean checkPsw (String pswString){
            if (pswString.equals(""))
                return false;
            else
                return true;
        }

        private boolean checkEmail (String emailString){
            if (!emailString.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailString).matches())
                return true;
            else
                return false;
        }

}