package com.example.projectnewsbgn.UI.Main;
//questa classe permette di iniettare il repository nel viewmodel
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectnewsbgn.Repository.INewsRepository;
import com.example.projectnewsbgn.Repository.INewsRepositoryWithLiveData;

//necessario aggiungere iNewsRepositoty perchè funzioni

public class NewsViewModelFactory implements ViewModelProvider.Factory {
    private final INewsRepositoryWithLiveData iNewsRepositoryWithLiveData;


    public NewsViewModelFactory(INewsRepositoryWithLiveData iNewsRepositoryWithLiveData) {
        this.iNewsRepositoryWithLiveData = iNewsRepositoryWithLiveData;

    }

    @NonNull
    @Override

    public <T extends ViewModel> T create (@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(iNewsRepositoryWithLiveData); //crea un oggetto newsviewmodel passando quel inews object
    }
}
