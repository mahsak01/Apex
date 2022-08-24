package com.example.apex

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.*
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.LayoutApexItemBinding
import com.example.apex.databinding.LayoutApexItemSwipeBinding
import java.util.ArrayList

class ApexItemAdapter(
    private val apexItemList: ArrayList<ApexItem>,
    private val eventListener: EventListener,
    private val apexListHeader: ApexListHeader,
    private val namePage: NamePage
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ViewHolder(val binding: LayoutApexItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun binding(apexItem: ApexItem) {
            val differenceDates = differenceDates(apexListHeader.date, apexItem.date)

            binding.layoutApexItemApexItemSerialTv.text =
                "ُسریال ${namePage.getValue()}: " + apexItem.serial
            binding.layoutApexItemApexItemDateTv.text =
                "تاریخ ${namePage.getValue()}: " + apexItem.date
            binding.layoutApexItemApexItemPriceTv.text =
                "مبلغ ${namePage.getValue()}: " + apexItem.price
            binding.layoutApexItemApexItemPercentPriceTv.text =
                "مبلغ بهره: " + priceInterests(
                    apexListHeader.percent,
                    apexItem.price.toLong(),
                    differenceDates
                )
            binding.layoutApexItemApexItemDayTv.text =
                "تعداد روز: $differenceDates روز"
            binding.layoutApexItemApexItemDayTv.isSelected =true
            binding.layoutApexItemApexItemSerialTv.isSelected =true
            binding.layoutApexItemApexItemDateTv.isSelected =true
            binding.layoutApexItemApexItemPriceTv.isSelected =true
            binding.layoutApexItemApexItemPercentPriceTv.isSelected =true

            binding.layoutApexItemApexItem.setOnClickListener {
                eventListener.openEditDialog(apexItem)
                notifyItemChanged(apexItemList.indexOf(apexItem))

            }
        }
    }

    inner class ViewHolderSwipe(val binding: LayoutApexItemSwipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun binding(apexItem: ApexItem) {
            val differenceDates = differenceDates(apexListHeader.date, apexItem.date)
            binding.layoutApexItemSwipeApexItemSerialTv.text =
                "ُسریال ${namePage.getValue()}: " + apexItem.serial
            binding.layoutApexItemSwipeApexItemDateTv.text =
                "تاریخ ${namePage.getValue()}: " + apexItem.date
            binding.layoutApexItemSwipeApexItemPriceTv.text =
                "مبلغ ${namePage.getValue()}: " + apexItem.price
            binding.layoutApexItemSwipeApexItemPercentPriceTv.text =
                "مبلغ بهره: " + priceInterests(
                    apexListHeader.percent,
                    apexItem.price.toLong(),
                    differenceDates
                )
            binding.layoutApexItemSwipeApexItemDayTv.text =
                "تعداد روز: $differenceDates روز"

            binding.layoutApexItemSwipeApexItemDayTv.isSelected =true
            binding.layoutApexItemSwipeApexItemSerialTv.isSelected =true
            binding.layoutApexItemSwipeApexItemDateTv.isSelected =true
            binding.layoutApexItemSwipeApexItemPriceTv.isSelected =true
            binding.layoutApexItemSwipeApexItemPercentPriceTv.isSelected =true
            binding.layoutApexItemSwipeDeleteBtn.setOnClickListener {
                eventListener.deleteApexItem(apexItem)
            }
            binding.layoutApexItemSwipeEditBtn.setOnClickListener {
                eventListener.openEditDialog(apexItem)
                notifyItemChanged(apexItemList.indexOf(apexItem))
            }
            binding.layoutApexItemSwipeApexItem.setOnClickListener {
                eventListener.openEditDialog(apexItem)
                notifyItemChanged(apexItemList.indexOf(apexItem))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MenuStatus.HIDE.getValue())
            return ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_apex_item,
                    parent,
                    false
                )
            )
        return ViewHolderSwipe(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_apex_item_swipe,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        if (apexItemList[position].isShowMenu)
            return MenuStatus.SHOW.getValue()
        return MenuStatus.HIDE.getValue()
    }

    override fun getItemCount(): Int = apexItemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder)
            holder.binding(apexItemList[position])
        else if (holder is ViewHolderSwipe)
            holder.binding(apexItemList[position])
    }

    fun showMenu(position: Int) {
        for (i in apexItemList.indices) {
            if (apexItemList[i].isShowMenu) {
                apexItemList[i].isShowMenu = false
                notifyItemChanged(i)
            }
        }
        apexItemList[position].isShowMenu = true
        notifyItemChanged(position)
    }

    fun closeMenu(position: Int) {
        this.apexItemList[position].isShowMenu = false
        notifyItemChanged(position)
    }

    fun sort(id: Int) {
        when (id) {
            2131231375 -> apexItemList.sortBy { getTimeOfDate(it.date) }
            2131231377 -> apexItemList.sortBy { it.price }
            2131231376 -> apexItemList.sortByDescending { it.price }
        }
        notifyDataSetChanged()

    }

    interface EventListener {
        fun openEditDialog(apexItem: ApexItem)
        fun deleteApexItem(apexItem: ApexItem)
    }
}