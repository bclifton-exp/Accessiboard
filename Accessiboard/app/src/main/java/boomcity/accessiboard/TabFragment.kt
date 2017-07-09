package boomcity.accessiboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.style.TtsSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TabFragment : Fragment() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<SoundRecyclerAdapter.ViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_sound_tab, container, false)

        mRecyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(rootView.context)
        mRecyclerView.setLayoutManager(mLayoutManager)

        //Use this to initiate the proper tab
        var tabIndex = this.arguments["tab_number"] as Int
        var tabInfo = DataService.getTabsData().getTab(tabIndex)

        if (tabInfo != null) {
            var myDataset = tabInfo.ttsObjects

            mAdapter = SoundRecyclerAdapter(myDataset, tabIndex)
            mRecyclerView.setAdapter(mAdapter)
        }
        else {
            var myDataset = mutableListOf<TtsObject>()

            mAdapter = SoundRecyclerAdapter(myDataset, tabIndex)
            mRecyclerView.setAdapter(mAdapter)
        }

        return rootView
    }

//    fun update(){
//        mAdapter.notifyDataSetChanged()
//    }

    companion object {
        val ARG_SECTION_NUMBER = "tab_number"

        fun newInstance(tabNumber: Int): TabFragment {
            val fragment = TabFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, tabNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
