package com.sukitsuki.tsukibb.component

import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import dagger.Component

@Component
interface AnimeDetailActivityComponent {
  fun inject(activity: AnimeDetailActivity)
}