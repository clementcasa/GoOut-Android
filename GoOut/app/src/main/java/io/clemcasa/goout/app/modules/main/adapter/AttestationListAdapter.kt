package io.clemcasa.goout.app.modules.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.clemcasa.goout.app.modules.main.viewholders.AttestationViewHolder
import io.clemcasa.goout.databinding.ViewholderAttestationBinding

class AttestationListAdapter: RecyclerView.Adapter<AttestationViewHolder>() {
    
    var titles: List<String> = listOf()
    var delegate: AttestationListAdapterDelegate? = null
    
    fun updateData(titles: List<String>) {
        this.titles = titles.toList()
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttestationViewHolder =
        AttestationViewHolder(ViewholderAttestationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    override fun getItemCount(): Int = titles.count()
    
    override fun onBindViewHolder(holder: AttestationViewHolder, position: Int) {
        holder.onBind(titles[position], delegate)
    }
}