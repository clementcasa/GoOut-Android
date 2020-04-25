package io.clemcasa.goout.app.utils

import android.content.Context
import android.os.Environment
import android.util.Base64
import android.webkit.JavascriptInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.util.Date

interface JSHelperDelegate {
    fun didCreateFile()
}

class JSHelper(
    private val context: Context,
    private val delegate: JSHelperDelegate
) {
    companion object {
        fun getBase64StringFromBlobUrl(blobUrl: String): String {
            if (blobUrl.startsWith("blob")) {
                return "javascript: var xhr = new XMLHttpRequest();" +
                        "xhr.open('GET', '"+ blobUrl +"', true);" +
                        "xhr.setRequestHeader('Content-type','application/pdf');" +
                        "xhr.responseType = 'blob';" +
                        "xhr.onload = function(e) {" +
                        "    if (this.status == 200) {" +
                        "        var blobPdf = this.response;" +
                        "        var reader = new FileReader();" +
                        "        reader.readAsDataURL(blobPdf);" +
                        "        reader.onloadend = function() {" +
                        "            base64data = reader.result;" +
                        "            Android.getBase64FromBlobData(base64data);" +
                        "        }" +
                        "    }" +
                        "};" +
                        "xhr.send();"
            }
            return "javascript: console.log('It is not a Blob URL');"
        }
    }
    
    @JavascriptInterface
    @Throws(IOException::class)
    fun getBase64FromBlobData(base64Data: String?) {
        convertBase64StringToPdfAndStoreIt(base64Data ?: "")
    }
    
    @Throws(IOException::class)
    private fun convertBase64StringToPdfAndStoreIt(base64PDf: String) {
        val currentDateTime = DateFormat.getDateTimeInstance().format(Date())
        val fileName = "$currentDateTime.pdf"
        val file = File(context.filesDir, fileName)
        val pdfData = base64PDf.replaceFirst("^data:application/pdf;base64,".toRegex(), "")
        val pdfAsBytes = Base64.decode(pdfData, 0)
        context.openFileOutput(fileName, Context.MODE_PRIVATE)
                .use { fos ->
                    fos.write(pdfAsBytes)
                    fos.flush()
                }
        if (file.exists()) {
            delegate.didCreateFile()
        }
    }
}