# Dockerfile for Android Emulator and Testing
# This image provides a complete Android development environment with emulator support

FROM android:emulator

# Install additional tools
RUN sdkmanager \
    "system-images;android-34;default;x86_64" \
    "platforms;android-34" \
    "build-tools;34.0.0" \
    "cmdline-tools;latest"

# Create AVD (Android Virtual Device)
ENV ANDROID_AVD_HOME=/root/.android/avd
RUN mkdir -p $ANDROID_AVD_HOME

# Create device configuration
RUN echo "hw.lcd.width=1080" > /root/.android/avd/emulator_default.ini && \
    echo "hw.lcd.height=1920" >> /root/.android/avd/emulator_default.ini && \
    echo "hw.lcd.density=420" >> /root/.android/avd/emulator_default.ini && \
    echo "hw.cpu.nativebridge=yes" >> /root/.android/avd/emulator_default.ini

# Set working directory
WORKDIR /app

# Copy gradle wrapper
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

# Copy application code
COPY app ./app

# Install dependencies
RUN chmod +x ./gradlew && \
    ./gradlew build -x connectedAndroidTest --no-daemon || true

# Default command: run emulator with ADB enabled
CMD ["emulator", "-avd", "emulator_default", "-no-window", "-no-audio", "-no-snapshot-load"]
