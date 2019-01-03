package online.ahndwon.photogallery

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class PhotoPageActivity : SingleFragmentActivity() {

    companion object {
        fun newIntent(context: Context, photoPageUri: Uri) : Intent {
            val intent = Intent(context, PhotoPageActivity::class.java)
            intent.data = photoPageUri
            return intent
        }
    }

    override fun createFragment(): Fragment {
        intent?.data?.let {
            return PhotoPageFragment.newInstance(it)
        }
        return PhotoPageFragment()
    }
}
