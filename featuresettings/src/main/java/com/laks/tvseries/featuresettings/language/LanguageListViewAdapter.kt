package com.laks.tvseries.featuresettings.language

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.common.language.LanguageOnItemClickListener
import com.laks.tvseries.core.language.LanguageModel
import com.laks.tvseries.featuresettings.R


class LanguageListViewAdapter (private val context: Context, private val languageList: ArrayList<LanguageModel>, val handler: LanguageOnItemClickListener) : RecyclerView.Adapter<LanguageListViewAdapter.LanguageViewHolder>() {

    private var lastCheckedRB: RadioButton? = null

    inner class LanguageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rootView: RelativeLayout = view.findViewById(R.id.root)
        var labelTitle: TextView = view.findViewById(R.id.labelTitle)
        var labelDesc: TextView = view.findViewById(R.id.labelDesc)
        var radioSelect: RadioButton = view.findViewById(R.id.radioSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.language_list_item, parent, false)
        return LanguageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.labelTitle.text = languageList[position].title
        holder.labelDesc.text = languageList[position].desc
        holder.radioSelect.isChecked = languageList[position].isSelect!!
        if(languageList[position].isSelect!!){
            lastCheckedRB = holder.radioSelect
        }
        holder.radioSelect.tag = position

        holder.radioSelect.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, _ ->
            val tag = buttonView.tag as Int
            if (lastCheckedRB == null) {
                lastCheckedRB = buttonView as RadioButton
            } else if (tag != lastCheckedRB!!.tag as Int) {
                lastCheckedRB!!.isChecked = false
                lastCheckedRB = buttonView as RadioButton
            }
            handler.languageOnItemClickListener(languageList[position])
        })
    }

    override fun getItemCount(): Int {
        return languageList.size
    }
}
