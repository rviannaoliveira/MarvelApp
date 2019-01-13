package com.rviannaoliveira.marvelapp.comics.ui

import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.comics.di.ComicModule
import com.rviannaoliveira.marvelapp.comics.ui.adapter.ComicsAdapter
import com.rviannaoliveira.marvelapp.extensions.createFilterCustom
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 14/04/17.
 */


class ComicsFragment : Fragment(), ComicsView, SearchView.OnQueryTextListener, SupportFragmentInjector {
    override val injector: KodeinInjector = KodeinInjector()
    private val comicsPresenter by injector.instance<ComicsPresenter>()
    private lateinit var comicsAdapter: ComicsAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var comicsRecyclerView: RecyclerView
    private var isLoading: Boolean = false
    private val LIST_STATE_KEY = "123"
    private var listState: Parcelable? = null
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initializeInjector()
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        comicsRecyclerView = view?.findViewById(R.id.list_recycler_view) as RecyclerView
        progressbar = view.findViewById(R.id.progressbar) as ProgressBar
        setHasOptionsMenu(true)

        loadView()
        comicsPresenter.loadMarvelComics(0)
        return view
    }

    override fun onResume() {
        super.onResume()
        comicsRecyclerView.layoutManager.onRestoreInstanceState(listState)
    }

    override fun onDestroy() {
        destroyInjector()
        comicsPresenter.onDisposable()
        super.onDestroy()
    }

    override fun provideOverridingModule(): Kodein.Module = ComicModule(this).dependenciesKodein

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        listState = savedInstanceState?.getParcelable(LIST_STATE_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE_KEY, comicsRecyclerView.layoutManager.onSaveInstanceState())
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_list, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_filter) {
            var positionFilter = 0

            AlertDialog.Builder(requireContext()).createFilterCustom(requireContext(), DialogInterface.OnClickListener { _, w -> positionFilter = w }, DialogInterface.OnClickListener { d, _ ->
                val letter = requireContext().resources.getStringArray(R.array.alphabetic)[positionFilter]
                comicsAdapter.clear()
                if (positionFilter == 0) comicsPresenter.loadMarvelComics(0) else comicsPresenter.loadMarvelComicsBeginLetter(letter)
                d.dismiss()
            }).show()

        }
        return super.onOptionsItemSelected(item)
    }


    override fun loadView() {
        comicsAdapter = ComicsAdapter(comicsPresenter, activity as AppCompatActivity)
        comicsRecyclerView.adapter = comicsAdapter
        val numberGrid = if (MarvelUtil.isPortrait(requireContext())) 2 else 3
        comicsRecyclerView.setHasFixedSize(true)
        comicsRecyclerView.layoutManager = GridLayoutManager(context, numberGrid)
        comicsRecyclerView.addOnScrollListener(onScrollListener())
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadComics(comics: List<MarvelComic>) {
        loadComicRecyclerView(comics, false)
    }

    private fun loadComicRecyclerView(comics: List<MarvelComic>, listForLetter: Boolean) {
        comicsAdapter.setComics(comics, listForLetter)
        isLoading = false
        comicsAdapter.showLoading(isLoading)
    }

    override fun loadFilterComics(comics: List<MarvelComic>) {
        loadComicRecyclerView(comics, true)
    }


    override fun error() {
        if (this.isVisible) {
            MarvelUtil.showErrorScreen(requireContext(), view, resources, R.drawable.comic_error)
        }
    }

    private fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView.layoutManager.childCount
                val totalItemCount = recyclerView.layoutManager.itemCount
                val firstVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                if (!(comicsAdapter.isListForLetter()) &&
                        !isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= MifareUltralight.PAGE_SIZE) {
                    isLoading = true
                    comicsAdapter.showLoading(isLoading)
                    comicsPresenter.loadMarvelComics(totalItemCount)
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        comicsAdapter.filter(newText)
        return true
    }
}