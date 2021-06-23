package com.jordroid.showcase.quote.random.presenter.view

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jordroid.showcase.R
import com.jordroid.showcase.databinding.QuoteListFragmentBinding
import com.jordroid.showcase.quote.random.presenter.model.QuoteUi.QuoteItemUi
import com.jordroid.showcase.quote.random.presenter.view.adapter.QuoteAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuoteRandomFragment : Fragment() {

    private val quoteViewModel: QuoteViewModel by viewModel()

    private var _binding: QuoteListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuoteListFragmentBinding.inflate(layoutInflater, container, false)
        val rootView = binding.root

        val quoteAdapter = QuoteAdapter { item, view ->
            onItemClick(item, view)
        }

        binding.quoteRv.adapter = quoteAdapter
        binding.quoteRefresh.setOnRefreshListener {
            binding.motionLayout.transitionToEnd()
            quoteViewModel.fetchData()
        }

        lifecycleScope.launchWhenStarted {
            quoteViewModel.getQuote().flowOn(Dispatchers.IO).collect {
                quoteAdapter.submitList(it) {
                    if (binding.quoteRefresh.isRefreshing) {
                        binding.quoteRefresh.isRefreshing = false
                        view?.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    binding.motionLayout.transitionToStart()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            quoteViewModel.quoteStatistic.collect {
                binding.chipNumberItem.text = getString(R.string.number_quote_item, it.numberItem)
                binding.chipNumberAnime.text =
                    getString(R.string.number_quote_anime, it.numberDistinctAnime)
            }
        }

        binding.searchTextLayout.doOnTextChanged { input, _, _, _ ->
            quoteViewModel.searchWith(input.toString())
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(quoteItemUi: QuoteItemUi, view: View?) {
        view?.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        Toast.makeText(context, quoteItemUi.label, Toast.LENGTH_LONG).show()
    }
}