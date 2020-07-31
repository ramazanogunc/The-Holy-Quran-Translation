package com.ramo.quran.dataAccess

import android.content.Context
import com.ramo.quran.R
import com.ramo.quran.dataAccess.abstr.SqliteResponse
import com.ramo.quran.model.*
import com.ramo.quran.ui.fragment.ReadFragment
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class LocalSqliteHelper(
    context: Context?,
    private val DATABASE_NAME: String = "kuran.db",
    private val DATABASE_VERSION: Int = 1
) : SQLiteAssetHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    private val currentSurahWithVersicles = "SELECT * FROM versicles WHERE resourceId=(SELECT currentResourceId FROM configs) AND nameOfSurahId=(SELECT currentSurahId FROM configs) ORDER BY versicleNo"
    private val nameAndNumberOfSurah = "SELECT (SELECT name FROM nameOfSurahs WHERE nameOfSurahs.id=configs.currentSurahId) as nameOfSurah, (configs.currentSurahId%114) as numberOfSurah FROM configs"
    private val nextSurah = "UPDATE configs SET currentSurahId=((SELECT currentSurahId FROM configs)+1)"
    private val prevouseSurah = "UPDATE configs SET currentSurahId=((SELECT currentSurahId FROM configs)-1)"
    private val nameOfSurahs = "SELECT * FROM nameOfSurahs WHERE languageId=(SELECT languageId FROM configs)"
    private val changeSurah = "UPDATE configs SET currentSurahId="
    private val getResources = "SELECT resources.id, resources.name , languages.name as languageName,(SELECT currentResourceId FROM configs) as currentResourceId FROM resources, languages WHERE languages.id=resources.language"
    private val changeResource = "UPDATE configs SET currentResourceId="
    private val getConfig = "SELECT * ,(SELECT name FROM languages WHERE id=configs.languageId) as languageName FROM configs"
    private val getAllLanguages = "SELECT * FROM languages"
    private val changeConfig = "UPDATE configs SET languageId="

    val dataNotFound = context?.let { it.getString(R.string.data_not_found) } ?: "veri bulunamadı | not found"

    fun getSurah(sqliteResponse: SqliteResponse<Surah>) {
        var result = readableDatabase.rawQuery(currentSurahWithVersicles, null)
        if (result.count == 0 || !result.moveToFirst()){
            sqliteResponse.onFail(dataNotFound)
            return
        }
        val versicleMutableList : MutableList<Versicle> = mutableListOf();
        val surahId = result.getInt(result.getColumnIndex("nameOfSurahId"))
        do {
            versicleMutableList.add(
                Versicle(
                    id = result.getInt(result.getColumnIndex("id")),
                    no = result.getInt(result.getColumnIndex("versicleNo")),
                    text = result.getString(result.getColumnIndex("text"))
                )
            )
        }while (result.moveToNext())
        result = readableDatabase.rawQuery(nameAndNumberOfSurah, null)
        if (result.count == 0 || !result.moveToFirst()){
            sqliteResponse.onFail(dataNotFound)
            return
        }
        val name = result.getString(result.getColumnIndex("nameOfSurah"))
        val number = result.getInt(result.getColumnIndex("numberOfSurah"))
        val surah: Surah = Surah(surahId,name,number,versicleMutableList.toList())
        sqliteResponse.onSuccess(surah)
    }

    fun nextSurah(){
        return writableDatabase.execSQL(nextSurah)
    }

    fun prevouseSurah(){
        return writableDatabase.execSQL(prevouseSurah)
    }

    fun getNameOfSurahs(sqliteResponse: SqliteResponse<List<NameOfSurah>>){
        var result = readableDatabase.rawQuery(nameOfSurahs, null)
        if (result.count == 0 || !result.moveToFirst()){
            sqliteResponse.onFail(dataNotFound)
            return
        }
        val nameOfSurahMutableList : MutableList<NameOfSurah> = mutableListOf();
        do {
            nameOfSurahMutableList.add(
                NameOfSurah(
                    id = result.getInt(result.getColumnIndex("id")),
                    name = result.getString(result.getColumnIndex("name"))
                )
            )
        }while (result.moveToNext())

        sqliteResponse.onSuccess(nameOfSurahMutableList.toList())
    }

    fun changeSurah(nameOfSurah: NameOfSurah){
        return writableDatabase.execSQL(changeSurah+nameOfSurah.id)
    }

    fun getResources(sqliteResponse: SqliteResponse<List<Resource>>){
        var result = readableDatabase.rawQuery(getResources, null)
        if (result.count == 0 || !result.moveToFirst()){
            sqliteResponse.onFail(dataNotFound)
            return
        }
        val resourceMutableList : MutableList<Resource> = mutableListOf()
        do {
            resourceMutableList.add(
                Resource(
                    id = result.getInt(result.getColumnIndex("id")),
                    name = result.getString(result.getColumnIndex("name")),
                    isActive = if (result.getInt(result.getColumnIndex("id")) ==
                        result.getInt(result.getColumnIndex("currentResourceId"))) true else false,
                    language = Language(id = 0, name =  result.getString(result.getColumnIndex("languageName")))
                )
            )
        }while (result.moveToNext())

        sqliteResponse.onSuccess(resourceMutableList.toList())
    }

    fun changeResource(resource: Resource){
        return writableDatabase.execSQL(changeResource+resource.id)
    }

    fun getAllConfig(sqliteResponse: SqliteResponse<Config>){
        var result = readableDatabase.rawQuery(getConfig, null)
        if (result.count == 0 || !result.moveToFirst()){
            sqliteResponse.onFail(dataNotFound)
            return
        }

        val config = Config(
            textSize = result.getInt(result.getColumnIndex("fontSize")),
            language = Language(id = result.getInt(result.getColumnIndex("languageId")),
                                name = result.getString(result.getColumnIndex("languageName"))
                                )
        )
        sqliteResponse.onSuccess(config)
    }

    fun getAllLanguages(sqliteResponse: SqliteResponse<List<Language>>){
        var result = readableDatabase.rawQuery(getAllLanguages, null)
        if (result.count == 0 || !result.moveToFirst()){
            sqliteResponse.onFail(dataNotFound)
            return
        }
        val langualgeMutableList : MutableList<Language> = mutableListOf()
        do {
            langualgeMutableList.add(
                Language(
                    id = result.getInt(result.getColumnIndex("id")),
                    name = result.getString(result.getColumnIndex("name"))
                )
            )
        }while (result.moveToNext())
        sqliteResponse.onSuccess(langualgeMutableList.toList())
    }

    fun updateConfig(config: Config){
        return writableDatabase.execSQL(changeConfig+config.language.id+",fontSize="+config.textSize)
    }

}