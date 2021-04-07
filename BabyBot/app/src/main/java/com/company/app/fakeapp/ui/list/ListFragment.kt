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
import com.company.app.fakeapp.R
import com.company.app.fakeapp.model.Category
import com.company.app.fakeapp.model.Phrase
import com.company.app.fakeapp.ui.detail.DetailActivity
import com.company.app.fakeapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.caterory_fragment.tvTitle
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
        private const val SPAN_COUNT = 1
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: PhraseAdapter

    private lateinit var nameCategory: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
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
        tvTitle.text = it.name
        tvSubTitle.text = it.description
    }

    private val renderPhrases= Observer<List<Phrase>> {
        adapter.update(it)
    }

    private fun setupUI() {
        adapter = PhraseAdapter(
            emptyList(),
            onItemAction()
        )
        rvListPhrase.layoutManager = GridLayoutManager(context,
            SPAN_COUNT
        )
        rvListPhrase.adapter = adapter
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

}