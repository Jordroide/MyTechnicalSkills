package com.jordroid.showcase.quotes.presenter.view

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jordroid.showcase.R
import com.jordroid.showcase.databinding.QuoteFragmentBinding
import com.jordroid.showcase.quotes.presenter.model.QuoteUi.QuoteItemUi
import com.jordroid.showcase.quotes.presenter.view.adapter.QuoteAdapter
import com.jordroid.showcase.quotes.presenter.view.adapter.QuoteHeaderViewHolder
import com.jordroid.showcase.quotes.presenter.viewmodel.QuoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuoteRandomFragment : Fragment() {

    // ViewModel instance by Koin
    private val quoteViewModel: QuoteViewModel by viewModel()

    // Binding
    private var _binding: QuoteFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuoteFragmentBinding.inflate(layoutInflater, container, false)
        val rootView = binding.root

        val quoteAdapter = QuoteAdapter { item, view ->
            onItemClick(item, view)
        }

        ItemTouchHelper(
            object :
                ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.START or ItemTouchHelper.END
                ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                /**
                 * On Swiped element, remove the element
                 */
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    with(quoteAdapter.currentList[viewHolder.adapterPosition]) {
                        if (this is QuoteItemUi) {
                            quoteViewModel.remove(this.id)
                        }
                    }
                }

                /**
                 * Override for avoid swipe on header
                 */
                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    return makeMovementFlags(
                        0,
                        if (viewHolder is QuoteHeaderViewHolder) 0 else ItemTouchHelper.START or ItemTouchHelper.END
                    )
                }
            }
        ).attachToRecyclerView(binding.quoteRv)

        binding.quoteRv.adapter = quoteAdapter
        binding.fab.setOnClickListener {
            quoteViewModel.fetchData()
            // add haptic feedback when click for a new quote
            view?.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        }

        lifecycleScope.launchWhenStarted {
            quoteViewModel.getQuotes().collect {
                quoteAdapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            quoteViewModel.quoteStatisticUi.collect {
                binding.chipNumberQuote.text = getString(R.string.number_quote_item, it.numberItem)
                binding.chipNumberAnime.text =
                    getString(R.string.number_quote_anime, it.numberDistinctAnime)
            }
        }

        binding.searchTextLayout.doOnTextChanged { input, _, _, _ ->
            quoteViewModel.searchWith("$input")
        }


        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Display the the name of anime clicked
     */
    private fun onItemClick(quoteItemUi: QuoteItemUi, view: View?) {
        view?.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        Toast.makeText(context, quoteItemUi.label, Toast.LENGTH_LONG).show()
    }
}
