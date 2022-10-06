package com.example.apex

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.beautifyPrice
import com.example.apex.common.differenceDates
import com.example.apex.common.getLastPrice
import com.example.apex.common.totalPrice
import com.example.apex.data.model.ApexItem
import com.example.apex.databinding.FragmentApexItemsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ApexListItemFragment : Fragment(), AddApexItemDialogFragment.EventListener,
    ApexItemAdapter.EventListener, SortApexItemDialogFragment.EventListeners {

    private lateinit var binding: FragmentApexItemsBinding

    private val args: ApexListItemFragmentArgs by navArgs()

    private val viewModel: ApexViewModel by viewModel()

    private var adapter: ApexItemAdapter? = null
    private var sort = 0

    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        viewModel.getApexItems(args.apexListHeader)
        setListener()
        initializeSwiping()
        setInformation()
    }

    private fun setListener() {
        binding.fragmentApexItemsApexListHeaderName.setOnClickListener {
            val bottomSheetDialog = EditApexBottomSheetFragment(args.apexListHeader, args.namePage)
            bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
        }
        binding.fragmentApexItemsSortBtn.setOnClickListener {
            val sortDialogFragment = SortApexItemDialogFragment(sort, this)
            sortDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentApexItemsAddItemBtn.setOnClickListener {
            val addApexItemDialogFragment =
                AddApexItemDialogFragment(args.apexListHeader, this, null)
            addApexItemDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentApexItemsBackBtn.setOnClickListener {

            this.findNavController()
                .navigate(
                    ApexListItemFragmentDirections.actionApexListItemFragmentToApexListHeaderFragment2(
                        args.namePage
                    )
                )
        }
        this.view?.setOnKeyListener(object :
            View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == KeyEvent.KEYCODE_BACK) {
                    findNavController()
                        .navigate(
                            ApexListItemFragmentDirections.actionApexListItemFragmentToApexListHeaderFragment2(
                                args.namePage
                            )
                        )

                    return true
                }
                return false
            }

        })
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

    @SuppressLint("SetTextI18n")
    private fun setInformation() {
        binding.fragmentApexItemsApexListHeaderName.text = args.apexListHeader.name
        binding.fragmentApexItemsApexListHeaderName.isSelected = true

        binding.fragmentApexItemsAppBarText.text = args.namePage.getValue()
        viewModel.apexItemsLiveData.observe(viewLifecycleOwner) {
            binding.fragmentApexItemsApexListHeaderNumberTv.text = it.size.toString() + " مورد"
            binding.fragmentApexItemsApexListTotalPriceTv.text =
                getTotalPrice(it).toString().beautifyPrice()
            if (it.isNotEmpty()) {
                binding.fragmentApexItemsDateLl.visibility = View.VISIBLE
                binding.fragmentApexItemsPriceLl.visibility = View.VISIBLE
                binding.fragmentApexItemsMinPriceTv.text =
                    getMinPrice(it).toString().beautifyPrice()
                binding.fragmentApexItemsMaxPriceTv.text =
                    getMaxPrice(it).toString().beautifyPrice()
                binding.fragmentApexItemsMinDateTv.text = getMinDate(it)
                binding.fragmentApexItemsMaxDateTv.text = getMaxDate(it)
                binding.fragmentApexItemsApexItemsRv.visibility = View.VISIBLE
                binding.fragmentApexItemsApexItemsRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = ApexItemAdapter(
                    it as ArrayList<ApexItem>,
                    this,
                    args.apexListHeader,
                    args.namePage
                )
                adapter?.sort(sort)
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

    private fun getTotalPrice(array: List<ApexItem>): Long {
        var totalPrice = 0L
        for (item in array) {
            totalPrice += item.price.toLong()
        }
        return totalPrice
    }

    @SuppressLint("SimpleDateFormat")
    private fun getMinDate(array: List<ApexItem>): String {
        var min = array[0].date
        for (item in array) {
            val minSecond = SimpleDateFormat("yyyy/MM/dd").parse(min)
            val temp = SimpleDateFormat("yyyy/MM/dd").parse(item.date)
            if (temp < minSecond)
                min = item.date
        }
        return min
    }

    @SuppressLint("SimpleDateFormat")
    private fun getMaxDate(array: List<ApexItem>): String {
        var max = array[0].date
        for (item in array) {
            val minSecond = SimpleDateFormat("yyyy/MM/dd").parse(max)
            val temp = SimpleDateFormat("yyyy/MM/dd").parse(item.date)
            if (temp > minSecond)
                max = item.date
        }
        return max
    }

    private fun getMinPrice(array: List<ApexItem>): Long {
        var min = array[0].price.toLong()
        for (item in array) {
            if (item.price.toLong() < min)
                min = item.price.toLong()
        }
        return min
    }

    private fun getMaxPrice(array: List<ApexItem>): Long {
        var max = array[0].price.toLong()
        for (item in array) {
            if (item.price.toLong() > max)
                max = item.price.toLong()
        }
        return max
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentApexItemsBinding.inflate(layoutInflater, container, false)
        return this.binding.root
    }

    override fun addApexItem(apexItem: ApexItem) {
        if (!checkPrice(apexItem.price.toLong()))
            Toast.makeText(
                requireContext(),
                "مبلغ ${args.namePage.getValue()} از مبلغ باقی مانده بیشتر است",
                Toast.LENGTH_SHORT
            )
                .show()
        else if (!checkDate(apexItem.date))
            Toast.makeText(
                requireContext(),
                "تاریخ ${args.namePage.getValue()} از تاریخ راس زودتر است",
                Toast.LENGTH_SHORT
            )
                .show()
        else if (viewModel.searchApexItem(apexItem))
            viewModel.addApexItem(apexItem, args.apexListHeader)
        else
            Toast.makeText(
                requireContext(),
                "${args.namePage.getValue()} با این سریال وجود دارد",
                Toast.LENGTH_SHORT
            )
                .show()
    }

    override fun updateApexItem(apexItem: ApexItem) {
        viewModel.updateApexItem(apexItem, args.apexListHeader)

    }

    private fun checkPrice(price: Long): Boolean {
        if (getLastPrice(
                args.apexListHeader,
                totalPrice(viewModel.apexItemsLiveData.value!!)
            ) >= price
        )
            return true
        return false
    }

    private fun checkDate(date: String): Boolean {
        if (date.replace("/", "").toLong() >= args.apexListHeader.date.replace("/", "").toLong())
            return true
        return false
    }

    override fun openEditDialog(apexItem: ApexItem) {
        val addApexItemDialogFragment =
            AddApexItemDialogFragment(args.apexListHeader, this, apexItem)
        addApexItemDialogFragment.show(requireActivity().supportFragmentManager, null)

    }

    override fun deleteApexItem(apexItem: ApexItem) {
        viewModel.deleteApexItem(apexItem, args.apexListHeader)
        Toast.makeText(requireContext(), "${args.namePage.getValue()} حذف شد", Toast.LENGTH_SHORT)
            .show()
    }

    override fun changeSort(id: Int) {
        this.sort = id
        if (adapter != null)
            adapter?.sort(id)
    }
}