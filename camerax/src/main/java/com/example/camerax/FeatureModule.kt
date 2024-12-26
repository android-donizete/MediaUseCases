package com.example.camerax

import com.example.core.Feature
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface FeatureModule {
    @Binds
    @IntoSet
    fun bindsCameraXFeature(impl: CameraXFeature): Feature
}