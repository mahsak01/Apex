package com.example.apex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.MenuStatus
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.LayoutInvoiceItemBinding
import com.example.apex.databinding.LayoutInvoiceItemSwipeBinding

class InvoiceItemAdapter(val apexListHeader: ArrayList<ApexListHeader>,val eventListener: EventListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutInvoiceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(apexListHeader: ApexListHeader) {
            binding.layoutInvoiceItemApexListHeaderNameTv.text =
                "گروه فاکتور: " + apexListHeader.name
            binding.layoutInvoiceItemApexListHeaderNumberInvoiceTv.text =
                "تعداد فاکتور: " + apexListHeader.numberItem.toString() + " مورد"
            binding.layoutInvoiceItemApexListHeaderTotalPriceTv.text =
                "مبلغ کل: " + apexListHeader.totalPrice.toString() + " ریال"
            binding.layoutInvoiceItemApexListHeaderDateTv.text =
                "تاریخ راس فاکتور: " + apexListHeader.date
            binding.layoutInvoiceItemApexListHeader.setOnClickListener {
                eventListener.openEditBottomSheet(apexListHeader)
            }
        }
    }

    inner class ViewHolderSwipe(val binding: LayoutInvoiceItemSwipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(apexListHeader: ApexListHeader) {
            binding.layoutInvoiceItemApexListHeaderNameTv.text =
                "گروه فاکتور: " + apexListHeader.name
            binding.layoutInvoiceItemApexListHeaderNumberInvoiceTv.text =
                "تعداد فاکتور: " + apexListHeader.numberItem.toString() + " مورد"
            binding.layoutInvoiceItemApexListHeaderTotalPriceTv.text =
                "مبلغ کل: " + apexListHeader.totalPrice.toString() + " ریال"
            binding.layoutInvoiceItemApexListHeaderDateTv.text =
                "تاریخ راس فاکتور: " + apexListHeader.date
            binding.layoutInvoiceItemApexListHeader.setOnClickListener {
                eventListener.openEditBottomSheet(apexListHeader)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MenuStatus.HIDE.getValue())
            return ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_invoice_item,
                    parent,
                    false
                )
            )
        return ViewHolderSwipe(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_invoice_item_swipe,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        if (apexListHeader[position].isShowMenu)
            return MenuStatus.SHOW.getValue()
        return MenuStatus.HIDE.getValue()
    }

    override fun getItemCount(): Int = apexListHeader.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder)
            holder.binding(apexListHeader[position])
        else if (holder is ViewHolderSwipe)
            holder.binding(apexListHeader[position])
    }

    fun showMenu(position: Int) {
        for (i in apexListHeader.indices) {
            if (apexListHeader[i].isShowMenu) {
                apexListHeader[i].isShowMenu = false
                notifyItemChanged(i)
            }
        }
        apexListHeader[position].isShowMenu = true
        notifyItemChanged(position)
    }

    fun closeMenu(position: Int) {
        this.apexListHeader[position].isShowMenu = false
        notifyItemChanged(position)
    }

    interface EventListener{
        fun openEditBottomSheet(apexItem: ApexListHeader)
    }
}