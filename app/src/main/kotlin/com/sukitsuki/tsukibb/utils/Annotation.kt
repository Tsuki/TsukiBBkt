package com.sukitsuki.tsukibb.utils

import javax.inject.Qualifier
import javax.inject.Scope


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class App

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ExoPlayer

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope