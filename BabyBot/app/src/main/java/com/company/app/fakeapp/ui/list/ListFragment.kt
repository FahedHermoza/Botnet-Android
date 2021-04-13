package com.company.app.fakeapp.ui.list

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.company.app.fakeapp.databinding.ListFragmentBinding
import com.company.app.fakeapp.model.Category
import com.company.app.fakeapp.model.Phrase
import com.company.app.fakeapp.ui.detail.DetailActivity
import com.company.app.fakeapp.viewmodel.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
        private const val SPAN_COUNT = 1
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: PhraseAdapter

    private lateinit var nameCategory: String

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun getAnswer(){
        activity!!.intent?.getStringExtra("name_category").let {
            nameCategory = it?: ""
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        getAnswer()
        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        viewModel.loadCategory(nameCategory)
        viewModel.loadPrhases(nameCategory)
        viewModel.category.observe(viewLifecycleOwner,renderCategory)
        viewModel.phrases.observe(viewLifecycleOwner,renderPhrases)
    }

    //observers
    private val renderCategory= Observer<Category> {
        binding.tvTitle.text = it.name
        binding.tvSubTitle.text = it.description
    }

    private val renderPhrases= Observer<List<Phrase>> {
        adapter.update(it)
    }

    private fun setupUI() {
        adapter = PhraseAdapter(
            emptyList(),
            onItemAction()
        )
        binding.rvListPhrase.layoutManager = GridLayoutManager(context,
            SPAN_COUNT
        )
        binding.rvListPhrase.adapter = adapter
    }

    private fun onItemAction(): (item: Phrase) -> Unit {
        return {
            goToDetailView(it)
        }
    }

    private fun goToDetailView(item: Phrase) {
        val bundle = Bundle().apply {
            putInt("id_phrase", item.id)
        }
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}