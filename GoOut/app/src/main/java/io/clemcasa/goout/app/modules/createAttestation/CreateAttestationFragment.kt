package io.clemcasa.goout.app.modules.createAttestation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.clemcasa.goout.app.modules.main.MainViewDelegate
import io.clemcasa.goout.app.utils.JSHelper
import io.clemcasa.goout.app.utils.JSHelperDelegate
import io.clemcasa.goout.databinding.FragmentCreateAttestationBinding
import io.clemcasa.goout.presenter.modules.createAttestation.CreateAttestationPresenterImpl
import io.clemcasa.goout.presenter.modules.createAttestation.CreateAttestationView

class CreateAttestationFragment : BottomSheetDialogFragment(), JSHelperDelegate, CreateAttestationView {
    
    private var _binding: FragmentCreateAttestationBinding? = null
    private val binding get() = _binding!!
    
    private val presenter = CreateAttestationPresenterImpl()
    
    var delegate: MainViewDelegate? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        
        _binding = FragmentCreateAttestationBinding.inflate(layoutInflater)
        
        presenter.attach(this)
        
        setupUI()
        setupWebview()
        
        return binding.root
    }
    
    private fun setupUI() {
        binding.checkboxGroceries.setOnCheckedChangeListener { _, isChecked ->
            presenter.setGroceriesCheckBox(isChecked)
        }
        binding.checkboxWalk.setOnCheckedChangeListener { _, isChecked ->
            presenter.setWalkCheckBox(isChecked)
        }
        binding.checkboxDoctor.setOnCheckedChangeListener { _, isChecked ->
            presenter.setDoctorCheckBox(isChecked)
        }

        binding.createAttestationButton.setOnClickListener {
            presenter.didClickOnCreate()
        }
    }
    
    private fun setupWebview() {
        val activity = activity ?: return
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webview.settings.setSupportMultipleWindows(true)
        binding.webview.settings.setSupportZoom(true)
        binding.webview.settings.allowFileAccess = true
        binding.webview.settings.domStorageEnabled = true
        val jsHelper = JSHelper(activity, this)
        binding.webview.addJavascriptInterface(jsHelper, "Android")
        binding.webview.setDownloadListener { p0, _, _, _, _ ->
            p0?.let { blobUrl ->
                binding.webview.loadUrl(JSHelper.getBase64StringFromBlobUrl(blobUrl))
            }
        }
        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                onHideLoading()
                binding.checkboxGroceries.isChecked = true
                presenter.setGroceriesCheckBox(true)
                presenter.fillForm()
            }
        }
        binding.webview.loadUrl("https://media.interieur.gouv.fr/deplacement-covid-19/")
        onShowLoading()
    }
    
    override fun onDetach() {
        delegate?.didCreateNewAttestation()
        super.onDetach()
    }
    
    override fun didCreateFile() {
        dismiss()
    }
    
    companion object {
        fun newInstance(model: Bundle): CreateAttestationFragment = CreateAttestationFragment().apply { arguments = model }
    }
    
    override fun onShowLoading() {
        with(binding) {
            createAttestationButton.visibility = View.INVISIBLE
            checkBoxContainer.visibility = View.INVISIBLE
            loadingView.visibility = View.VISIBLE
        }
    }
    
    fun onHideLoading() {
        with(binding) {
            createAttestationButton.visibility = View.VISIBLE
            checkBoxContainer.visibility = View.VISIBLE
            loadingView.visibility = View.GONE
        }
    }
    
    override fun onPerformJSScript(jsScript: String) {
        binding.webview.evaluateJavascript(jsScript, null)
    }
}