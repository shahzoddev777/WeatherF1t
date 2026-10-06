package shahzod.projects.weatherf1t.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shahzod.projects.data.repository.GeocodingRepositoryImpl
import shahzod.projects.data.repository.SavedLocationsRepositoryImpl
import shahzod.projects.data.repository.WeatherRepositoryImpl
import shahzod.projects.domain.repository.GeocodingRepository
import shahzod.projects.domain.repository.SavedLocationsRepository
import shahzod.projects.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindGeocodingRepository(impl: GeocodingRepositoryImpl): GeocodingRepository

    @Binds
    @Singleton
    abstract fun bindSavedLocationsRepository(impl: SavedLocationsRepositoryImpl): SavedLocationsRepository
}
