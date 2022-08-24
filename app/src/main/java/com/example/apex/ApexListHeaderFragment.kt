package com.example.apex

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentApexListHeaderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApexListHeaderFragment : Fragment(), ApexListHeaderItemAdapter.EventListener {
    private lateinit var binding: FragmentApexListHeaderBinding
    private val viewModel: ApexViewModel by viewModel()
    private var adapter: ApexListHeaderItemAdapter? = null
    private val args: ApexListHeaderFragmentArgs by navArgs()


    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true;
        this.view?.requestFocus();
        if (args.namePage == NamePage.INVOICE)
            viewModel.getApexInvoiceListHeader()
        else
            viewModel.getApexChequeListHeader()
        setListeners()
        initializeSwiping()
        observer()
        setInformation()
    }

    private fun setInformation() {
        binding.fragmentApexListHeaderAppbarNameTv.text = args.namePage.getValue()
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
        binding.fragmentApexListHeaderBackBtn.setOnClickListener {
            this.requireActivity().onBackPressed()
        }

        binding.fragmentApexListHeaderSortBtn.setOnClickListener {
            val sortDialogFragment = ApexSortDialogFragment()
            sortDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentApexListHeaderAddBtn.setOnClickListener {


            this.findNavController()
                .navigate(
                    ApexListHeaderFragmentDirections.actionApexListHeaderFragmentToAddApexListHeaderFragment(
                        ModePage.NEW, args.namePage, null
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

                if (adapter != null) {
                    binding.fragmentApexListHeaderCountItemTv.text =
                        adapter?.search(s.toString()).toString() + " مورد"

                }
            }
        })
        this.view?.setOnKeyListener(object : DialogInterface.OnKeyListener,
            View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == KeyEvent.KEYCODE_BACK) {
                    requireActivity().onBackPressed()
                    return true
                }
                return false
            }

            override fun onKey(p0: DialogInterface?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == KeyEvent.KEYCODE_BACK) {
                    requireActivity().onBackPressed()
                    return true

                }
                return false
            }
        })
    }

    private fun observer() {
        viewModel.apexListHeaderLiveData.observe(viewLifecycleOwner) {
            binding.fragmentApexListHeaderCountItemTv.text = it.size.toString() + " مورد"
            if (it.isNotEmpty()) {
                binding.fragmentApexListHeaderApexListRv.visibility = View.VISIBLE
                binding.fragmentApexListHeaderApexListRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = ApexListHeaderItemAdapter(it as ArrayList<ApexListHeader>, this,args.namePage)
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
        var bottomSheetDialog = EditApexBottomSheetFragment(apexListHeader,args.namePage)
        bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
    }

    override fun deleteApexListHeader(apexListHeader: ApexListHeader) {
        viewModel.deleteApexListHeader(apexListHeader)
        Toast.makeText(requireContext(), "گروه ${args.namePage.getValue()} حذف شد", Toast.LENGTH_SHORT)
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

    override fun emptySearch(show: Boolean) {
        if (show) {
            binding.fragmentApexListHeaderEmptyLayout.root.visibility = View.VISIBLE
            binding.fragmentApexListHeaderApexListRv.visibility = View.GONE

        } else {
            binding.fragmentApexListHeaderEmptyLayout.root.visibility = View.GONE
            binding.fragmentApexListHeaderApexListRv.visibility = View.VISIBLE

        }
    }
}