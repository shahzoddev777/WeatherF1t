package shahzod.projects.weatherf1t.util.navigation

import androidx.lifecycle.LiveData

interface AppNavigationHandler {
    val backStack : LiveData<AppNavigationParam>

}