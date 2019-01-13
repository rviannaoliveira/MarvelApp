package com.rviannaoliveira.marvelapp.favorite

import com.rviannaoliveira.marvelapp.data.IDataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Criado por rodrigo on 14/04/17.
 */
class FavoritePresenterImpl(private var view: FavoriteView, private val dataManager: IDataManager) : FavoritePresenter {
    private val disposable = CompositeDisposable()

    override fun loadFavorites() {
        view.let {
            it.showProgressBar()
            val observableFavorites = dataManager.getAllFavorites()
            disposable.add(observableFavorites.subscribe({ favorites ->
                it.loadFavorites(favorites)
                it.hideProgressBar()
            }, { error ->
                it.hideProgressBar()
                it.error()
                Timber.w(error.message)
            }))
        }
    }

    override fun deleteFavorite(favorite: Favorite, removeCharacter: Boolean) {
        dataManager.run {
            disposable.add(deleteFavorite(favorite, removeCharacter).subscribe({ _ ->
                loadFavorites()
            }))
        }
    }

    override fun onDisposable() {
        disposable.clear()
    }
}