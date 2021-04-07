package com.company.app.fakeapp.ui.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.company.app.fakeapp.R
import kotlinx.android.synthetic.main.activity_search.*

/***
 * https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview
 */
class SearchActivity : AppCompatActivity(), SearchAdapter.Callback  {

    private lateinit var listNameContact: List<String>
    private lateinit var listNumberContact: List<String>

    private var adapter: SearchAdapter? = null

    private fun getAnswer(){
        intent?.extras?.let {
            listNameContact = it.getStringArrayList("ListNameContact")?: emptyList()
            listNumberContact = it.getStringArrayList("ListNumberContact")?: emptyList()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        getAnswer()

        initRecycler(listName = listNameContact,listNumber = listNumberContact)

        searchViewOptions.setIconifiedByDefault(false)
        searchViewOptions.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter?.filter(query?:"")
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                adapter?.filter(query?:"")
                return false
            }
        })
    }

    fun initRecycler(listName: List<String>, listNumber: List<String>) {
        adapter = SearchAdapter(listName.toMutableList(), listNumber.toMutableList(), this)
        recyclerViewSearch.setHasFixedSize(true)
        recyclerViewSearch.adapter = adapter
    }

    override fun listener(name: String, number: String) {
        var intent= Intent().apply {
            putExtra("NameSelect", name)
            putExtra("NumberSelect", number)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}