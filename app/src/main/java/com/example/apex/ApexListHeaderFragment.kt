package com.example.apex

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentApexListHeaderBinding
import kotlinx.android.synthetic.main.fragment_apex_items.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApexListHeaderFragment : Fragment(), ApexListHeaderItemAdapter.EventListener,
    SortApexListHeaderDialogFragment.EventListeners {
    private lateinit var binding: FragmentApexListHeaderBinding
    private val viewModel: ApexViewModel by viewModel()
    private var adapter: ApexListHeaderItemAdapter? = null
    private val args: ApexListHeaderFragmentArgs by navArgs()
    private var sort = 0


    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
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


        binding.fragmentApexListHeaderSortBtn.setOnClickListener {
            val sortDialogFragment = SortApexListHeaderDialogFragment(this.sort, this)
            sortDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentApexListHeaderAddBtn.setOnClickListener {
            val inputMethodManager = this.requireContext()
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

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

            @SuppressLint("SetTextI18n")
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

        binding.fragmentApexListHeaderBackBtn.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
        this.view?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == KeyEvent.KEYCODE_BACK) {
                    binding.fragmentApexListHeaderBackBtn.callOnClick()
                    return true
                }
                return false
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.apexListHeaderLiveData.observe(viewLifecycleOwner) {
            binding.fragmentApexListHeaderCountItemTv.text = it.size.toString() + " مورد"
            if (it.isNotEmpty()) {
                binding.fragmentApexListHeaderApexListRv.visibility = View.VISIBLE
                binding.fragmentApexListHeaderApexListRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter =
                    ApexListHeaderItemAdapter(it as ArrayList<ApexListHeader>, this, args.namePage)
                adapter?.sort(sort)
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
        val bottomSheetDialog = EditApexBottomSheetFragment(apexListHeader, args.namePage)
        bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
    }

    override fun deleteApexListHeader(apexListHeader: ApexListHeader) {
        viewModel.deleteApexListHeader(apexListHeader)
        Toast.makeText(
            requireContext(),
            "گروه ${args.namePage.getValue()} حذف شد",
            Toast.LENGTH_SHORT
        )
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

    override fun changeSort(id: Int) {
        this.sort = id
        if (adapter != null)
            adapter?.sort(id)
    }
}