// AboutFragment.kt
package com.gmail.eamosse.imdb.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.gmail.eamosse.imdb.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AboutFragment", "onViewCreated")
        val aboutUsTextView = view.findViewById<TextView>(R.id.aboutUsTextView)
        animateText(aboutUsTextView, "About Us")
        view.findViewById<TextView>(R.id.marwaneLinkedInText).setOnClickListener {
            openLinkedIn("https://www.linkedin.com/in/marwanelarbi/")
        }
        view.findViewById<TextView>(R.id.badisLinkedInText).setOnClickListener {
            openLinkedIn("https://www.linkedin.com/in/badis-boucheffa/")
        }
        view.findViewById<TextView>(R.id.yassineLinkedInText).setOnClickListener {
            openLinkedIn("https://www.linkedin.com/in/yassine-berriri-a013091b8/")
        }
        view.findViewById<TextView>(R.id.ibrahimLinkedInText).setOnClickListener {
            openLinkedIn("https://www.linkedin.com/in/ibrahim-krimi-a9a355173/")
        }
    }
    private fun animateText(view: TextView, text: String) {
        var index = 0
        val maxLength = text.length
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                if (index < maxLength) {
                    view.text = text.substring(0, index++)
                    handler.postDelayed(this, 150)
                } else {
                    handler.postDelayed({ view.text = ""; index = 0; animateText(view, text) }, 1500)
                }
            }
        }
        handler.post(runnable)
    }
    private fun openLinkedIn(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


}
