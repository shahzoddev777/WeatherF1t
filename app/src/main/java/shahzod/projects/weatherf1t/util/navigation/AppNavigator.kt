package shahzod.projects.weatherf1t.util.navigation

interface AppNavigator {
    fun navigateTo(screen: shahzod.projects.weatherf1t.util.navigation.Screen)
    fun back()
    fun replace(screen: shahzod.projects.weatherf1t.util.navigation.Screen)
    fun replaceAll(screen: shahzod.projects.weatherf1t.util.navigation.Screen)
}
