package com.sukitsuki.tsukibb.viewmodel

import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.repository.Repository

abstract class RepositoryViewModel(repository: Repository) : ViewModel()