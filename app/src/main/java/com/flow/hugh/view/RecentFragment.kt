package com.flow.hugh.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.flow.hugh.R
import com.flow.hugh.adapter.RecentAdapter
import com.flow.hugh.adapter.ViewHolderBindListener
import com.flow.hugh.databinding.FragmentRecentBinding
import com.flow.hugh.onClick
import com.flow.hugh.viewmodel.MainViewModel

class RecentFragment : Fragment(), ViewHolderBindListener {

    private val binding by lazy { FragmentRecentBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by activityViewModels()
    private val adapter: RecentAdapter = RecentAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.adapter = adapter
        binding.recyclerView.itemAnimator = null
        setOnclick()
        setObserver()
    }

    private fun setOnclick() {
        binding.button.onClick {
            viewModel.recentLiveList.value?.let {
                if (it.isNotEmpty()) showDialog { viewModel.removeAllRecent() }
            }
        }
    }

    private fun setObserver() {
        viewModel.recentLiveList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onViewHolderBind(holder: RecyclerView.ViewHolder, item: Any) {
        item as String

        holder.itemView.findViewById<ImageView>(R.id.imageView).onClick {
            viewModel.removeRecent(item)
        }

        holder.itemView.findViewById<TextView>(R.id.textView).onClick {
            viewModel.recentClickLiveData.value = item
            viewModel.search(item)
            findNavController().navigate(R.id.MovieFragment)
        }
    }

    private fun showDialog(action: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.remove_all))
            .setMessage(getString(R.string.msg_remove_all))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> action.invoke() }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .show()
    }
}