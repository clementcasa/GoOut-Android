package io.clemcasa.goout.app.modules.createAttestation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.clemcasa.goout.databinding.FragmentCreateAttestationBinding

class CreateAttestationFragment : BottomSheetDialogFragment() {
    
    private var _binding: FragmentCreateAttestationBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        
        _binding = FragmentCreateAttestationBinding.inflate(layoutInflater)
        
        binding.webview.loadUrl("https://media.interieur.gouv.fr/deplacement-covid-19/")
        return binding.root
    }
    
    companion object {
        fun newInstance(model: Bundle): CreateAttestationFragment = CreateAttestationFragment().apply { arguments = model }
    }
}