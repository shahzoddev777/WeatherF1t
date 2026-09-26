package shahzod.projects.weatherf1t.util.navigation

import androidx.lifecycle.MutableLiveData

object AppNavigationDispatcher : AppNavigator, AppNavigationHandler {

    override val backStack = MutableLiveData<AppNavigationParam>()

    private fun navigat(param: AppNavigationParam) {
        backStack.postValue(param)
    }

    override fun navigateTo(screen: shahzod.projects.weatherf1t.util.navigation.Screen) = navigat {
        push(screen)
    }

    override fun back() = navigat {
        pop()
    }

    override fun replace(screen: shahzod.projects.weatherf1t.util.navigation.Screen) = navigat {
        replace(screen)
    }

    override fun replaceAll(screen: shahzod.projects.weatherf1t.util.navigation.Screen) = navigat {
        replaceAll(screen)
    }
}

