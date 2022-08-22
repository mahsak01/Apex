package com.example.apex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.data.model.ApexItem
import com.example.apex.databinding.FragmentApexItemsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ApexListItemFragment : Fragment(), AddApexItemDialogFragment.EventListener,ApexItemAdapter.EventListener {

    private lateinit var binding: FragmentApexItemsBinding

    private val args: ApexListItemFragmentArgs by navArgs()

    private val viewModel: ApexViewModel by viewModel()

    private var adapter: ApexItemAdapter? = null

    override fun onResume() {
        super.onResume()
        viewModel.getApexItems(args.apexListHeader)
        setListener()
        initializeSwiping()
        setInformation()
    }

    private fun setListener(){
        binding.fragmentApexItemsApexListHeaderName.setOnClickListener {
            var bottomSheetDialog = EditApexBottomSheetFragment(args.apexListHeader)
            bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
        }
        binding.fragmentApexItemsAddItemBtn.setOnClickListener {
            val addApexItemDialogFragment = AddApexItemDialogFragment(args.apexListHeader,this,null)
            addApexItemDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
    }
    private fun initializeSwiping() {
        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    adapter?.showMenu(viewHolder.layoutPosition)
                } else {
                    adapter?.closeMenu(viewHolder.layoutPosition)
                }
            }
        }
        val handler = ItemTouchHelper(simpleCallback)
        handler.attachToRecyclerView(this.binding.fragmentApexItemsApexItemsRv)
    }

    private fun setInformation() {
        binding.fragmentApexItemsApexListHeaderName.text = args.apexListHeader.name
        viewModel.apexItemsLiveData.observe(viewLifecycleOwner) {
            binding.fragmentApexItemsApexListHeaderNumberTv.text = it.size.toString() + " مورد"
            binding.fragmentApexItemsApexListTotalPriceTv.text =
                getTotalPrice(it).toString() + " ریال"
            if (it.isNotEmpty()) {
                binding.fragmentApexItemsDateLl.visibility = View.VISIBLE
                binding.fragmentApexItemsPriceLl.visibility = View.VISIBLE
                binding.fragmentApexItemsMinPriceTv.text = getMinPrice(it).toString() + " ریال"
                binding.fragmentApexItemsMaxPriceTv.text = getMaxPrice(it).toString() + " ریال"
                binding.fragmentApexItemsMinDateTv.text = getMinDate(it)
                binding.fragmentApexItemsMaxDateTv.text = getMaxDate(it)
                binding.fragmentApexItemsApexItemsRv.visibility = View.VISIBLE
                binding.fragmentApexItemsApexItemsRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = ApexItemAdapter(it as ArrayList<ApexItem>,this)
                binding.fragmentApexItemsApexItemsRv.adapter = adapter
                binding.fragmentChequeEmptyLayout.root.visibility = View.GONE
            } else {
                binding.fragmentApexItemsDateLl.visibility = View.GONE
                binding.fragmentApexItemsPriceLl.visibility = View.GONE
                binding.fragmentApexItemsApexItemsRv.visibility = View.GONE
                binding.fragmentChequeEmptyLayout.root.visibility = View.VISIBLE
            }
        }
    }

    private fun getTotalPrice(array: List<ApexItem>): Int {
        var totalPrice = 0
        for (item in array) {
            totalPrice += item.price
        }
        return totalPrice
    }

    private fun getMinDate(array: List<ApexItem>): String {
        var min = array[0].date
        for (item in array) {
            var minSecond = SimpleDateFormat("yyyy/MM/dd").parse(min)
            var temp = SimpleDateFormat("yyyy/MM/dd").parse(item.date)
            if (temp < minSecond)
                min = item.date
        }
        return min
    }

    private fun getMaxDate(array: List<ApexItem>): String {
        var max = array[0].date
        for (item in array) {
            var minSecond = SimpleDateFormat("yyyy/MM/dd").parse(max)
            var temp = SimpleDateFormat("yyyy/MM/dd").parse(item.date)
            if (temp > minSecond)
                max = item.date
        }
        return max
    }

    private fun getMinPrice(array: List<ApexItem>): Int {
        var min = array[0].price
        for (item in array) {
            if (item.price < min)
                min = item.price
        }
        return min
    }

    private fun getMaxPrice(array: List<ApexItem>): Int {
        var max = array[0].price
        for (item in array) {
            if (item.price > max)
                max = item.price
        }
        return max
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentApexItemsBinding.inflate(layoutInflater, container, false)
        return this.binding.root
    }

    override fun addApexItem(apexItem: ApexItem) {
        viewModel.addApexItem(apexItem,args.apexListHeader)
    }

    override fun updateApexItem(apexItem: ApexItem) {
        viewModel.updateApexItem(apexItem,args.apexListHeader)

    }

    override fun openEditDialog(apexItem: ApexItem) {
        val addApexItemDialogFragment = AddApexItemDialogFragment(args.apexListHeader,this,apexItem)
        addApexItemDialogFragment.show(requireActivity().supportFragmentManager, null)

    }

    override fun deleteApexItem(apexItem: ApexItem) {
        viewModel.deleteApexItem(apexItem,args.apexListHeader)
        Toast.makeText(requireContext(), "فاکتور حذف شد", Toast.LENGTH_SHORT)
            .show()
    }
}