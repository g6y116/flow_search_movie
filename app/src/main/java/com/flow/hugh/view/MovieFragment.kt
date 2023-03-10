package com.flow.hugh.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.flow.hugh.Code
import com.flow.hugh.R
import com.flow.hugh.adapter.MovieAdapter
import com.flow.hugh.adapter.ViewHolderBindListener
import com.flow.hugh.bottomsheet.RoundedBottomSheet
import com.flow.hugh.data.Movie
import com.flow.hugh.data.SearchOption
import com.flow.hugh.databinding.FragmentMovieBinding
import com.flow.hugh.onClick
import com.flow.hugh.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieFragment : Fragment(), ViewHolderBindListener {

    private val binding by lazy { FragmentMovieBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by activityViewModels()
    private val adapter: MovieAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.adapter = adapter
        binding.recyclerView.itemAnimator = null
        setOnclick()
        setObserver()
        setListener()
    }

    private fun setOnclick() {
        binding.button.onClick {
            viewModel.search(SearchOption(query = binding.textInputEditText.text.toString()))
        }

        binding.genreChip.onClick {
            viewModel.searchOptionLiveData.value?.let { searchOption ->
                RoundedBottomSheet(getString(R.string.genre), Code.genreList.map { it.name }) { name ->
                    viewModel.search(searchOption.copy(genre = Code.getGenre(name)?.code))
                }.show((activity as MainActivity).supportFragmentManager, tag)
            }
        }

        binding.countryChip.onClick {
            viewModel.searchOptionLiveData.value?.let { searchOption ->
                RoundedBottomSheet(getString(R.string.country), Code.countryList.map { it.name }) { name ->
                    viewModel.search(searchOption.copy(country = Code.getCountry(name)?.code))
                }.show((activity as MainActivity).supportFragmentManager, tag)
            }
        }

        binding.resetChip.onClick {
            viewModel.searchOptionLiveData.value?.let { searchOption ->
                viewModel.search(SearchOption(query = searchOption.query))
            }
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieFlowList.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        viewModel.recentClickLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.textInputEditText.setText(it)
                viewModel.recentClickLiveData.value = null
            }
        }

        viewModel.searchOptionLiveData.observe(viewLifecycleOwner) {
            binding.genreChip.text =
                if (it.genre.isNullOrEmpty()) getString(R.string.genre)
                else Code.getGenre(it.genre)?.name ?: getString(R.string.genre)

            binding.countryChip.text =
                if (it.country.isNullOrEmpty()) getString(R.string.country)
                else Code.getCountry(it.country)?.name ?: getString(R.string.country)
        }
    }

    private fun setListener() {
        binding.textInputEditText.setOnEditorActionListener { view, id, keyEvent ->
            if(id == EditorInfo.IME_ACTION_DONE){
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.textInputEditText.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onViewHolderBind(holder: RecyclerView.ViewHolder, item: Any) {
        holder.itemView.onClick {
            if ((item as Movie).link.isNotEmpty())
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
        }
    }
}