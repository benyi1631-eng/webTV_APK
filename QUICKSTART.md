# Android TV Live TV - 快速启动指南

## 🚀 快速开始

### 1. 环境要求

- **Java**: JDK 8 或更高版本
- **Android SDK**: API 21 (Android 5.0) 或更高版本
- **Gradle**: 7.5 或更高版本（项目已包含Gradle Wrapper）
- **Android Studio**: 推荐使用最新版本

### 2. 克隆项目

```bash
git clone <your-repository-url>
cd AndroidTVLiveTV
```

### 3. 配置环境变量

#### macOS/Linux:
```bash
export ANDROID_HOME=/Users/username/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

#### Windows:
```cmd
set ANDROID_HOME=C:\Users\username\AppData\Local\Android\Sdk
set PATH=%PATH%;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools
```

### 4. 构建项目

#### 使用构建脚本（推荐）:
```bash
# 构建Debug版本
./build.sh debug

# 构建Release版本
./build.sh release

# 清理项目
./build.sh clean

# 构建并安装到设备
./build.sh install
```

#### 使用Gradle Wrapper:
```bash
# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease

# 清理项目
./gradlew clean

# 安装到设备
./gradlew installDebug
```

### 5. 运行应用

#### 在Android TV设备上:
1. 确保设备已启用开发者选项和USB调试
2. 连接设备到电脑
3. 运行安装命令：`./build.sh install`

#### 在模拟器上:
1. 启动Android模拟器
2. 运行安装命令：`./build.sh install`

## 🔧 常见问题

### Q: 构建失败，提示找不到SDK
**A**: 请检查`ANDROID_HOME`环境变量是否正确设置，并确保SDK路径存在。

### Q: Gradle同步失败
**A**: 检查网络连接，或使用国内镜像源。在`build.gradle`中添加：
```gradle
repositories {
    maven { url 'https://maven.aliyun.com/repository/google' }
    maven { url 'https://maven.aliyun.com/repository/public' }
}
```

### Q: 应用无法安装到设备
**A**: 确保设备已启用USB调试，并且已授权电脑连接。

### Q: 视频播放失败
**A**: 检查网络连接，确保视频源URL可访问。当前使用的是示例URL，需要替换为真实的视频源。

## 📱 功能特性

- ✅ 电视优化的用户界面
- ✅ 自动搜索在线视频源
- ✅ 支持多种视频格式（HLS、MP4、FLV）
- ✅ 遥控器友好操作
- ✅ 频道分类管理
- ✅ 实时视频播放

## 🎯 下一步

1. **自定义视频源**: 修改`VideoSourceSearchService`中的API端点
2. **添加更多频道**: 在`MainActivity`或`ChannelListActivity`中添加频道数据
3. **优化UI**: 自定义主题颜色和样式
4. **添加功能**: 收藏夹、历史记录、EPG等

## 📞 获取帮助

- 查看完整文档: [README.md](README.md)
- 提交Issue: [GitHub Issues](https://github.com/your-repo/issues)
- 联系开发者: [your-email@example.com](mailto:your-email@example.com)

---

**注意**: 这是一个演示项目，视频源URL需要替换为真实可用的地址。
