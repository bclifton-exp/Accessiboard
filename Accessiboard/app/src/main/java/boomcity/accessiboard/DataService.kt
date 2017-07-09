package boomcity.accessiboard

import android.content.SharedPreferences
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson

class DataService {
    companion object{
        private lateinit var viewPager: ViewPager
        private lateinit var tabsDataObject: TabsData
        private lateinit var sharedPreferences: SharedPreferences
        private var gson = Gson()

        fun getTabsData() : TabsData{
            return tabsDataObject
        }

        fun setViewpager(vp: ViewPager) {
            viewPager = vp
        }

        fun init(tabsData: TabsData, sharedPrefs: SharedPreferences) {
            tabsDataObject = tabsData
            sharedPreferences = sharedPrefs
            val json = gson.toJson(tabsDataObject)
            sharedPreferences.edit().putString("TabsDataInfo", json).apply()
        }

        fun addNewTab(tabName: String, tabIndex: Int) {
            tabsDataObject.tabsList!!.add(TabDataInfo(tabName,tabIndex))
            val json = gson.toJson(tabsDataObject)
            sharedPreferences.edit().putString("TabsDataInfo", json).apply()
        }

        fun deleteTab(tabIndex: Int) {
            tabsDataObject.tabsList!!.removeAt(tabIndex)
            val json = gson.toJson(tabsDataObject)
            sharedPreferences.edit().putString("TabsDataInfo", json).apply()
        }

        fun renameTab(newTabName: String, tabIndex: Int) {
            tabsDataObject.tabsList!![tabIndex].name = newTabName
            val json = gson.toJson(tabsDataObject)
            sharedPreferences.edit().putString("TabsDataInfo", json).apply()
            viewPager.adapter.notifyDataSetChanged()
        }

        fun addTtsObjectToTab(ttsObject: TtsObject, tabIndex: Int){
            val selectedTabView = viewPager.getChildAt(tabIndex)
            val recyclerView = selectedTabView.findViewById(R.id.recycler_view) as RecyclerView
            val soundAdapter = recyclerView.adapter as SoundRecyclerAdapter
            tabsDataObject.getTab(tabIndex)!!.ttsObjects.add(ttsObject)
            val json = gson.toJson(tabsDataObject)
            sharedPreferences.edit().putString("TabsDataInfo", json).apply()
            soundAdapter.notifyDataSetChanged()
        }

        fun removeTtsObjectFromTab(soundAdapter: SoundRecyclerAdapter, ttsObject: TtsObject, tabIndex: Int) {
            tabsDataObject.getTab(tabIndex)!!.ttsObjects.removeAll { tts ->
                tts.TtsId == ttsObject.TtsId
            }
            val json = gson.toJson(tabsDataObject)
            sharedPreferences.edit().putString("TabsDataInfo", json).apply()
            soundAdapter.notifyDataSetChanged()
        }

        fun removeTtsObjectFromApp(ttsObject: TtsObject) {
            for (tab in tabsDataObject.tabsList!!) {
                tab.ttsObjects.removeAll { tts ->
                    tts.TtsId == ttsObject.TtsId
                }
            }

            val json = gson.toJson(tabsDataObject)
            sharedPreferences.edit().putString("TabsDataInfo", json).apply()

            tabsDataObject.tabsList!!
                    .map { viewPager.getChildAt(it.position).findViewById(R.id.recycler_view) as RecyclerView }
                    .forEach { it.adapter.notifyDataSetChanged() }
        }
    }
}
