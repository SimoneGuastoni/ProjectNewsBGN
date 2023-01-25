package com.example.projectnewsbgn.UI.homepage;
//questa classe permette di iniettare il repository nel viewmodel
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectnewsbgn.Repository.NewsRepository.INewsRepositoryWithLiveData;

//necessario aggiungere iNewsRepositoty perchè funzioni

public class NewsViewModelFactory implements ViewModelProvider.Factory {
    private final INewsRepositoryWithLiveData iNewsRepositoryWithLiveData;
    private final Application application;


    public NewsViewModelFactory(INewsRepositoryWithLiveData iNewsRepositoryWithLiveData,Application application) {
        this.iNewsRepositoryWithLiveData = iNewsRepositoryWithLiveData;
        this.application = application;
    }

    @NonNull
    @Override

    public <T extends ViewModel> T create (@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(iNewsRepositoryWithLiveData,application); //crea un oggetto newsviewmodel passando quel inews object
    }
}
