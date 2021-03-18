package com.orcchg.yandexcontest.search.demo.ui

import androidx.fragment.app.Fragment
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.search.demo.R
import com.orcchg.yandexcontest.search.demo.databinding.SearchSuggestDemoFragmentBinding

internal class SearchSuggestDemoFragment : Fragment(R.layout.search_suggest_demo_fragment) {

    private val binding by viewBindings(SearchSuggestDemoFragmentBinding::bind)
}
