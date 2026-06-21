package com.homecontentlock.di

import com.homecontentlock.domain.repository.IBlockerRepository
import com.homecontentlock.domain.repository.IPasswordValidator
import com.homecontentlock.usecase.AddCustomSiteUseCase
import com.homecontentlock.usecase.DisableBlockerUseCase
import com.homecontentlock.usecase.EnableBlockerUseCase
import com.homecontentlock.usecase.GetStatusUseCase
import com.homecontentlock.usecase.ListCustomSitesUseCase
import com.homecontentlock.usecase.QueryLogsUseCase
import com.homecontentlock.usecase.RemoveCustomSiteUseCase
import com.homecontentlock.usecase.SetPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetStatusUseCase(repository: IBlockerRepository): GetStatusUseCase {
        return GetStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideEnableBlockerUseCase(
        repository: IBlockerRepository,
        passwordValidator: IPasswordValidator
    ): EnableBlockerUseCase {
        return EnableBlockerUseCase(repository, passwordValidator)
    }

    @Provides
    @Singleton
    fun provideDisableBlockerUseCase(
        repository: IBlockerRepository,
        passwordValidator: IPasswordValidator
    ): DisableBlockerUseCase {
        return DisableBlockerUseCase(repository, passwordValidator)
    }

    @Provides
    @Singleton
    fun provideQueryLogsUseCase(repository: IBlockerRepository): QueryLogsUseCase {
        return QueryLogsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetPasswordUseCase(passwordValidator: IPasswordValidator): SetPasswordUseCase {
        return SetPasswordUseCase(passwordValidator)
    }

    @Provides
    @Singleton
    fun provideAddCustomSiteUseCase(repository: IBlockerRepository): AddCustomSiteUseCase {
        return AddCustomSiteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveCustomSiteUseCase(repository: IBlockerRepository): RemoveCustomSiteUseCase {
        return RemoveCustomSiteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideListCustomSitesUseCase(repository: IBlockerRepository): ListCustomSitesUseCase {
        return ListCustomSitesUseCase(repository)
    }
}
