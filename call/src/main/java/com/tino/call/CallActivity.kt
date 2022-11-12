package com.tino.call

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.permissionx.guolindev.PermissionX
import java.io.File


@Route(path = "/Call/CallActivity")
class CallActivity : AppCompatActivity() {

    @JvmField
    @Autowired
    var from = ""

    lateinit var web : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

//        ARouter.getInstance().inject(this);
        val tv = this.findViewById<TextView>(R.id.tv_call)
        val bt1 = this.findViewById<Button>(R.id.bt1)
        val bt2 = this.findViewById<Button>(R.id.bt2)
        web = this.findViewById(R.id.web_view)
//        tv.text = from



        initWeb()

        bt1.setOnClickListener {
//            FixDexUtils.loadFixedDex(this, File(Environment.getExternalStorageDirectory().toString()+"/aaa"))

            /**
             * 无返回值，会重新加载页面，效率低
             */
//            web.post {
//                kotlin.run {
//                    //第一种方法 通过loadUrl调用JS代码
//                    //调用无参JS方法
//                    web.loadUrl("javascript:setRed()")
//                    //调用有参JS方法
//                    web.loadUrl("javascript:clickJS('我调用了JS的方法fuck')")
//                }
//            }

            /**
             * 有返回值，局部加载页面，效率高，Android4.4以上才能用
             */
            web.evaluateJavascript("javascript:clickJS()") {
                //    这里返回JS的结果

            }
        }

        bt2.setOnClickListener {
            tv.text = Fuck().getStr()
        }


        PermissionX.init(this)
            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                //拒绝后回调，可以解释需要该权限的理由
                scope.showRequestReasonDialog(deniedList, "功能正常使用基于这些权限，轻允许使用该权限", "OK", "Cancel")
            }
            .onForwardToSettings { scope, deniedList ->
                //拒绝且不在申请时候调用
                scope.showForwardToSettingsDialog(deniedList, "您需要手动在设置中允许必要的权限", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Log.e("AAA","发送消息")
                    //todo申请完所有权限

                } else {
                    //部分权限没有申请
                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun initWeb() {
        val set = web.settings
        //支持javascript
        set.javaScriptEnabled  = true
        //将图片调整到适合webview的大小
        set.useWideViewPort = true
        // 缩放至屏幕的大小
        set.loadWithOverviewMode = true

        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        set.setSupportZoom(true)
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        set.builtInZoomControls = true
        //隐藏原生的缩放控件
        set.displayZoomControls = false
        // 文件操作
        set.allowFileAccess = true

        //启用数据库
        set.databaseEnabled = true
        set.javaScriptCanOpenWindowsAutomatically = true
        //不设置可能会导致js调用失败
        set.domStorageEnabled = true

        //加载速度优化：先加载文字后加载图片
        set.blockNetworkImage = true
        set.loadsImagesAutomatically = true
        set.setAppCacheEnabled(true)

        val btunclick =BtnClick()

        web.addJavascriptInterface(btunclick,"androids")

        web.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.e("AAA","newProgress = $newProgress")
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                Log.e("AAA","title = $title")
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }
        }

//        web.loadUrl("https://www.baidu.com")

        web.loadUrl("file:///android_asset/haha.html")
    }



    inner class BtnClick{
        @JavascriptInterface
        fun click0(){
            Log.e("AAA","被点击了")
        }

        @JavascriptInterface
        fun jsAndroid(str:String){
            Log.e("AAA","被点击了+$str")
        }
    }
}