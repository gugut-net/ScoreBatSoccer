package com.example.scorebatapp.ui.contacts

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.scorebatapp.R
import com.example.scorebatapp.databinding.FragmentContactsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ContactsFragment"
private const val REQUEST_CODE_CONTACTS = 1


@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null

    /**
     * This property is only valid between onCreateView and onDestroyView.
     */
    private val binding get() = _binding!!

    private lateinit var contactName: ListView
    private lateinit var mainActivity: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContactsBinding.inflate(
            inflater,
            container,
        false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as AppCompatActivity
        contactName = binding.contactNames

        val hasReadContactsPermission = ContextCompat.checkSelfPermission(
            mainActivity,
            android.Manifest.permission.READ_CONTACTS
        )
        Log.d(TAG, "onViewCreated:n checkSelfPermission $hasReadContactsPermission")

        if (hasReadContactsPermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onViewCreated: request permission")
            ActivityCompat.requestPermissions(
                mainActivity,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_CONTACTS)

        }

        if (ContextCompat.checkSelfPermission(mainActivity,
                android.Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {
            val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);

            val cursor = activity?.contentResolver?.query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

            val contacts = ArrayList<String>()
            cursor?.use {
                while (it.moveToNext()) {
                    contacts.add(it.getString(it.getColumnIndexOrThrow(ContactsContract
                        .Contacts.DISPLAY_NAME_PRIMARY)))
                }
            }
            val adapter = ArrayAdapter<String>(mainActivity, R.layout.contact_detail, R.id.name, contacts)
            contactName.adapter = adapter
        } else {
            Snackbar.make(view, "Please grant access to your Contacts",
            Snackbar.LENGTH_LONG).setAction("Grant Access") {
                Log.d(TAG, "Snack bar, onClick: start")

                if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,
                        android.Manifest.permission.READ_CONTACTS
                )) {
                    Log.d(TAG, "Snack bar onClick: calling request permission")
                    ActivityCompat.requestPermissions(
                        mainActivity,
                        arrayOf(android.Manifest.permission.READ_CONTACTS),
                        REQUEST_CODE_CONTACTS
                    )
                } else {
                    Log.d(TAG, "Snack bar onClick: calling request permission")
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", mainActivity.packageName, null)
                    Log.d(TAG, "Snack bar onClick: Uri is $uri")
                    intent.data = uri
                    this.startActivity(intent)
                }
                Log.d(TAG, "Snack bar onClick: ends")

                Toast.makeText(it.context, "Snack bar action clicked", Toast.LENGTH_SHORT).show()
            }.show()
        }

        Log.d(TAG, "Fab onClick: ends")

        Log.d(TAG, "onCreate: ends")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}