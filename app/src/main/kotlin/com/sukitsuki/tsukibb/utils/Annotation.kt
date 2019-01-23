package com.sukitsuki.tsukibb.utils

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass


@Qualifier
@Retention(RUNTIME)
annotation class App

@Qualifier
@Retention(RUNTIME)
annotation class ExoPlayer

@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Scope
@Retention(RUNTIME)
annotation class ActivityScope

@Scope
@Retention(RUNTIME)
annotation class ApplicationScope

@Scope
@Retention(RUNTIME)
annotation class FragmentScope