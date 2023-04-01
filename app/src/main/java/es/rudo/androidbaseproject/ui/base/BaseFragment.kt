package es.rudo.androidbaseproject.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseViewModel, VB: ViewDataBinding> : Fragment() {

    lateinit var viewModel: VM
    lateinit var binding: VB
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var fragmentClass: Class<BaseFragment<VM, VB>> = javaClass
        while (fragmentClass.genericSuperclass !is ParameterizedType) {
            fragmentClass = fragmentClass.superclass as Class<BaseFragment<VM, VB>>
        }
        val type = fragmentClass.genericSuperclass as ParameterizedType
        viewModel = ViewModelProvider(this).get(type.actualTypeArguments[0] as Class<VM>)
        arguments?.let { viewModel.initData(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, layoutId, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    protected abstract fun setUpViews()
}