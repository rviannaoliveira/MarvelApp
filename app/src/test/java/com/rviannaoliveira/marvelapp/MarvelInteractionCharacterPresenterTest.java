package com.rviannaoliveira.marvelapp;

import com.rviannaoliveira.marvelapp.characters.ui.CharactersPresenter;
import com.rviannaoliveira.marvelapp.characters.ui.CharactersPresenterImpl;
import com.rviannaoliveira.marvelapp.characters.ui.CharactersView;
import com.rviannaoliveira.marvelapp.data.IDataManager;
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

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Criado por rodrigo on 24/09/17.
 */
@RunWith(MockitoJUnitRunner.class)
@Config(constants = BuildConfig.class)
public class MarvelInteractionCharacterPresenterTest {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    public CharactersView charactersView;

    @Mock
    public IDataManager dataManager;

    @Mock
    public List<MarvelCharacter> mockList;


    @Test
    public void loadMarvelCharacters() {
        int offset = 0;
        when(dataManager.getMarvelCharacters(offset)).thenReturn(Single.just(mockList));
        getCharactersPresenterUnderTest().loadMarvelCharacters(offset);

        verify(this.charactersView).showProgressBar();
        verify(this.charactersView).loadCharacters(ArgumentMatchers.<MarvelCharacter>anyList());
        verify(this.charactersView).hideProgressBar();
    }

    @Test
    public void loadMarvelCharactersBeginLetter() {
        String letter = "a";
        when(dataManager.getMarvelCharactersBeginLetter(letter)).thenReturn(Single.just(mockList));
        getCharactersPresenterUnderTest().loadMarvelCharactersBeginLetter(letter);

        verify(this.charactersView).showProgressBar();
        verify(this.charactersView).loadFilterCharacters(ArgumentMatchers.<MarvelCharacter>anyList());
        verify(this.charactersView).hideProgressBar();
    }

    private CharactersPresenter getCharactersPresenterUnderTest() {
        return new CharactersPresenterImpl(charactersView, dataManager);
    }
}
