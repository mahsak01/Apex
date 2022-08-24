package com.example.apex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.MenuStatus
import com.example.apex.common.NamePage
import com.example.apex.common.getTimeOfDate
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.LayoutApexListItemBinding
import com.example.apex.databinding.LayoutApexListItemSwipeBinding

class ApexListHeaderItemAdapter(
    var apexListHeader: ArrayList<ApexListHeader>,
    val eventListener: EventListener,
    val namePage: NamePage
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val allApexListHeader = apexListHeader

    inner class ViewHolder(val binding: LayoutApexListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(apexListHeader: ApexListHeader) {
            binding.layoutApexListItemApexListHeaderNameTv.text =
                "گروه ${namePage.getValue()}: " + apexListHeader.name
            binding.layoutApexListItemApexListHeaderNumberInvoiceTv.text =
                "تعداد ${namePage.getValue()}: " + apexListHeader.numberItem.toString() + " مورد"
            binding.layoutApexListItemApexListHeaderTotalPriceTv.text =
                "مبلغ کل: " + apexListHeader.totalPrice.toString() + " ریال"
            binding.layoutApexListItemApexListHeaderDateTv.text =
                "تاریخ راس ${namePage.getValue()}: " + apexListHeader.date
            binding.layoutApexListItemApexListHeaderNameTv.isSelected =true
            binding.layoutApexListItemApexListHeaderNumberInvoiceTv.isSelected =true
            binding.layoutApexListItemApexListHeaderTotalPriceTv.isSelected =true
            binding.layoutApexListItemApexListHeaderDateTv.isSelected =true
            binding.layoutApexListItemApexListHeader.setOnClickListener {
                eventListener.openEditBottomSheet(apexListHeader)
            }
        }
    }

    inner class ViewHolderSwipe(val binding: LayoutApexListItemSwipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(apexListHeader: ApexListHeader) {
            binding.layoutApexListItemSwipeApexListHeaderNameTv.text =
                "گروه ${namePage.getValue()}: " + apexListHeader.name
            binding.layoutApexListItemSwipeApexListHeaderNumberInvoiceTv.text =
                "تعداد ${namePage.getValue()}: " + apexListHeader.numberItem.toString() + " مورد"
            binding.layoutApexListItemSwipeApexListHeaderTotalPriceTv.text =
                "مبلغ کل: " + apexListHeader.totalPrice.toString() + " ریال"
            binding.layoutApexListItemSwipeApexListHeaderDateTv.text =
                "تاریخ راس ${namePage.getValue()}: " + apexListHeader.date

            binding.layoutApexListItemSwipeApexListHeaderNameTv.isSelected =true
            binding.layoutApexListItemSwipeApexListHeaderNumberInvoiceTv.isSelected =true
            binding.layoutApexListItemSwipeApexListHeaderTotalPriceTv.isSelected =true
            binding.layoutApexListItemSwipeApexListHeaderDateTv.isSelected =true
            binding.layoutApexListItemSwipeApexListHeader.setOnClickListener {
                eventListener.openEditBottomSheet(apexListHeader)
            }
            binding.layoutApexListItemSwipeDeleteBtn.setOnClickListener {
                eventListener.deleteApexListHeader(apexListHeader)
            }
            binding.layoutApexListItemSwipeEditBtn.setOnClickListener {
                eventListener.editApexListHeader(apexListHeader)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MenuStatus.HIDE.getValue())
            return ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_apex_list_item,
                    parent,
                    false
                )
            )
        return ViewHolderSwipe(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_apex_list_item_swipe,
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

    fun search(word: String): Int {
        apexListHeader = allApexListHeader
        var apexListHeaderSearch = ArrayList<ApexListHeader>()

        for (item in apexListHeader)
            if (item.name.contains(word))
                apexListHeaderSearch.add(item)

        apexListHeader = apexListHeaderSearch
        if (apexListHeader.size == 0)
            eventListener.emptySearch(true)
        else
            eventListener.emptySearch(false)
        notifyDataSetChanged()
        return apexListHeaderSearch.size
    }

    fun sort(id: Int) {
        when (id) {
            2131231375 -> apexListHeader.sortBy { getTimeOfDate(it.date) }
            2131231377 -> apexListHeader.sortBy { it.price }
            2131231376 -> apexListHeader.sortByDescending { it.price }
            2131231378 -> apexListHeader.sortBy { it.numberItem }
        }
        notifyDataSetChanged()
    }

    interface EventListener {
        fun openEditBottomSheet(apexListHeader: ApexListHeader)
        fun deleteApexListHeader(apexListHeader: ApexListHeader)
        fun editApexListHeader(apexListHeader: ApexListHeader)
        fun emptySearch(show: Boolean)
    }
}