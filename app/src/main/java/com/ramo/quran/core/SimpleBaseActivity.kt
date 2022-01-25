package com.ramo.quran.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ramo.quran.core.ext.findGenericWithType
import java.lang.reflect.Method

abstract class SimpleBaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foundInflater = findInflateMethod()
        @Suppress("UNCHECKED_CAST")
        _binding = foundInflater.invoke(null, layoutInflater) as VB
        setContentView(binding.root)
    }

    protected fun withVB(block: VB.() -> Unit) = with(binding, block)


    private fun findInflateMethod(): Method {
        val clazz = javaClass.findGenericWithType<VB>(0)
        return clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
        )
    }
}