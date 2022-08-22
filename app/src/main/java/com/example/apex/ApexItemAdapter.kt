package com.example.apex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.MenuStatus
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.LayoutApexItemBinding
import com.example.apex.databinding.LayoutApexItemSwipeBinding
import java.util.ArrayList

class ApexItemAdapter(
    private val apexItemList: ArrayList<ApexItem>,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ViewHolder(val binding: LayoutApexItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(apexItem: ApexItem) {
            binding.layoutApexItemApexItemSerialTv.text = "ُسریال فاکتور: " + apexItem.serial
            binding.layoutApexItemApexItemDateTv.text = "تاریخ فاکتور: " + apexItem.date
            binding.layoutApexItemApexItemPriceTv.text = "مبلغ فاکتور: " + apexItem.price
            binding.layoutApexItemApexItemPercentPriceTv.text =
                "مبلغ بهره: " + apexItem.priceInterest
            binding.layoutApexItemApexItemDayTv.text = "تعداد روز: " + apexItem.apexDay + " روز"
            binding.layoutApexItemApexItem.setOnClickListener {
                eventListener.openEditDialog(apexItem)
                notifyItemChanged(apexItemList.indexOf(apexItem))

            }
        }
    }

    inner class ViewHolderSwipe(val binding: LayoutApexItemSwipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(apexItem: ApexItem) {
            binding.layoutApexItemSwipeApexItemSerialTv.text = "ُسریال فاکتور: " + apexItem.serial
            binding.layoutApexItemSwipeApexItemDateTv.text = "تاریخ فاکتور: " + apexItem.date
            binding.layoutApexItemSwipeApexItemPriceTv.text = "مبلغ فاکتور: " + apexItem.price
            binding.layoutApexItemSwipeApexItemPercentPriceTv.text =
                "مبلغ بهره: " + apexItem.priceInterest
            binding.layoutApexItemSwipeApexItemDayTv.text =
                "تعداد روز: " + apexItem.apexDay + " روز"
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

    interface EventListener {
        fun openEditDialog(apexItem: ApexItem)
        fun deleteApexItem(apexItem: ApexItem)
    }
}