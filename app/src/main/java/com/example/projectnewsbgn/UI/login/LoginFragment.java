package com.example.projectnewsbgn.UI.login;

import static android.content.Context.MODE_PRIVATE;

import static com.example.projectnewsbgn.UI.login.UserAccessActivity.ACCOUNT;
import static com.example.projectnewsbgn.UI.login.UserAccessActivity.SHARED_PREFS;
import static com.example.projectnewsbgn.UI.login.UserAccessActivity.SHARED_PREFS_ACCOUNT;

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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Repository.AccountReposiroty.IAccountRepositoryWithLiveData;
import com.example.projectnewsbgn.UI.homepage.MainActivity;
import com.example.projectnewsbgn.Utility.ServiceLocator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


public class LoginFragment extends Fragment {

    private TextInputLayout email, psw;
    private CheckBox rememberCb;
    private Button loginBtn;
    private TextView forgotPswTxt, createAccountTxt;
    private LinearProgressIndicator progressBar;
    private AccountViewModel accountViewModel;
    private IAccountRepositoryWithLiveData accountRepository;
    private MutableLiveData<Result> accountMutableLiveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountRepository = ServiceLocator.getInstance().getAccountRepository
                (requireActivity().getApplication());

        accountViewModel = new ViewModelProvider(requireActivity(),
                new AccountViewModelFactory(accountRepository)).get(AccountViewModel.class);

    }

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
        progressBar = v.findViewById(R.id.progressIndicator);

        clearAccount();

        createAccountTxt.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });

        forgotPswTxt.setOnClickListener(view -> {
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
        });

        loginBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String emailString = email.getEditText().getText().toString();
            String pswString = psw.getEditText().getText().toString();
            Boolean booleanEmail = false, booleanPsw = false;

            booleanEmail = checkEmail(emailString);
            booleanPsw = checkPsw(pswString);

            if (booleanEmail) {
                if (booleanPsw) {
                    accountMutableLiveData = accountViewModel.authentication("",emailString,pswString,"",null);
                    accountMutableLiveData.observe(
                            getViewLifecycleOwner(), result -> {
                                if(result.isSuccess()){
                                    Account account = ((Result.AccountSuccess) result).getData();
                                    saveAccountLocal(account);
                                    if (rememberCb.isChecked()) {
                                        Toast.makeText(getActivity(), "You will be remembered", Toast.LENGTH_SHORT).show();
                                        Intent goToHome = new Intent(getActivity(), MainActivity.class);
                                        SharedPreferences sharedPreferences = getActivity()
                                                .getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
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
                                }
                                else{
                                    accountMutableLiveData = null;
                                    Toast.makeText(getActivity(),result.getClass().toString(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                } else
                    psw.setError("Invalid Password");
            } else
                email.setError("Invalid E-mail");

        });

    }

    private void saveAccountLocal(Account account) {
        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(SHARED_PREFS_ACCOUNT,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(account);
        editor.putString(ACCOUNT,json);
        editor.apply();
    }

    private void clearAccount() {
        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(SHARED_PREFS_ACCOUNT,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

        private boolean checkPsw (String pswString){
            if (pswString.equals("") || pswString.length()<6)
                return false;
            else
                return true;
        }

        private boolean checkEmail (String emailString){
            if (!emailString.isEmpty() && Patterns.EMAIL_ADDRESS
                    .matcher(emailString).matches())
                return true;
            else
                return false;
        }

}