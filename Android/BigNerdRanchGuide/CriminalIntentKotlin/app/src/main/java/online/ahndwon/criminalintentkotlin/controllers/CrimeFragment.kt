package online.ahndwon.criminalintentkotlin.controllers

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_crime.*
import kotlinx.android.synthetic.main.fragment_crime.view.*
import kotlinx.android.synthetic.main.view_camera_and_title.*
import kotlinx.android.synthetic.main.view_camera_and_title.view.*
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime
import online.ahndwon.criminalintentkotlin.models.CrimeLab
import online.ahndwon.criminalintentkotlin.utils.PictureUtils
import java.io.File
import java.util.*


class CrimeFragment : Fragment() {

    private var mCrime = Crime()
    private var photoFile: File? = null
    var onCrimeUpdate: ((Crime) -> Unit)? = null

    companion object {
        val TAG: String = CrimeFragment::class.java.name
        private const val ARG_CRIME_ID = "crime_id"
        private const val DIALOG_DATE = "DialogDate"
        private const val REQUEST_DATE = 0
        private const val REQUEST_CONTACT = 1
        private const val REQUEST_PHOTO = 2

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoFile = this.context?.let { CrimeLab.getPhotoFile(it, mCrime) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        view.crime_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mCrime.mTitle = s.toString()
                updateCrime()
            }

            override fun afterTextChanged(s: Editable) {
                updateCrime()
            }
        })

        Log.d(TAG, "external ${Environment.getExternalStorageDirectory()}")

        updateDate()
        view.crimeDateButton.setOnClickListener { _ ->
            //            DatePickerFragment().show(fragmentManager, DIALOG_DATE)
            DatePickerFragment.newInstance(mCrime.mDate).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
            }.show(fragmentManager, DIALOG_DATE)
        }

        view.crime_solved.setOnCheckedChangeListener { _, isChecked -> mCrime.setmSolved(isChecked) }

        view.crimeReport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport())
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
            startActivity(intent)
        }

        val pickContact = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
//        pickContact.addCategory(Intent.CATEGORY_HOME)
        view.crimeSuspect.setOnClickListener {
            startActivityForResult(pickContact, REQUEST_CONTACT)
        }

        mCrime.suspect?.let {
            view.crimeSuspect.text = it
        }

        val packageManager = activity?.packageManager
        packageManager?.let {
            if (it.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
                view.crimeSuspect.isEnabled = false
            }
        }

        val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        packageManager ?: return view
        val canTakePhoto = photoFile != null &&
                captureImage.resolveActivity(packageManager) != null
        view.photoButton.isEnabled = canTakePhoto

        if (canTakePhoto) {
//            val uri = Uri.fromFile(photoFile)
//            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            this.context?.let {
                photoFile?.let { photoFile ->

                    //                    val uri = FileProvider.getUriForFile(it, it.packageName +".fileprovider", photoFile)
//                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//                    captureImage.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    val photoUri = FileProvider.getUriForFile(it, "${it.packageName}.fileprovider", photoFile)
                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                }
            }

        }

        view.photoButton.setOnClickListener {
            startActivityForResult(captureImage, REQUEST_PHOTO)
        }

        updatePhotoView()

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_DATE) {
            val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            mCrime.mDate = date
            updateCrime()
            updateDate()
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            val contactUri = data.data ?: return

            // 값을 반환할 쿼리 필드 지정
            val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
            // 쿼리 수행
            // contactUri는 SQL의 'where'에 해당됨

            val c = activity?.contentResolver?.query(
                contactUri, queryFields,
                null, null, null
            )

            c.use { c ->
                if (c?.count == 0) {
                    return
                }

                c?.moveToFirst()

                val suspect = c?.getString(0)
                mCrime.suspect = suspect
                updateCrime()
                crimeSuspect.text = suspect
            }
        } else if (requestCode == REQUEST_PHOTO) {
            updateCrime()
            updatePhotoView()
        }
    }

    private fun updateDate() {
        view?.crimeDateButton?.text = mCrime.getmDate()
    }

    override fun onPause() {
        super.onPause()
        CrimeLab.updateCrime(mCrime)
    }

    private fun updateCrime() {
        CrimeLab.updateCrime(mCrime)
        onCrimeUpdate?.invoke(mCrime)
    }


    private fun getCrimeReport(): String {
        val solvedString = if (mCrime.ismSolved()) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }

        val dateFormat = "EEE, MMM dd"
        val dateString = DateFormat.format(dateFormat, mCrime.mDate).toString()

        var suspect = mCrime.suspect
        suspect = if (suspect == null) {
            getString(R.string.crime_report_no_suspect)
        } else {
            String.format(getString(R.string.crime_report_suspect), suspect)
        }

        return getString(R.string.crime_report, mCrime.mTitle, dateString, solvedString, suspect)
    }

    fun onClick(view: View) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport())
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
        intent = Intent.createChooser(intent, getString(R.string.send_report))
        startActivity(intent)
    }

    fun updatePhotoView() {
        val bitmap = photoFile?.let { file ->
            if (!file.exists())
                null
            else {
                activity?.let {
                    PictureUtils.getScaledBitmap(file.path, it)
                }
            }

        }

        if (bitmap == null)
            view?.photoView?.setImageDrawable(null)
        else
            view?.photoView?.setImageBitmap(bitmap)
    }
}
