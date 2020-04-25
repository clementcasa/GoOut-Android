package io.clemcasa.goout.app.modules.main.viewholders

import androidx.recyclerview.widget.RecyclerView
import io.clemcasa.goout.app.modules.main.adapter.AttestationListAdapterDelegate
import io.clemcasa.goout.databinding.ViewholderAttestationBinding

class AttestationViewHolder(
    val binding: ViewholderAttestationBinding
): RecyclerView.ViewHolder(binding.root) {
    fun onBind(title: String, delegate: AttestationListAdapterDelegate?) {
        binding.attestationTitleTextView.text = title
        binding.container.setOnClickListener {
            delegate?.didClickOnAttestation(title)
        }
    }
}