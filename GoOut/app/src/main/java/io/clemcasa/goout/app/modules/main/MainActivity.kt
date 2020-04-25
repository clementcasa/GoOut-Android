package io.clemcasa.goout.app.modules.main

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.clemcasa.goout.R
import io.clemcasa.goout.app.modules.createAttestation.CreateAttestationFragment
import io.clemcasa.goout.app.modules.detailsAttestation.DetailsAttestationActivity
import io.clemcasa.goout.app.modules.main.adapter.AttestationListAdapter
import io.clemcasa.goout.app.modules.main.adapter.AttestationListAdapterDelegate
import io.clemcasa.goout.app.modules.main.itemdecoration.SpaceItemDecoration
import io.clemcasa.goout.app.utils.IntentRequest
import io.clemcasa.goout.databinding.ActivityMainBinding
import java.io.File

interface MainViewDelegate {
    fun didCreateNewAttestation()
}

class MainActivity: AppCompatActivity(), MainViewDelegate {
    
    private lateinit var binding: ActivityMainBinding
    
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        
        setupUI()
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_delete_all -> {
                deleteAllAttestations()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IntentRequest.details -> {
                if (resultCode == Activity.RESULT_OK) {
                    updateList()
                }
            }
        }
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        with(binding) {
            val attestationListAdapter = AttestationListAdapter()
            attestationListAdapter.delegate = object : AttestationListAdapterDelegate {
                override fun didClickOnAttestation(title: String) {
                    val intent = Intent(this@MainActivity, DetailsAttestationActivity::class.java)
                    intent.putExtra("fileName", title)
                    startActivityForResult(intent, IntentRequest.details)
                }
            }
            val linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            with(binding.recyclerView) {
                layoutManager = linearLayoutManager
                adapter = attestationListAdapter
                addItemDecoration(SpaceItemDecoration((12 * (context?.resources?.displayMetrics?.density ?: 0.0f)).toInt()))
            }
            updateList()
            
            createAttestationButton.setOnClickListener { _ ->
                val fragment = CreateAttestationFragment.newInstance(Bundle())
                fragment.delegate = this@MainActivity
                fragment.show(supportFragmentManager, CreateAttestationFragment::class.java.name)
            }
            
            swiperefreshLayout.setOnRefreshListener {
                updateList()
                swiperefreshLayout.isRefreshing = false
            }
        }
    }
    
    private fun updateList() {
        val files = filesDir?.list()?.toList()
        with(binding) {
            if (files?.isEmpty() != false) {
                emptyTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                (recyclerView.adapter as? AttestationListAdapter)?.updateData(files)
            }
        }
    }
    
    private fun deleteAllAttestations() {
        AlertDialog.Builder(this)
                .setTitle("Delete all attestations?")
                .setPositiveButton("Delete forever") { _, _ ->
                    val files = filesDir?.list()?.toList()
                    files?.forEach {
                        File(filesDir, it).delete()
                    }
                    updateList()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
    }
    
    override fun didCreateNewAttestation() {
        updateList()
    }
}
