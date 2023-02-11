package com.example.projectnewsbgn.UI.login;

import androidx.fragment.app.Fragment;
import com.example.projectnewsbgn.R;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.projectnewsbgn.Repository.AccountRepository.IAccountRepositoryWithLiveData;
import com.example.projectnewsbgn.Utility.ServiceLocator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;



public class ForgotPasswordFragment extends Fragment {
    private TextInputLayout emailAddress;
    private LinearProgressIndicator progressIndicator;
    private AccountViewModel accountViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IAccountRepositoryWithLiveData accountRepository = ServiceLocator.getInstance().getAccountRepository
                ();
        accountViewModel = new ViewModelProvider(requireActivity(),
                new AccountViewModelFactory(accountRepository)).get(AccountViewModel.class);

    }



    public ForgotPasswordFragment(){
        super(R.layout.fragment_forgot_password);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);

    }

    @Override
    public void onViewCreated(View OnCreateView,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        emailAddress = v.findViewById(R.id.emailLayout);
        Button sendBTN = v.findViewById((R.id.sendEmailBtn));
        progressIndicator = v.findViewById(R.id.progressIndicator);
        sendBTN.setOnClickListener(view -> {
            progressIndicator.setVisibility(View.VISIBLE);
            String emailString;
            emailString = emailAddress.getEditText().getText().toString();
            if (controlEmailString(emailString))
            {
                prepareDataForRegister(true, emailString);
                accountViewModel.sendResetEmailPassword(emailString);
                Snackbar.make(view, R.string.EmailForgotPsw,Snackbar.LENGTH_SHORT).show();
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_forgotPasswordFragment_to_loginFragment);


            }
            else
            {
                progressIndicator.setVisibility(View.GONE);
                Snackbar.make(view,R.string.ForgotPswError,Snackbar.LENGTH_SHORT).show();

            }
        });

    }

    private boolean controlEmailString(String emailString) {
        return !emailString.isEmpty() && Patterns.EMAIL_ADDRESS
                .matcher(emailString).matches();
    }

    private void prepareDataForRegister(boolean checked, String emailString) {
        Bundle rememberResult = new Bundle();
        rememberResult.putBoolean("booleankey", checked);
        rememberResult.putString("mailKey", emailString);
        requireActivity().getSupportFragmentManager()
                .setFragmentResult("mailKey", rememberResult);
    }
}


