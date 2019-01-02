package online.ahndwon.photogallery


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class PhotoPageFragment : Fragment() {

    private var mUri : Uri? = null
    private var mWebView : WebView? = null

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
        mWebView = view.findViewById(R.id.fragment_photo_page_web_view)
        return view
    }


}
