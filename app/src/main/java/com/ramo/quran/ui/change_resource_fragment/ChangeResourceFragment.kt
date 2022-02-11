package com.ramo.quran.ui.change_resource_fragment

import android.os.Bundle
import android.view.View
import com.ramo.core.ext.invisible
import com.ramo.core.ext.observe
import com.ramo.core.ext.visible
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.databinding.FragmentChangeResourceBinding
import com.ramo.quran.databinding.RecyclerResourceItemBinding
import com.ramo.quran.model.db_translation.ResourceWithLanguage
import com.ramo.quran.ui.MainActivity
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeResourceFragment :
    BaseFragment<FragmentChangeResourceBinding, ChangeResourceViewModel>() {

    private val sweetRecyclerAdapter = SweetRecyclerAdapter<ResourceWithLanguage>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.change_resource)
        initUi()
        initObserver()
        viewModel.getData()
    }

    private fun initUi() {
        sweetRecyclerAdapter.addHolder(R.layout.recycler_resource_item) { v, item ->
            val binding = RecyclerResourceItemBinding.bind(v)

            with(binding) {
                resourceLanguage.text = item.language.name
                resource.text = item.resource.name
                if (viewModel.currentResourceId.value == item.resource.id)
                    isCheck.visible()
                else
                    isCheck.invisible()
            }
        }
        sweetRecyclerAdapter.setOnItemClickListener { _, item ->
            sweetRecyclerAdapter.notifyDataSetChanged()
            viewModel.updateCurrentResource(item.resource.id!!)
        }
        binding.recyclerViewResource.adapter = sweetRecyclerAdapter
    }

    private fun initObserver() {
        observe(viewModel.resourceList) { resourceList ->
            sweetRecyclerAdapter.submitList(resourceList)
        }
        observe(viewModel.currentResourceId) {
            sweetRecyclerAdapter.notifyDataSetChanged()
        }
    }
}