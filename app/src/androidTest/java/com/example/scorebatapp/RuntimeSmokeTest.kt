package com.example.scorebatapp

import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.scorebatapp.ui.league.LeagueViewModel
import com.example.scorebatapp.util.ResponseType
import com.example.scorebatapp.viewModel.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith

/** Exercises existing public-data screens without signing in or changing authentication state. */
@RunWith(AndroidJUnit4::class)
class RuntimeSmokeTest {
    @Test
    fun publicDataScreensRemainUsable() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            for ((destination, label) in listOf(
                R.id.navigation_home to "home",
                R.id.navigation_dashboard to "matches",
                R.id.navigation_table to "standings"
            )) {
                scenario.onActivity { activity ->
                    activity.findViewById<BottomNavigationView>(R.id.nav_view)
                        .selectedItemId = destination
                }
                // The existing app has no network idling resource; allow its 30-second timeout.
                val deadline = System.currentTimeMillis() + 35_000
                var rows = 0
                do {
                    Thread.sleep(500)
                    scenario.onActivity { activity ->
                        val host = activity.supportFragmentManager
                            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
                        assertEquals(destination, host.navController.currentDestination?.id)
                        rows = activity.findViewById<RecyclerView>(R.id.rv_standings)
                            .adapter?.itemCount ?: 0
                    }
                } while (rows == 0 && System.currentTimeMillis() < deadline)
                // A reachable screen is not proof that its external service supplied data.
                Log.i("ScoreBatRuntimeTest", "$label destination=$destination rows=$rows")
                if (destination == R.id.navigation_home && rows > 0) {
                    scenario.onActivity { activity ->
                        activity.findViewById<RecyclerView>(R.id.rv_standings)
                            .findViewHolderForAdapterPosition(0)!!.itemView.performClick()
                    }
                    Thread.sleep(10_000)
                    scenario.onActivity { activity ->
                        val host = activity.supportFragmentManager
                            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
                        assertEquals(R.id.navigation_highlits, host.navController.currentDestination?.id)
                        val video = activity.findViewById<WebView>(R.id.wb_videos)
                        Log.i("ScoreBatRuntimeTest", "video progress=${video.progress} title=${video.title}")
                        host.navController.popBackStack()
                    }
                }
            }
        }
    }

    /** Run explicitly with emulator Wi-Fi and mobile data disabled by the test operator. */
    @Test
    fun offlineRequestsReportErrors() {
        assumeTrue(InstrumentationRegistry.getArguments().getString("offline") == "true")
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            for (destination in listOf(R.id.navigation_home, R.id.navigation_dashboard)) {
                scenario.onActivity { activity ->
                    activity.findViewById<BottomNavigationView>(R.id.nav_view)
                        .selectedItemId = destination
                }
                val deadline = System.currentTimeMillis() + 35_000
                var hasError = false
                do {
                    Thread.sleep(500)
                    scenario.onActivity { activity ->
                        hasError = if (destination == R.id.navigation_home) {
                            ViewModelProvider(activity)[HomeViewModel::class.java].selected.value is ResponseType.Error
                        } else {
                            ViewModelProvider(activity)[LeagueViewModel::class.java].result.value is ResponseType.Error
                        }
                    }
                } while (!hasError && System.currentTimeMillis() < deadline)
                assertTrue("Offline request should report an error without crashing: $destination", hasError)
                Log.i("ScoreBatRuntimeTest", "offline destination=$destination errorHandled=true")
            }
        }
    }
}
