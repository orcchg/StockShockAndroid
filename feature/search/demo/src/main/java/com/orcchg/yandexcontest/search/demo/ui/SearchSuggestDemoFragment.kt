package com.orcchg.yandexcontest.search.demo.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.search.demo.R
import com.orcchg.yandexcontest.search.demo.databinding.SearchSuggestDemoFragmentBinding
import com.orcchg.yandexcontest.search.demo.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.search_bar.ui.SearchFlowLayout

internal class SearchSuggestDemoFragment : Fragment(R.layout.search_suggest_demo_fragment) {

    private val binding by viewBindings(SearchSuggestDemoFragmentBinding::bind)
    private val sharedViewModel by activityViewModels<SearchFlowViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sflTop.onItemClickListener = SearchFlowLayout.OnItemClickListener(::handleClickOnSearchItem)
        binding.sflBottom.onItemClickListener = SearchFlowLayout.OnItemClickListener(::handleClickOnSearchItem)
    }

    private fun handleClickOnSearchItem(view: View) {
        if (view is TextView) {
            sharedViewModel.sendSearchRequest(view.text.toString())
        }
    }
}
