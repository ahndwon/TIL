package online.ahndwon.criminalintentkotlin.controllers


import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.dialog_date.view.*
import online.ahndwon.criminalintentkotlin.R
import java.util.*


class DatePickerFragment : DialogFragment() {

    companion object {
        const val ARG_DATE = "date"
        const val EXTRA_DATE = "online.ahndwon.criminalintentkotlin.date"

        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)

            return DatePickerFragment().apply {
                this.arguments = args
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val view = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_date, null)

        view.datePicker.init(year, month, day, null)

        return AlertDialog.Builder(view.context)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                with(view.datePicker) {
                    val date = GregorianCalendar(this.year, this.month, this.dayOfMonth).time
                    sendResult(Activity.RESULT_OK, date)
                }
            }
            .create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)

        targetFragment?.onActivityResult(targetRequestCode, resultCode, intent)
    }
}
