package com.ikran.flickrapiapp.search

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikran.flickrapiapp.R
import com.ikran.flickrapiapp.data.ERROR
import com.ikran.flickrapiapp.data.LIST
import com.ikran.flickrapiapp.data.NO_DATA
import com.ikran.flickrapiapp.data.Status
import com.ikran.flickrapiapp.extensions.injectViewModel
import com.ikran.flickrapiapp.utils.view.ItemDivider
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_no_result.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchVM: SearchVM
    private val adapter = SearchAdapter()

    private var oldQuery: String = "nature"
    private var newQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchVM = injectViewModel(viewModelFactory)

        val config = resources.configuration
        rvPhotos.layoutManager = GridLayoutManager(this, 4, getOrientation(config), false)

        val divider = R.dimen.default_4dp
        rvPhotos.addItemDecoration(ItemDivider(Color.TRANSPARENT,divider,divider))
        rvPhotos.adapter = adapter

        etSearch.setOnEditorActionListener { v, actionId, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && etSearch.text.toString().isNotEmpty()) {
                newQuery = etSearch.text.toString()
                etSearch.text?.clear()

                searchPhotos(newQuery)
            }
            true
        }

        ivSearch.setOnClickListener {
            if (etSearch.text.toString().isNotEmpty()) {
                newQuery = etSearch.text.toString()
                etSearch.text?.clear()

                searchPhotos(newQuery)
            }
        }
    }


    @RecyclerView.Orientation
    private fun getOrientation(config: Configuration): Int {
        return when (config.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                LinearLayoutManager.VERTICAL
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LinearLayoutManager.HORIZONTAL
            }
            else -> {
                throw AssertionError("This should not be the case.")
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (searchVM.oldQuery.isNotEmpty())
            oldQuery = searchVM.oldQuery

        searchPhotos()
    }

    fun searchPhotos(query: String = oldQuery) {
        dismissKeyboard()

        val data = searchVM.search(query)

        data?.pagedList?.observe(this, Observer {
            adapter.submitList(it)
        })

        data?.networkState?.observe(this, Observer {

            when (it.status) {
                Status.RUNNING -> {
                    toggleLoading(true)
                }
                Status.SUCCESS -> {
                    toggleLoading(false)
                    show(LIST)
                    oldQuery = newQuery
                }

                Status.FAILED -> {
                    toggleLoading(false)
                    show(ERROR)
                    searchVM.oldQuery = ""
                    oldQuery = ""
                }

                Status.NO_DATA -> {
                    toggleLoading(false)
                    if (adapter.itemCount  == 0 || oldQuery != newQuery )
                        show(NO_DATA)
                }
            }
        })
    }

    private fun show(list: Int = LIST) {
        when (list) {
            LIST -> {
                llNoResults.visibility = View.GONE
                rvPhotos.visibility = View.VISIBLE
            }
            ERROR -> {
                setDetails(
                    R.drawable.ic_no_result,
                    R.string.error,
                    R.string.try_later
                )
                llNoResults.visibility = View.VISIBLE
                rvPhotos.visibility = View.GONE
            }
            NO_DATA -> {
                setDetails()
                llNoResults.visibility = View.VISIBLE
                rvPhotos.visibility = View.GONE
            }
        }
    }

    fun dismissKeyboard() {
        etSearch.clearFocus()
        val inms = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inms.hideSoftInputFromWindow(etSearch.windowToken, 0)
    }

    fun toggleLoading(show: Boolean) {
        pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setDetails(
        @DrawableRes icon: Int = R.drawable.ic_no_result,
        @StringRes title: Int = R.string.error,
        @StringRes message: Int = R.string.no_result
    ) {
        ivIcon.setImageDrawable(ContextCompat.getDrawable(this, icon))
        ivTitle.text = resources.getString(title)
        ivMessage.text = resources.getString(message)
    }

   /* fun setTestViewModel(viewModel: SearchVM) {
        searchVM = viewModel
    }*/

}
