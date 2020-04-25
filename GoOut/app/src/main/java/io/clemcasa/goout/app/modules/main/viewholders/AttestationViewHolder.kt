package io.clemcasa.goout.app.modules.main.viewholders

import androidx.recyclerview.widget.RecyclerView
import io.clemcasa.goout.databinding.ViewholderAttestationBinding

class AttestationViewHolder(
    val binding: ViewholderAttestationBinding
): RecyclerView.ViewHolder(binding.root) {
    fun onBind(title: String) {
        binding.attestationTitleTextView.text = title
    }
}