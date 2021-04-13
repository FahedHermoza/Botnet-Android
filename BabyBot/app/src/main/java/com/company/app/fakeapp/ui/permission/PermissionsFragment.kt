package com.company.app.fakeapp.ui.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.company.app.fakeapp.R
import com.company.app.fakeapp.databinding.PermissionsFragmentBinding
import com.company.app.fakeapp.ui.category.CategoryActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class PermissionsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private var _binding: PermissionsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() =
            PermissionsFragment()
        private const val  RC_CONTACTS_SMS_STORAGE_PERM = 124
        private var CONTACTS_SMS_STORAGE = arrayOf(Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PermissionsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(checkSDKMinorsToMarshmallow()){
            navigationToCategory()
        }else{
            if(checkPermissionsGranted()){ navigationToCategory()}
        }

        binding.btnGrantPermissions.setOnClickListener {
            checkSdkAndPermissions()
            binding.btnGrantPermissions.text = getString(R.string.finish_button_permissions_fragment)
        }
    }

    private fun checkPermissionsGranted(): Boolean{
        if (ActivityCompat.checkSelfPermission(activity!!.applicationContext, CONTACTS_SMS_STORAGE[0]) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity!!.applicationContext, CONTACTS_SMS_STORAGE[1]) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity!!.applicationContext, CONTACTS_SMS_STORAGE[2]) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity!!.applicationContext, CONTACTS_SMS_STORAGE[3]) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun checkSdkAndPermissions() {
        if(checkSDKMinorsToMarshmallow())
            navigationToCategory()
        else
            permissionTask()
    }

    private fun checkSDKMinorsToMarshmallow(): Boolean{
        var check = true
        val myVersion = Build.VERSION.SDK_INT
        val versionMarshmallow = Build.VERSION_CODES.M
        if(myVersion >= versionMarshmallow)
            check = false
        return check
    }

    @AfterPermissionGranted(RC_CONTACTS_SMS_STORAGE_PERM)
    fun permissionTask() {
        if (hasPermissions()) {
            navigationToCategory()
        } else {
            EasyPermissions.requestPermissions(
                activity!!,
                getString(R.string.need_permissions_fragment),
                RC_CONTACTS_SMS_STORAGE_PERM,
                CONTACTS_SMS_STORAGE[0],
                CONTACTS_SMS_STORAGE[1],
                CONTACTS_SMS_STORAGE[2],
                CONTACTS_SMS_STORAGE[3]
            )
        }
    }

    private fun hasPermissions(): Boolean {
        return EasyPermissions.hasPermissions(context!!, CONTACTS_SMS_STORAGE[0]) &&
                EasyPermissions.hasPermissions(context!!, CONTACTS_SMS_STORAGE[1]) &&
                EasyPermissions.hasPermissions(context!!, CONTACTS_SMS_STORAGE[2]) &&
                EasyPermissions.hasPermissions(context!!, CONTACTS_SMS_STORAGE[3])
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        navigationToCategory()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(context, getString(R.string.without_permissions_fragment), Toast.LENGTH_LONG).show();
    }

    private fun navigationToCategory() {
        val intent = Intent(context, CategoryActivity::class.java)
        startActivity(intent)
        activity!!.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}