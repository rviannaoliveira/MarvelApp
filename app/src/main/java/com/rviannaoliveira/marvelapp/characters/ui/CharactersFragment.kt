package com.rviannaoliveira.marvelapp.characters.ui

import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.nfc.tech.MifareUltralight.PAGE_SIZE
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
import com.rviannaoliveira.marvelapp.characters.di.CharactersModule
import com.rviannaoliveira.marvelapp.extensions.createFilterCustom
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.util.MarvelUtil


/**
 * Criado por rodrigo on 09/04/17.
 */

class CharactersFragment : Fragment(), CharactersView, SearchView.OnQueryTextListener, SupportFragmentInjector {
    override val injector: KodeinInjector = KodeinInjector()
    private val charactersPresenterImpl by injector.instance<CharactersPresenter>()
    private var charactersAdapter: CharactersAdapter? = null
    private lateinit var progressbar: ProgressBar
    private lateinit var charactersRecyclerView: RecyclerView
    private var isLoading: Boolean = false
    private val LIST_STATE_KEY = "123"
    private var listState: Parcelable? = null
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initializeInjector()
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        this.progressbar = view?.findViewById(R.id.progressbar) as ProgressBar
        this.charactersRecyclerView = view.findViewById(R.id.list_recycler_view) as RecyclerView
        setHasOptionsMenu(true)

        loadView()
        charactersPresenterImpl.loadMarvelCharacters(0)
        return view
    }

    override fun onResume() {
        super.onResume()
        charactersRecyclerView.layoutManager.onRestoreInstanceState(listState)
    }

    override fun onDestroy() {
        destroyInjector()
        charactersPresenterImpl.onDisposable()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_list, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_filter) {
            var positionFilter = 0

            AlertDialog.Builder(requireContext()).createFilterCustom(requireContext(), DialogInterface.OnClickListener { _, w -> positionFilter = w }, DialogInterface.OnClickListener { d, _ ->
                val letter = requireContext().resources.getStringArray(R.array.alphabetic)[positionFilter]
                charactersAdapter?.clear()
                if (positionFilter == 0) charactersPresenterImpl.loadMarvelCharacters(0) else charactersPresenterImpl.loadMarvelCharactersBeginLetter(letter)
                d.dismiss()
            }).show()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun provideOverridingModule(): Kodein.Module = CharactersModule(this).dependenciesKodein

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        listState = savedInstanceState?.getParcelable(LIST_STATE_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE_KEY, charactersRecyclerView.layoutManager.onSaveInstanceState())
    }

    override fun loadView() {
        charactersAdapter = CharactersAdapter(charactersPresenterImpl, activity as AppCompatActivity)
        charactersRecyclerView.adapter = charactersAdapter
        val numberGrid = if (MarvelUtil.isPortrait(requireContext())) 2 else 3
        charactersRecyclerView.setHasFixedSize(true)
        charactersRecyclerView.layoutManager = GridLayoutManager(context, numberGrid)
        charactersRecyclerView.addOnScrollListener(onScrollListener())
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadCharacters(marvelCharacters: List<MarvelCharacter>) {
        this.loadCharactersRecycleView(marvelCharacters, false)
    }

    override fun error() {
        if (this.isVisible) {
            MarvelUtil.showErrorScreen(requireContext(), view, resources, R.drawable.deadpool_error)
        }
        hideProgressBar()
    }

    private fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView.layoutManager.childCount
                val totalItemCount = recyclerView.layoutManager.itemCount
                val firstVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                if (!(charactersAdapter?.isListForLetter() as Boolean) && !isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true
                    charactersAdapter?.showLoading(isLoading)
                    charactersPresenterImpl.loadMarvelCharacters(totalItemCount)
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        isLoading = !newText.isNullOrEmpty()
        charactersAdapter?.filter(newText)
        return true
    }

    override fun loadFilterCharacters(marvelCharacters: List<MarvelCharacter>) {
        loadCharactersRecycleView(marvelCharacters, true)
    }

    private fun loadCharactersRecycleView(marvelCharacters: List<MarvelCharacter>, listForLetter: Boolean) {
        charactersAdapter?.setCharacters(marvelCharacters, listForLetter)
        isLoading = false
        charactersAdapter?.showLoading(isLoading)
    }

}