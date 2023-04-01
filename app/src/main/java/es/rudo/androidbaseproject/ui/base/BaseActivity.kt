package es.rudo.androidbaseproject.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import es.rudo.androidbaseproject.BR
import es.rudo.androidbaseproject.R
import es.rudo.androidbaseproject.data.helpers.HttpCodeError
import es.rudo.androidbaseproject.data.helpers.NoInternetError
import es.rudo.androidbaseproject.data.helpers.ResultError
import es.rudo.androidbaseproject.data.helpers.RetrofitError
import es.rudo.androidbaseproject.helpers.extensions.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<VM: BaseViewModel, VB: ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var binding: VB

    private var progressContainer: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        var activityClass: Class<in BaseActivity<VM, VB>> = javaClass
        while (activityClass.genericSuperclass !is ParameterizedType) {
            activityClass = activityClass.superclass as Class<in BaseActivity<VM, VB>>
        }

        val type = activityClass.genericSuperclass as ParameterizedType
        viewModel = ViewModelProvider(this)[type.actualTypeArguments[0] as Class<VM>]

        binding.setVariable(BR.activity, this)
        binding.setVariable(BR.viewModel, viewModel)

        setContentView(binding.root)
        intent.extras?.let {
            viewModel.initData(it)
        }

        setProgressContainer()
        setUpViews()
    }

    protected abstract fun setUpViews()

    fun <T> background(callback: ((T?) -> Unit)? = null, process: suspend () -> T?) =
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                process()
            }

            callback?.let {
                withContext(Dispatchers.Main) {
                    callback(result)
                }
            }
        }

    private fun setProgressContainer() {
        progressContainer = findViewById(R.id.progress_container)
    }

    /**
     * Manage progress container
     */

    protected fun showProgressContainer() {
        progressContainer?.let {
            it.visibility = View.VISIBLE
        }
    }

    protected fun hideProgressContainer() {
        progressContainer?.let {
            it.visibility = View.GONE
        }
    }

    fun <T> Flow<T>.collectOnLifecycle(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        block: (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(state) {
                collect {
                    block(it)
                }
            }
        }
    }

    fun handleError(error: ResultError) {
        when (error) {
            is HttpCodeError.NotFound -> toast(R.string.error_not_found)
            is HttpCodeError.InternalServerError -> toast(
                R.string.error_internal_server
            )
            is NoInternetError -> toast(R.string.error_internet_connection)
            is RetrofitError.HttpException -> toast("Error HTTP")
            else -> {}
        }
    }
}
