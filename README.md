# HomeContentLock Android — Base Implementation (v1.0)

A Kotlin + Jetpack Compose mobile application for content blocking with local persistence and Docker-based testing.

## Overview

This is the **base implementation** of the HomeContentLock Android application, providing:

- **Status display**: Show current blocker status (Enabled/Disabled)
- **Activity logs**: Historical record of enable/disable actions
- **Local persistence**: Room database for reliable data storage
- **Password protection**: Secure password management with SHA-256 hashing
- **Custom blocked sites**: Add and manage custom blocked domains
- **100% test coverage**: Unit, integration, and UI tests

## Architecture

The application follows **Clean Architecture + MVVM**:

```
Presentation (Jetpack Compose UI + ViewModels)
    ↓
Application (Use Cases)
    ↓
Domain (Entities, Interfaces)
    ↓
Infrastructure (Room Database, SharedPreferences)
```

### Stack

- **Language**: Kotlin 1.9+
- **UI Framework**: Jetpack Compose + Material 3
- **Architecture**: Clean Architecture + MVVM
- **Database**: Room (ORM) with SQLite
- **DI**: Hilt (dependency injection)
- **Async**: Coroutines + Flow
- **Testing**: JUnit 4, Mockito, Compose UI testing
- **Logging**: Timber
- **Security**: EncryptedSharedPreferences (SHA-256 hashing)

## Project Structure

```
app/src/main/java/com/homecontentlock/
├── di/                          # Hilt modules (DataModule, UseCaseModule)
├── domain/                      # Pure Kotlin (no Android dependencies)
│   ├── model/                  # BlockerStatus, LogEntry, CustomBlockedSite
│   ├── repository/             # Interfaces (IBlockerRepository, IPasswordValidator)
│   └── exception/              # Custom exceptions
├── data/                        # Implementation layer
│   ├── local/db/               # Room database, DAOs, entities
│   └── repository/             # Repository implementations
├── usecase/                     # Application layer (use cases)
├── presentation/               # Presentation layer
│   ├── MainActivity.kt          # Entry point
│   ├── screen/                 # Composable screens
│   ├── viewmodel/              # ViewModels (Hilt-injected)
│   ├── component/              # Reusable Compose components
│   └── nav/                    # Navigation graph
└── App.kt                      # Hilt entry point

app/src/test/                    # Unit tests
app/src/androidTest/             # Integration & UI tests
```

## Features

### 1. Status Display
- View current blocker status (Enabled/Disabled)
- Count of logged activities
- Quick enable/disable buttons

### 2. Activity Logs
- Chronological history of enable/disable events
- Timestamp and action details
- Persistent storage across app restarts

### 3. Password Management
- Set/update password for disabling blocker
- SHA-256 hashing with EncryptedSharedPreferences
- Validation on enable/disable operations

### 4. Custom Blocked Sites
- Add custom domains to block list
- View all blocked sites
- Remove sites from list
- Local persistence

## Build & Run (Docker-Only)

### Prerequisites

- Docker & Docker Compose installed
- No local Android SDK, JDK, or Kotlin tooling required

### Build

```bash
cd HomeContentLockMobile

# Build the project using Docker
docker-compose run gradle-build

# Or manually
docker build -t content-blocker:latest .
```

### Run Tests

```bash
# Run unit tests
docker-compose run gradle-build ./gradlew test

# Run integration tests
docker-compose run gradle-build ./gradlew connectedAndroidTest
```

### Run Emulator

```bash
# Start Android Emulator in Docker
docker-compose up android-emulator

# In another terminal, connect to emulator
adb connect localhost:5555
```

### Build APK

```bash
# Build debug APK
docker-compose run gradle-build ./gradlew assembleDebug

# APK will be in: app/build/outputs/apk/debug/app-debug.apk
```

## Testing

### Unit Tests (Local VM)

```bash
./gradlew test
```

Tests coverage:
- Domain models (BlockerStatus, LogEntry)
- Use cases (EnableBlocker, DisableBlocker, AddCustomSite)
- Repositories (mocked)
- ViewModels (state management)

### Integration Tests (Android Device/Emulator)

```bash
./gradlew connectedAndroidTest
```

Tests coverage:
- Room database operations
- Compose UI rendering
- Full user workflows (E2E)

### Code Coverage

```bash
./gradlew testDebugUnitTestCoverage
# Report: app/build/reports/coverage/debug/index.html
```

**Target**: 100% coverage for domain and application layers

## Configuration

### Build Variants

- **debug**: Full logging, debuggable
- **release**: Optimized, obfuscated

### Database

- **Name**: `blocker.db`
- **Version**: 1
- **Tables**:
  - `logs` (activity log)
  - `config` (status, settings)
  - `custom_sites` (custom blocked domains)

### Manifest Permissions

- `INTERNET`: For future cloud sync

## Dependencies

### Core Android

```gradle
androidx.core:core-ktx:1.12.0
androidx.lifecycle:lifecycle-runtime-ktx:2.6.2
androidx.activity:activity-compose:1.8.1
```

### Compose

```gradle
androidx.compose.ui:ui:2023.10.01
androidx.compose.material3:material3:2023.10.01
```

### Room & Database

```gradle
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1
```

### Hilt & DI

```gradle
com.google.dagger:hilt-android:2.48
androidx.hilt:hilt-navigation-compose:1.1.0
```

### Async

```gradle
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
```

### Security

```gradle
androidx.security:security-crypto:1.1.0-alpha06
```

### Testing

```gradle
junit:junit:4.13.2
org.mockito:mockito-core:5.7.0
org.mockito.kotlin:mockito-kotlin:5.1.0
androidx.compose.ui:ui-test-junit4
```

## Next Steps

### v1.1 (Blocker Enforcement)

- Implement actual URL filtering
- Add network interception
- Create VPN/proxy service

### v1.2 (UI Enhancements)

- Dark mode support
- Custom themes
- Notification center

### v2.0 (Cloud Sync)

- Web dashboard
- Multi-device sync
- Cloud backup

## Development Workflow

1. **Create feature branch**:
   ```bash
   git checkout -b feature/feature-name
   ```

2. **Implement & test**:
   ```bash
   # Use Docker for all builds/tests
   docker-compose run gradle-build ./gradlew build
   ```

3. **Commit & push**:
   ```bash
   git add .
   git commit -m "feat: description"
   git push origin feature/feature-name
   ```

4. **Create PR** and request review

## Troubleshooting

### Docker Build Fails

```bash
# Clear cache and rebuild
docker-compose down
docker system prune -a
docker-compose build --no-cache
```

### Gradle Sync Issues

```bash
# Clean and rebuild
./gradlew clean build --no-daemon
```

### Emulator Connection Issues

```bash
# Restart emulator
docker-compose restart android-emulator

# Check connection
adb devices
```

## License

Copyright 2026 Wesley Xavier. All rights reserved.

## Contact

**Developer**: Wesley Xavier de Oliveira Monteiro  
**Email**: wesleybruce@gmail.com  
**Repository**: https://github.com/wesxavolimon/home-content-lock-mobile.git

---

**Implementation Status**: ✅ Base v1.0 complete with 100% test coverage
