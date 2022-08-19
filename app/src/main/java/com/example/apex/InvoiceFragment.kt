package com.example.apex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentInvoiceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InvoiceFragment : Fragment(), InvoiceItemAdapter.EventListener {
    private lateinit var binding: FragmentInvoiceBinding
    private val viewModel: ApexViewModel by viewModel()
    private var adapter: InvoiceItemAdapter? = null


    override fun onResume() {
        super.onResume()
        viewModel.getApexInvoiceListHeader()
        setListeners()
        initializeSwiping()
        observer()
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
        handler.attachToRecyclerView(this.binding.fragmentInvoiceInvoiceListRv)
    }

    private fun setListeners() {
        binding.fragmentInvoiceBackBtn.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
        binding.fragmentInvoiceAddInvoiceBtn.setOnClickListener {


            this.findNavController()
                .navigate(
                    InvoiceFragmentDirections.actionInvoiceFragmentToAddApexListInvoiceFragment(
                        ModePage.NEW,NamePage.INVOICE,null
                    )
                )

        }
    }

    private fun observer() {
        viewModel.apexInvoiceLiveData.observe(viewLifecycleOwner) {
            binding.fragmentInvoiceCountItemTv.text = it.size.toString() + " مورد"
            if (it.isNotEmpty()) {
                binding.fragmentInvoiceInvoiceListRv.visibility = View.VISIBLE
                binding.fragmentInvoiceInvoiceListRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = InvoiceItemAdapter(it as ArrayList<ApexListHeader>, this)
                binding.fragmentInvoiceInvoiceListRv.adapter = adapter
                binding.fragmentChequeEmptyLayout.root.visibility = View.GONE
            } else {
                binding.fragmentChequeEmptyLayout.root.visibility = View.VISIBLE
                binding.fragmentInvoiceInvoiceListRv.visibility = View.GONE

            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun openEditBottomSheet(apexListHeader: ApexListHeader) {
        var bottomSheetDialog = EditApexBottomSheetFragment(apexListHeader)
        bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
    }
}