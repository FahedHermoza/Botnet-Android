package com.company.app.fakeapp.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Telephony
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.company.app.fakeapp.R
import com.company.app.fakeapp.model.ContactPhone
import com.company.app.fakeapp.model.Phrase
import com.company.app.fakeapp.viewmodel.DetailViewModel
import com.company.app.fakeapp.ui.search.SearchActivity
import kotlinx.android.synthetic.main.detail_fragment.*
import java.lang.Exception
/***
 * https://stackoverflow.com/questions/7661875/how-to-use-share-image-using-sharing-intent-to-share-images-in-android
 */
class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
        const val CODE_SEARCH = 69
    }

    private lateinit var viewModel: DetailViewModel
    private var idPhrase: Int = 1
    private lateinit var phrase: Phrase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    private fun getAnswer(){
        activity!!.intent?.getIntExtra("id_phrase",1).let {
            idPhrase = it?: 1
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        getAnswer()
        setupViewModel()
        setupView()
    }

    private fun setupViewModel() {
        viewModel.phrase.observe(viewLifecycleOwner, Observer {
            phrase = it as Phrase
            ivPhoto.setImageResource(phrase.photo)
            tvDescription.text = phrase.description
        })
    }

    private fun setupView(){
        viewModel.loadPhrase(idPhrase)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sms -> {
                var listContact = getContacts(requireContext())?: emptyList()
                if(listContact.isNotEmpty()){
                    var listName = arrayListOf<String>()
                    var listNumber = arrayListOf<String>()
                    listContact.forEach {
                        listName.add(it.name)
                        listNumber.add(it.number)
                    }
                    val intent = Intent(requireContext(), SearchActivity::class.java).apply {
                        putStringArrayListExtra("ListNameContact", listName)
                        putStringArrayListExtra("ListNumberContact", listNumber)
                    }
                    startActivityForResult(intent,
                        CODE_SEARCH
                    )
                }

                return true
            }
            R.id.action_share -> {
                //navigationToShare()
                navigationToShareImage()
                return true
            }
            R.id.action_email -> {
                navigationToEmail()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun navigationToShare() {
        val message = phrase.description
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text";
        intent.putExtra(Intent.EXTRA_SUBJECT, "Dedicatoria")
        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(intent, ""))
    }

    private fun navigationToShareImage(){
        try {
            val message = phrase.description
            var drawable = resources.getDrawable(phrase.photo,null)
            var bitmap = drawable.toBitmap()
            var path = MediaStore.Images.Media.insertImage(activity?.contentResolver, bitmap, "Dedicatoria", null);
            var uri = Uri.parse(path);

            var intent = Intent(Intent.ACTION_SEND)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Dedicatoria")
            intent.putExtra(Intent.EXTRA_TEXT, message)
            requireContext().startActivity(intent)
        }catch (e: Exception){
            Log.e("TAG", "Exception: $e")
            //navigationToShare()
        }

    }

    private fun navigationToEmail() {
        try {
            val message = phrase.description
            var intent = Intent(Intent.ACTION_SEND)
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, arrayListOf("anyMail@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Dedicatoria")
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.setPackage("com.google.android.gm")
            if (intent.resolveActivity(activity!!.packageManager) != null)
                startActivity(intent)
            else
                Toast.makeText(requireContext(), "Gmail App is not installed", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(requireContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getContacts(context: Context): List<ContactPhone>?{
        var listContactsContract = arrayOf (
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts._ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP,
                ContactsContract.CommonDataKinds.Email.ADDRESS)

        // Fetch Contacts Contract from Built-in Content Provider
        var cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                listContactsContract ,
                null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

        if(cursor!=null) {
            var listContact = arrayListOf<ContactPhone>()
            while (cursor.moveToNext()) {
                var name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME))?:""
                var number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))?:""
                var lastTimestamp = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP))?:""

                listContact.add(
                    ContactPhone(
                        name,
                        number,
                        lastTimestamp
                    )
                )
            }
            cursor.close()
            return deleteDuplicates(listContact)
        }else{
            return null
        }
    }

    private fun deleteDuplicates(listBasic: List<ContactPhone>): List<ContactPhone>{
        var listGroupBy = listBasic.groupBy {it.name}.entries.map { it.value.maxBy { it.name } }
        var listDistinct = arrayListOf<ContactPhone>()
        for(item in listGroupBy){
            item?.let {
                listDistinct.add(
                    ContactPhone(
                        it.name,
                        it.number,
                        it.time
                    )
                )
            }
        }
        return listDistinct
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CODE_SEARCH -> {
                    var name = data?.getStringExtra("NameSelect")
                    var number = data?.getStringExtra("NumberSelect")?:""
                    navigationToSms(number)
                }
            }
        }
    }

    private fun navigationToSms(number: String) {
        val message = phrase.description
        val phone = number

        val uri = Uri.parse("smsto:+$phone")
        val intent = Intent(Intent.ACTION_SENDTO, uri)

        with(intent) {
            putExtra("address", "+$phone")
            putExtra("sms_body", message)
        }

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                //Getting the default sms app.
                val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context)

                // Can be null in case that there is no default, then the user would be able to choose
                // any app that support this intent.
                if (defaultSmsPackageName != null) intent.setPackage(defaultSmsPackageName)

                startActivity(intent)
            }
            else -> startActivity(intent)
        }
    }
}

