package shahzod.projects.weatherf1t.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shahzod.projects.data.location.DefaultLocationTracker
import shahzod.projects.domain.location.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(impl: DefaultLocationTracker): LocationTracker
}
