package io.clemcasa.goout.app.modules.detailsAttestation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.clemcasa.goout.databinding.ActivityDetailsAttestationBinding

class DetailsAttestationActivity: AppCompatActivity() {
    
    private lateinit var binding: ActivityDetailsAttestationBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityDetailsAttestationBinding.inflate(layoutInflater).apply { setContentView(root) }
    }
}