package conextSurahm.ramo.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.quran.R
import com.ramo.quran.dataAccess.LocalSqliteHelper
import com.ramo.quran.dataAccess.abstr.SqliteResponse
import com.ramo.quran.helper.showError
import com.ramo.quran.helper.showSuccess
import com.ramo.quran.model.NameOfSurah
import com.ramo.quran.model.Resource
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.ui.adapter.ReadRecyclerAdapter
import com.ramo.quran.ui.adapter.ResourceRecyclerAdapter
import com.ramo.quran.ui.adapter.onResourceClickListener
import kotlinx.android.synthetic.main.fragment_change_resource.*
import kotlinx.android.synthetic.main.fragment_read.*

class ChangeResourceFragment: Fragment(), onResourceClickListener {

    lateinit var db: LocalSqliteHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_resource,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.change_resource)
        activity?.let {
            db = LocalSqliteHelper(it)
        }
        getData()

    }

    private fun getData(){
        db.getResources(object : SqliteResponse<List<Resource>> {
            override fun onSuccess(response: List<Resource>) {
                prepareUi(response)
            }

            override fun onFail(failMessage: String) {
                activity?.showError()
            }
        })
    }

    private fun prepareUi(listResource: List<Resource>){
        recyclerViewResource.adapter = ResourceRecyclerAdapter(listResource,this)
        recyclerViewResource.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResourceClick(resource: Resource) {
        db.changeResource(resource)
        getData()
        activity?.showSuccess()
    }
}