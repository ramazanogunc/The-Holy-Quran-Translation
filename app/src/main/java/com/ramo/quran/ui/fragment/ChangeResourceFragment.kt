package com.ramo.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ramo.quran.R
import com.ramo.quran.helper.invisible
import com.ramo.quran.helper.show
import com.ramo.quran.helper.showSuccess
import com.ramo.quran.model.Config
import com.ramo.quran.model.ResourceWithLanguage
import com.ramo.quran.ui.MainActivity
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_change_resource.*

class ChangeResourceFragment : HasDatabaseFragment() {

    private val sweetRecyclerAdapter = SweetRecyclerAdapter<ResourceWithLanguage>()
    private var config: Config? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_resource, container, false)
    }

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
                isCheck.show()
            else
                isCheck.invisible()
        }
        sweetRecyclerAdapter.setOnItemClickListener { v, item ->
            sweetRecyclerAdapter.notifyDataSetChanged()
            appDatabase.configDao.updateCurrentResource(item.resource.id!!)
            requireActivity().showSuccess()
            refreshDataAndUi()
        }
        recyclerViewResource.adapter = sweetRecyclerAdapter
        sweetRecyclerAdapter.submitList(resourceList)
    }

    private fun refreshDataAndUi() {
        config = appDatabase.configDao.getConfig()
        sweetRecyclerAdapter.notifyDataSetChanged()
    }
}