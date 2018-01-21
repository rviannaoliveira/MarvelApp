package com.rviannaoliveira.marvelapp;

import com.rviannaoliveira.marvelapp.data.DataManager;
import com.rviannaoliveira.marvelapp.data.IDataManager;
import com.rviannaoliveira.marvelapp.data.api.IApiData;
import com.rviannaoliveira.marvelapp.data.repository.IRepositoryData;
import com.rviannaoliveira.marvelapp.model.Favorite;
import com.rviannaoliveira.marvelapp.model.MarvelCharacter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;
import io.reactivex.Flowable;

import static org.mockito.Mockito.when;

/**
 * Criado por rodrigo on 15/10/17.
 */

@SuppressWarnings("unchecked")
@RunWith(Parameterized.class)
@Config(constants = BuildConfig.class)
public class MarvelParametrizedTestApiHelper {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    public IApiData apiData;
    @Mock
    public IRepositoryData repositoryData;

    @Parameterized.Parameter(value = 0)
    public MarvelCharacter marvelCharacter;

    @Parameterized.Parameter(value = 1)
    public Favorite favorite;

    @Parameterized.Parameters
    public static List data() {
        return Arrays.asList(new Object[][]{
                {new MarvelCharacter(1), null},
                {new MarvelCharacter(1), new Favorite(1)},
                {new MarvelCharacter(1), new Favorite(2)}
        });
    }

    // ver com edu test Options gradle
    @Test
    public void loadMarvelCharacters() {
        IDataManager dataManager = getDataManager();
        List<MarvelCharacter> characters = Arrays.asList(new MarvelCharacter[]{marvelCharacter});
        List<Favorite> favorites = favorite == null ? Collections.emptyList() : Arrays.asList(new Favorite[]{favorite});

        when(apiData.getMarvelCharacters(0)).thenReturn(Flowable.just(characters));
        when(repositoryData.getCharactersFavorites()).thenReturn(Flowable.just(favorites));
        dataManager.getMarvelCharacters(0)
                .test()
                .assertValue(characters);
    }


    private IDataManager getDataManager() {
        return new DataManager(apiData, repositoryData);
    }
}
