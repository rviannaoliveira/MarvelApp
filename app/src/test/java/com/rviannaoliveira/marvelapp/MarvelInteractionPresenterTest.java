package com.rviannaoliveira.marvelapp;

import com.rviannaoliveira.marvelapp.characters.CharactersPresenter;
import com.rviannaoliveira.marvelapp.characters.CharactersPresenterImpl;
import com.rviannaoliveira.marvelapp.characters.CharactersView;
import com.rviannaoliveira.marvelapp.data.DataManagerInterface;
import com.rviannaoliveira.marvelapp.model.MarvelCharacter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Criado por rodrigo on 24/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
@Config(constants = BuildConfig.class)
public class MarvelInteractionPresenterTest {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
    //Criar rule customizado para datamanager  basear Rx2TestSchedulerRule

    @Mock
    public CharactersView charactersView;

    @Mock
    public DataManagerInterface dataManager;

    @Mock
    public List<MarvelCharacter> mockList;


    @Test
    public void loadMarvelCharacters() {
        int offset = 0;
        when(dataManager.getMarvelCharacters(offset)).thenReturn(Flowable.just(mockList));
        getCharactersPresenterUnderTest().loadMarvelCharacters(offset);

        verify(this.charactersView).showProgressBar();
        verify(this.charactersView).loadCharacters(ArgumentMatchers.<MarvelCharacter>anyList());
        verify(this.charactersView).hideProgressBar();
    }

    private CharactersPresenter getCharactersPresenterUnderTest() {
        return new CharactersPresenterImpl(charactersView, dataManager);
    }
}
