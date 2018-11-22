package online.ahndwon.nerdlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_nerd_launcher.view.*
import java.util.*

class NerdLauncherFragment : Fragment() {

    companion object {
        private val TAG: String = NerdLauncherFragment::class.java.name

        fun newInstance() : NerdLauncherFragment {
            return NerdLauncherFragment()
        }
    }

    inner class ActivityHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        private val nameTextView = itemView as TextView
        private var resolveInfo : ResolveInfo? = null

        fun bindActivity(resolveInfo: ResolveInfo) {
            this.resolveInfo = resolveInfo
            val appName = resolveInfo.loadLabel(activity?.packageManager).toString()
            nameTextView.text = appName
            nameTextView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val activityInfo = resolveInfo?.activityInfo ?: return
            val intent = Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,
                            activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
    }

    inner class ActivityAdapter(private val items : MutableList<ResolveInfo>) : RecyclerView.Adapter<ActivityHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ActivityHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            val resolveInfo = items[position]
            holder.bindActivity(resolveInfo)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nerd_launcher, container, false)

        view.nerdRecyclerView.layoutManager = LinearLayoutManager(this.context)
        setupAdapter(view)

        return view
    }

    private fun setupAdapter(view: View) {
        val startupIntent = Intent(Intent.ACTION_MAIN)
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val pm = activity?.packageManager
        val activities = pm?.queryIntentActivities(startupIntent, 0)

        activities?.sortWith(Comparator { o1, o2 ->
            String.CASE_INSENSITIVE_ORDER.compare(o1.loadLabel(pm).toString(),
                    o2.loadLabel(pm).toString())
        })

        Log.i(TAG, "Found ${activities?.size} + $activities")

        view.nerdRecyclerView.adapter = activities?.let { ActivityAdapter(it) }
    }

}
