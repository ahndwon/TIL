package online.ahndwon.photogallery


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class PhotoPageFragment : Fragment() {

    private var mUri : Uri? = null
    private var mWebView : WebView? = null
    private var mProgressBar : ProgressBar? = null

    companion object {
        const val ARG_URI = "photo_page_url"
        fun newInstance(uri: Uri) : PhotoPageFragment {
            val args = Bundle()
            args.putParcelable(ARG_URI, uri)

            val fragment = PhotoPageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUri = arguments?.getParcelable(ARG_URI)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo_page, container, false)
        mProgressBar = view.findViewById(R.id.fragment_photo_page_progress_bar)
        mProgressBar?.max = 100
        mWebView = view.findViewById(R.id.fragment_photo_page_web_view)
        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    mProgressBar?.visibility = View.GONE
                } else {
                    mProgressBar?.visibility = View.VISIBLE
                    mProgressBar?.progress = newProgress
                }
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                val activity = activity as? AppCompatActivity
                activity?.supportActionBar?.subtitle = title
            }
        }

        mWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }
        mWebView?.loadUrl(mUri.toString())

        return view
    }


}
