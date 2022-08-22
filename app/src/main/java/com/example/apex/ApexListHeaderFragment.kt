package com.example.apex

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentApexListHeaderBinding
import kotlinx.android.synthetic.main.fragment_apex_list_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApexListHeaderFragment : Fragment(), ApexListHeaderItemAdapter.EventListener {
    private lateinit var binding: FragmentApexListHeaderBinding
    private val viewModel: ApexViewModel by viewModel()
    private var adapter: ApexListHeaderItemAdapter? = null


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
        handler.attachToRecyclerView(this.binding.fragmentApexListHeaderApexListRv)
    }

    private fun setListeners() {
        binding.fragmentInvoiceBackBtn.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
        binding.fragmentApexListHeaderAddBtn.setOnClickListener {


            this.findNavController()
                .navigate(
                    ApexListHeaderFragmentDirections.actionApexListHeaderFragmentToAddApexListHeaderFragment(
                        ModePage.NEW, NamePage.INVOICE, null
                    )
                )

        }
        binding.fragmentApexListHeaderSearchEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                if (adapter != null)
                    adapter?.search(s.toString())
            }
        })
    }

    private fun observer() {
        viewModel.apexInvoiceLiveData.observe(viewLifecycleOwner) {
            binding.fragmentApexListHeaderCountItemTv.text = it.size.toString() + " مورد"
            if (it.isNotEmpty()) {
                binding.fragmentApexListHeaderApexListRv.visibility = View.VISIBLE
                binding.fragmentApexListHeaderApexListRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = ApexListHeaderItemAdapter(it as ArrayList<ApexListHeader>, this)
                binding.fragmentApexListHeaderApexListRv.adapter = adapter
                binding.fragmentApexListHeaderEmptyLayout.root.visibility = View.GONE
            } else {
                binding.fragmentApexListHeaderEmptyLayout.root.visibility = View.VISIBLE
                binding.fragmentApexListHeaderApexListRv.visibility = View.GONE

            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApexListHeaderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun openEditBottomSheet(apexListHeader: ApexListHeader) {
        var bottomSheetDialog = EditApexBottomSheetFragment(apexListHeader)
        bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
    }

    override fun deleteApexListHeader(apexListHeader: ApexListHeader) {
        viewModel.deleteApexListHeader(apexListHeader)
        Toast.makeText(requireContext(), "گروه فاکتور حذف شد", Toast.LENGTH_SHORT)
            .show()
    }

    override fun editApexListHeader(apexListHeader: ApexListHeader) {
        this.findNavController()
            .navigate(
                ApexListHeaderFragmentDirections.actionApexListHeaderFragmentToAddApexListHeaderFragment(
                    ModePage.EDIT, NamePage.INVOICE, apexListHeader
                )
            )
    }

    override fun emptySearch(show:Boolean) {
        if (show){
            binding.fragmentApexListHeaderEmptyLayout.root.visibility = View.VISIBLE
            binding.fragmentApexListHeaderApexListRv.visibility = View.GONE

        }else{
            binding.fragmentApexListHeaderEmptyLayout.root.visibility = View.GONE
            binding.fragmentApexListHeaderApexListRv.visibility = View.VISIBLE

        }
        }
}