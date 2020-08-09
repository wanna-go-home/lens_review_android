package com.wannagohome.lens_review_android.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.wannagohome.lens_review_android.Board
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.Search
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    private val tabSearch =
        Search.instance
    private val tabBoard =
        Board.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        callFragment(0)

        main_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.i("Interaction","reselected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.i("Interaction","unselected")
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("Interaction","selected")
                callFragment(tab?.position)
            }
        })

    }
    fun callFragment(pos:Int?){
        val transaction = supportFragmentManager.beginTransaction()
        when(pos){
            0-> transaction.replace(R.id.main_tab_container,tabSearch)
            1-> transaction.replace(R.id.main_tab_container,tabBoard)
        }
        transaction.commitNow()
    }
}