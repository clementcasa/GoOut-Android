package io.clemcasa.goout.app.modules.detailsAttestation

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.clemcasa.goout.R
import io.clemcasa.goout.databinding.ActivityDetailsAttestationBinding
import kotlinx.android.synthetic.main.activity_main.toolbar
import java.io.File

class DetailsAttestationActivity: AppCompatActivity() {
    
    private lateinit var binding: ActivityDetailsAttestationBinding
    
    private var fileName: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityDetailsAttestationBinding.inflate(layoutInflater).apply { setContentView(root) }
        fileName = intent.getStringExtra("fileName")
    
        setupToolbar()
        setupUI()
    }
    
    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                deleteFile()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.title = fileName
    }
    
    private fun setupUI() {
        binding.pdfViewer.fromFile(File(filesDir, fileName)).load()
    }
    
    private fun deleteFile() {
        AlertDialog.Builder(this)
                .setTitle("Delete this attestation?")
                .setPositiveButton("Delete forever") { _, _ ->
                    File(filesDir, fileName).delete()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        
    }
}