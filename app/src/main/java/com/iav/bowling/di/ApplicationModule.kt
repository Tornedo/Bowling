package com.iav.bowling.di

import com.iav.bowling.ui.viewmodel.OngoingGameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * For Application (View Model)
 * */
object ApplicationModule {
    val applicationModule: Module = module {
        viewModel {
            provideMainViewModel()
        }

    }

    private fun provideMainViewModel(): OngoingGameViewModel = OngoingGameViewModel()
}
