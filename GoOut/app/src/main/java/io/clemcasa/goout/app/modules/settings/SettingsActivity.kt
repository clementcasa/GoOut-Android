package io.clemcasa.goout.app.modules.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.clemcasa.goout.databinding.ActivitySettingsBinding

class SettingsActivity: AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply { setContentView(root) }
    }
}