package com.ramo.quran.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.quran.R
import com.ramo.quran.dataAccess.LocalSqliteHelper
import com.ramo.quran.dataAccess.abstr.SqliteResponse
import com.ramo.quran.helper.showError
import com.ramo.quran.model.Config
import com.ramo.quran.model.NameOfSurah
import com.ramo.quran.model.Surah
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.ui.adapter.ReadRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_read.*

class ReadFragment : Fragment(), SqliteResponse<Surah> {
    private lateinit var db: LocalSqliteHelper
    private lateinit var surahDialog: AlertDialog
    private var fontSize = 15

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.read_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeSurah -> {
                activity?.let {
                    surahDialog.show()
                }

            }
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.let {
            db = LocalSqliteHelper(it)
        }

        initUi()

    }

    private fun initUi() {
        getFontSize()
        db.getSurah(this)

        // fab on clicks
        fabRight.setOnClickListener { onRightFabClick(it) }
        fabLeft.setOnClickListener { onLeftFabClick(it) }

        initSurahDialog()


    }

    private fun getFontSize() {
        db.getAllConfig(object :SqliteResponse<Config>{
            override fun onSuccess(response: Config) {
                fontSize = response.textSize
            }

            override fun onFail(failMessage: String) {
                activity?.showError()
            }
        })
    }

    private fun initSurahDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.choice_surah))
        lateinit var surahArray: Array<NameOfSurah>

        db.getNameOfSurahs(object : SqliteResponse<List<NameOfSurah>> {
            override fun onSuccess(response: List<NameOfSurah>) {
                surahArray = response.toTypedArray()
            }

            override fun onFail(failMessage: String) {
                surahArray = arrayOf<NameOfSurah>(NameOfSurah(name = failMessage))
            }
        })
        val stringArray: List<String> = surahArray.map { it.name }
        builder.setItems(
            stringArray.toTypedArray(),
            DialogInterface.OnClickListener { dialog, which ->
                changeSurah(surahArray[which])
            })

        surahDialog = builder.create()
    }

    private fun changeSurah(nameOfSurah: NameOfSurah) {
        db.changeSurah(nameOfSurah)
        db.getSurah(this)
    }

    fun onLeftFabClick(v: View) {
        db.prevouseSurah()
        db.getSurah(this)
    }

    fun onRightFabClick(v: View) {
        db.nextSurah()
        db.getSurah(this)
    }

    private fun prepareFabButton(surahNo: Int) {
        fabRight?.let {
            it.isEnabled = true
        }
        fabLeft?.let {
            it.isEnabled = true
        }
        // check first or last surah
        if (surahNo == 114)
            fabRight?.let {
                it.isEnabled = false
            }
        else if (surahNo == 1)
            fabLeft?.let {
                it.isEnabled = false
            }

    }

    // surah response
    override fun onSuccess(response: Surah) {
        prepareView(response)
        prepareFabButton(response.surahNo)
    }

    // surah response
    override fun onFail(failMessage: String) {
        activity?.showError()
    }

    private fun prepareView(surah: Surah) {
        recyclerViewRead.apply {
            adapter = ReadRecyclerAdapter(fontSize)
            layoutManager = LinearLayoutManager(activity)
        }
        (recyclerViewRead.adapter as ReadRecyclerAdapter).submitList(surah.versicles)

        (activity as MainActivity).supportActionBar?.title = surah.name
        surahNumber.text = surah.surahNo.toString()
        versicleSize.text = surah.versicles.size.toString()
    }

}