# GitHub Actions 自动构建指南

## 🚀 快速开始

### 步骤1：创建GitHub仓库

1. 访问 [GitHub.com](https://github.com) 并登录
2. 点击右上角 "+" 号，选择 "New repository"
3. 填写仓库信息：
   - **Repository name**: `AndroidTVLiveTV`
   - **Description**: `Android TV Live TV application with automatic APK building`
   - **Visibility**: 选择 `Public`
   - **不要勾选** "Add a README file"、"Add .gitignore"、"Choose a license"
4. 点击 "Create repository"

### 步骤2：上传项目到GitHub

在您的项目目录中运行以下命令（替换 `YOUR_USERNAME` 为您的GitHub用户名）：

```bash
# 添加远程仓库
git remote add origin https://github.com/YOUR_USERNAME/AndroidTVLiveTV.git

# 推送到GitHub
git branch -M main
git push -u origin main
```

### 步骤3：触发自动构建

1. 推送完成后，GitHub Actions会自动开始构建
2. 在您的仓库页面，点击 "Actions" 标签页
3. 您会看到构建进度
4. 构建完成后，点击构建任务查看详情
5. 在 "Artifacts" 部分下载构建好的APK文件

## 📱 下载APK文件

构建完成后，您可以在以下位置找到APK文件：

- **Debug APK**: `app-debug.apk` - 用于测试
- **Release APK**: `app-release.apk` - 用于发布

## 🔧 手动触发构建

如果您想手动触发构建：

1. 在仓库页面点击 "Actions" 标签
2. 选择 "Build Android APK" 工作流
3. 点击 "Run workflow" 按钮
4. 选择分支（通常是 `main`）
5. 点击 "Run workflow"

## 📋 构建状态检查

构建过程包括以下步骤：

1. ✅ 检出代码
2. ✅ 设置JDK 11
3. ✅ 设置Android SDK
4. ✅ 构建Debug APK
5. ✅ 构建Release APK
6. ✅ 上传构建产物

## 🚨 常见问题

### 构建失败？
- 检查GitHub Actions日志
- 确保所有文件都已上传
- 检查网络连接

### 找不到APK文件？
- 等待构建完成
- 检查Actions页面的Artifacts部分
- 确保构建成功

### 需要重新构建？
- 修改代码后推送到GitHub
- 或手动触发构建

## 🎯 下一步

1. 按照上述步骤创建GitHub仓库
2. 上传项目代码
3. 等待自动构建完成
4. 下载APK文件
5. 在Android TV设备上安装测试

## 📞 需要帮助？

如果遇到问题：
1. 检查GitHub Actions日志
2. 确保所有依赖都已正确配置
3. 查看项目的README.md文件

---

**注意**: 首次构建可能需要较长时间，因为需要下载Android SDK和构建工具。
