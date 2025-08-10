# 安卓电视直播应用

一个专为安卓系统电视设计的实时在线电视软件，支持自动搜索在线可用视频源。

## 功能特性

- 🎯 **专为电视优化**: 使用Android Leanback库，完美适配电视遥控器操作
- 🔍 **自动搜索视频源**: 后台服务自动搜索和验证在线视频源
- 📺 **多格式支持**: 支持HLS、MP4、FLV等多种视频格式
- 🎮 **遥控器友好**: 支持方向键、确认键、返回键等电视遥控器操作
- 🎨 **现代化UI**: 采用Material Design风格，界面美观易用
- 📱 **响应式设计**: 适配不同尺寸的电视屏幕
- 🔄 **实时更新**: 自动检测视频源可用性，实时更新频道状态

## 技术架构

### 核心组件
- **MainActivity**: 主界面，展示频道分类和列表
- **PlayerActivity**: 视频播放器界面
- **VideoSourceSearchService**: 后台视频源搜索服务
- **ChannelPresenter**: 频道项UI展示器

### 数据模型
- **Channel**: 频道信息模型
- **ChannelCategory**: 频道分类模型
- **VideoSource**: 视频源信息模型

### 网络层
- **VideoSourceApi**: 视频源API接口
- **VideoSourceRetrofitClient**: Retrofit网络客户端

### 播放器
- **ExoPlayer**: Google官方推荐的视频播放器
- 支持HLS、DASH、MP4等多种格式
- 自动重试和错误处理

## 安装要求

- Android API Level 21+ (Android 5.0+)
- 支持Leanback的安卓电视设备
- 网络连接权限

## 使用方法

1. **启动应用**: 在电视上安装并启动应用
2. **浏览频道**: 使用遥控器方向键浏览频道分类
3. **选择频道**: 按确认键选择要观看的频道
4. **观看直播**: 频道将自动开始播放
5. **控制播放**: 使用遥控器控制播放/暂停、返回等操作

## 遥控器操作说明

- **方向键**: 在频道列表中导航
- **确认键**: 选择频道或确认操作
- **返回键**: 返回上一级或退出播放
- **菜单键**: 显示/隐藏播放控制界面

## 开发环境

- Android Studio Arctic Fox或更高版本
- Android SDK 33
- Gradle 7.4.2+

## 构建步骤

1. 克隆项目到本地
2. 在Android Studio中打开项目
3. 同步Gradle依赖
4. 连接安卓电视设备或启动模拟器
5. 点击运行按钮构建并安装应用

## 依赖库

- `androidx.leanback:leanback` - 电视UI组件
- `com.google.android.exoplayer:exoplayer` - 视频播放器
- `com.squareup.retrofit2:retrofit` - 网络请求
- `com.squareup.okhttp3:okhttp` - HTTP客户端

## 注意事项

- 应用需要网络权限来搜索和播放视频源
- 某些视频源可能需要特定的网络环境
- 建议在稳定的网络环境下使用

## 许可证

本项目采用MIT许可证，详见LICENSE文件。

## 贡献

欢迎提交Issue和Pull Request来改进这个项目。

## 联系方式

如有问题或建议，请通过GitHub Issues联系我们。
