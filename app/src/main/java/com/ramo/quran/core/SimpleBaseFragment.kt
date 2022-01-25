package com.ramo.quran.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ramo.quran.core.ext.findGenericWithType
import java.lang.reflect.Method

abstract class SimpleBaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val foundInflater = findInflateMethod()
        @Suppress("UNCHECKED_CAST")
        _binding = foundInflater.invoke(null, inflater, container, false) as VB
        return _binding!!.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun withVB(block: VB.() -> Unit) = with(binding, block)

    private fun findInflateMethod(): Method {
        val clazz = javaClass.findGenericWithType<VB>(0)
        return clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
    }

}