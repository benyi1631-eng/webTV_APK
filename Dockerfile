# 使用官方Android构建镜像
FROM openjdk:11-jdk

# 安装必要的工具
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app

# 下载并安装Gradle
RUN wget https://services.gradle.org/distributions/gradle-7.5-bin.zip \
    && unzip gradle-7.5-bin.zip \
    && rm gradle-7.5-bin.zip

# 设置Gradle环境变量
ENV GRADLE_HOME=/app/gradle-7.5
ENV PATH=$PATH:$GRADLE_HOME/bin

# 下载Android SDK命令行工具
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip \
    && unzip commandlinetools-linux-8512546_latest.zip \
    && rm commandlinetools-linux-8512546_latest.zip

# 创建Android SDK目录结构
RUN mkdir -p /opt/android-sdk/cmdline-tools/latest \
    && mv cmdline-tools/* /opt/android-sdk/cmdline-tools/latest/ \
    && rmdir cmdline-tools

# 设置Android SDK环境变量
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# 接受Android SDK许可
RUN yes | sdkmanager --licenses

# 安装必要的SDK组件
RUN sdkmanager "platforms;android-33" \
    "build-tools;33.0.0" \
    "platform-tools"

# 复制项目文件
COPY . .

# 给gradlew添加执行权限
RUN chmod +x gradlew

# 构建Debug APK
RUN ./gradlew assembleDebug

# 构建Release APK
RUN ./gradlew assembleRelease

# 显示构建结果
RUN ls -la app/build/outputs/apk/debug/ \
    && ls -la app/build/outputs/apk/release/

# 设置默认命令
CMD ["bash"]
