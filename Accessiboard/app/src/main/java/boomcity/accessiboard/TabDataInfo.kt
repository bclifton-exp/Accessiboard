package boomcity.accessiboard

class TabDataInfo (tabName: String, tabPosition: Int, var soundClips: MutableList<SoundClip> = arrayListOf()) {
    var name: String = tabName
    val position: Int = tabPosition
}

class TabsData (tabsData: MutableList<TabDataInfo>?) {
    val tabsList: MutableList<TabDataInfo>? = tabsData

    fun getTab(tabId: Int) : TabDataInfo? {
        for (tab in tabsList!!) {
            if (tab.position == tabId) {
                return tab
            }
        }
        return null
    }
}