package com.example.camerax

import com.example.core.FeatureEntryPoint
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface FeatureEntryPointModule {
    @Binds
    @IntoSet
    fun bindsCameraXFeatureEntryPoint(impl: CameraXFeatureEntryPoint): FeatureEntryPoint
}