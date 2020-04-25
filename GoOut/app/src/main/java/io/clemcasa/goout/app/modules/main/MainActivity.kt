package io.clemcasa.goout.app.modules.main

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.webkit.DownloadListener
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.clemcasa.goout.R
import io.clemcasa.goout.app.modules.createAttestation.CreateAttestationFragment
import io.clemcasa.goout.app.modules.main.adapter.AttestationListAdapter
import io.clemcasa.goout.app.modules.main.itemdecoration.SpaceItemDecoration
import io.clemcasa.goout.app.utils.JavaScriptInterface
import io.clemcasa.goout.databinding.ActivityMainBinding
import java.net.URLDecoder
import java.security.AccessController.getContext

class MainActivity: AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        
        setupUI()
        val check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_GRANTED) {
            //Do something
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1024)
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_delete_all -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun setupUI() {
        binding.webview.settings.javaScriptEnabled = true;
        binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true;
        binding.webview.settings.setSupportMultipleWindows(true);
        binding.webview.settings.setSupportZoom(true);
        binding.webview.settings.allowFileAccess = true;
        binding.webview.settings.domStorageEnabled = true;
        val toto = JavaScriptInterface(this)
        binding.webview.addJavascriptInterface(toto, "Android");
        binding.webview.webChromeClient = object: WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
    
            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsConfirm(view, url, message, result)
            }
        }
        binding.webview.setDownloadListener(object: DownloadListener {
            override fun onDownloadStart(p0: String?, p1: String?, p2: String?, p3: String?, p4: Long) {
                JavaScriptInterface.getBase64StringFromBlobUrl(p0)
                //webview.loadUrl(p0)
                //binding.webview.loadUrl(URLDecoder.decode(p0, "UTF-8"))
                binding.webview.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(p0));
                /*
                val request = DownloadManager.Request(Uri.parse(p0?.replace("blob:", "")))
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Is it working")
                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(applicationContext, "Downloading File",  //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG)
                        .show()
                        
                 */
            }
        })
        binding.webview.setWebViewClient(object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //view.loadUrl(url)
                return false
            }
    
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }
    
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }
        
            override fun onPageFinished(view: WebView, url: String) {
                print(url)
            }
        })
        binding.webview.loadUrl("https://media.interieur.gouv.fr/deplacement-covid-19/")
        
        setSupportActionBar(binding.toolbar)
        with(binding) {
            val attestationListAdapter = AttestationListAdapter()
            val linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            with(binding.recyclerView) {
                layoutManager = linearLayoutManager
                adapter = attestationListAdapter
                addItemDecoration(SpaceItemDecoration((12 * (context?.resources?.displayMetrics?.density ?: 0.0f)).toInt()))
            }
            
            createAttestationButton.setOnClickListener { view ->
                CreateAttestationFragment.newInstance(Bundle()).show(supportFragmentManager, CreateAttestationFragment::class.java.name)
            }
        }
    }
}
