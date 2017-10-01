package com.rviannaoliveira.marvelapp;

import com.rviannaoliveira.marvelapp.characters.CharactersPresenter;
import com.rviannaoliveira.marvelapp.characters.CharactersPresenterImpl;
import com.rviannaoliveira.marvelapp.characters.CharactersView;
import com.rviannaoliveira.marvelapp.data.api.ApiData;
import com.rviannaoliveira.marvelapp.model.MarvelCharacter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Criado por rodrigo on 24/09/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MarvelInteractionTest {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
    //Criar rule customizado para datamanager  basear Rx2TestSchedulerRule

    @Mock
    CharactersView charactersView;

    @Test
    public void testCreate() {
        ApiData apiData = mock(ApiData.class);
//        when(apiData.getMarvelCharacters(0)).thenReturn(Observable.<ArrayList<MarvelCharacter>>empty());

        getCharactersPresenterUnderTest().loadMarvelCharacters(0);

        verify(this.charactersView).loadCharacters(ArgumentMatchers.<MarvelCharacter>anyList());
        verify(this.charactersView).hideProgressBar();
    }

    private CharactersPresenter getCharactersPresenterUnderTest() {
        return new CharactersPresenterImpl(charactersView);
    }
}
