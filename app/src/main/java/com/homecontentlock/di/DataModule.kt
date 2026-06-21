package com.homecontentlock.di

import android.content.Context
import androidx.room.Room
import com.homecontentlock.data.local.db.BlockerDatabase
import com.homecontentlock.data.repository.BlockerRepositoryImpl
import com.homecontentlock.data.repository.PasswordValidatorImpl
import com.homecontentlock.domain.repository.IBlockerRepository
import com.homecontentlock.domain.repository.IPasswordValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideBlockerDatabase(
        @ApplicationContext context: Context
    ): BlockerDatabase {
        return Room.databaseBuilder(
            context,
            BlockerDatabase::class.java,
            "blocker.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBlockerRepository(
        database: BlockerDatabase
    ): IBlockerRepository {
        return BlockerRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun providePasswordValidator(
        @ApplicationContext context: Context
    ): IPasswordValidator {
        return PasswordValidatorImpl(context)
    }
}
