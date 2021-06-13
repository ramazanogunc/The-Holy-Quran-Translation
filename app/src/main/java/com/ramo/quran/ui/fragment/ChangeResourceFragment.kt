package com.ramo.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ramo.quran.R
import com.ramo.quran.model.Resource
import com.ramo.quran.ui.MainActivity

class ChangeResourceFragment : Fragment() {


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
        /*
        db.getResources(object : SqliteResponse<List<Resource>> {
            override fun onSuccess(response: List<Resource>) {
                prepareUi(response)
            }

            override fun onFail(failMessage: String) {
                activity?.showError()
            }
        })

         */
    }

    private fun prepareUi(listResource: List<Resource>) {
        /*
        recyclerViewResource.apply {
            adapter = ResourceRecyclerAdapter {
                //onClick event with higher order and this return parameter from bind function in the view holder
                //db.changeResource(it)
                getData()
                requireActivity().showSuccess()
            }
            //you can set layout manager in xml with 'layoutManager' attr
            layoutManager = LinearLayoutManager(activity)
        }
        (recyclerViewResource.adapter as ResourceRecyclerAdapter).submitList(listResource)

         */
    }
}