package com.ramo.quran.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ramo.quran.R
import com.ramo.quran.core.SimpleBaseFragment
import com.ramo.quran.core.ext.invisible
import com.ramo.quran.core.ext.visible
import com.ramo.quran.data.AppDatabase
import com.ramo.quran.databinding.FragmentChangeResourceBinding
import com.ramo.quran.ext.showSuccess
import com.ramo.quran.model.Config
import com.ramo.quran.model.ResourceWithLanguage
import com.ramo.quran.ui.MainActivity
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangeResourceFragment : SimpleBaseFragment<FragmentChangeResourceBinding>() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private val sweetRecyclerAdapter = SweetRecyclerAdapter<ResourceWithLanguage>()
    private var config: Config? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.change_resource)
        getData()
    }

    private fun getData() {
        val resourceList = appDatabase.resourceDao.getAllResourcesWithLanguage()
        config = appDatabase.configDao.getConfig()
        prepareUi(resourceList)
    }

    private fun prepareUi(resourceList: List<ResourceWithLanguage>) {
        sweetRecyclerAdapter.addHolder(R.layout.recycler_resource_item) { v, item ->
            val resourceLanguage = v.findViewById<TextView>(R.id.resourceLanguage)
            val resource = v.findViewById<TextView>(R.id.resource)
            val isCheck = v.findViewById<ImageView>(R.id.isCheck)

            resourceLanguage.text = item.language.name
            resource.text = item.resource.name
            if (config?.currentResourceId == item.resource.id)
                isCheck.visible()
            else
                isCheck.invisible()
        }
        sweetRecyclerAdapter.setOnItemClickListener { _, item ->
            sweetRecyclerAdapter.notifyDataSetChanged()
            appDatabase.configDao.updateCurrentResource(item.resource.id!!)
            requireActivity().showSuccess()
            refreshDataAndUi()
        }
        withVB {
            recyclerViewResource.adapter = sweetRecyclerAdapter
        }
        sweetRecyclerAdapter.submitList(resourceList)
    }

    private fun refreshDataAndUi() {
        config = appDatabase.configDao.getConfig()
        sweetRecyclerAdapter.notifyDataSetChanged()
    }
}