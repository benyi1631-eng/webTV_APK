#!/bin/bash

# Android TV Live TV 构建脚本
# 使用方法: ./build.sh [debug|release|clean]

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 打印带颜色的消息
print_message() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_step() {
    echo -e "${BLUE}[STEP]${NC} $1"
}

# 检查Java环境
check_java() {
    print_step "检查Java环境..."
    if ! command -v java &> /dev/null; then
        print_error "Java未安装或不在PATH中"
        print_message "请安装Java 8或更高版本"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    print_message "Java版本: $JAVA_VERSION"
}

# 检查Android SDK
check_android_sdk() {
    print_step "检查Android SDK..."
    if [ -z "$ANDROID_HOME" ]; then
        print_warning "ANDROID_HOME环境变量未设置"
        print_message "请设置ANDROID_HOME环境变量指向Android SDK目录"
        print_message "例如: export ANDROID_HOME=/Users/username/Library/Android/sdk"
        exit 1
    fi
    
    if [ ! -d "$ANDROID_HOME" ]; then
        print_error "Android SDK目录不存在: $ANDROID_HOME"
        exit 1
    fi
    
    print_message "Android SDK路径: $ANDROID_HOME"
}

# 检查Gradle
check_gradle() {
    print_step "检查Gradle..."
    if ! command -v gradle &> /dev/null; then
        print_warning "Gradle未安装，将使用Gradle Wrapper"
        if [ ! -f "./gradlew" ]; then
            print_error "Gradle Wrapper脚本不存在"
            exit 1
        fi
        GRADLE_CMD="./gradlew"
    else
        GRADLE_CMD="gradle"
        GRADLE_VERSION=$(gradle --version | grep "Gradle" | head -n 1)
        print_message "$GRADLE_VERSION"
    fi
}

# 清理项目
clean_project() {
    print_step "清理项目..."
    if [ "$GRADLE_CMD" = "./gradlew" ]; then
        chmod +x ./gradlew
        ./gradlew clean
    else
        gradle clean
    fi
    print_message "项目清理完成"
}

# 构建项目
build_project() {
    local build_type=$1
    
    print_step "构建项目 ($build_type)..."
    
    if [ "$GRADLE_CMD" = "./gradlew" ]; then
        chmod +x ./gradlew
        if [ "$build_type" = "debug" ]; then
            ./gradlew assembleDebug
        else
            ./gradlew assembleRelease
        fi
    else
        if [ "$build_type" = "debug" ]; then
            gradle assembleDebug
        else
            gradle assembleRelease
        fi
    fi
    
    print_message "项目构建完成"
}

# 安装到设备
install_app() {
    print_step "检查连接的设备..."
    
    if [ "$GRADLE_CMD" = "./gradlew" ]; then
        chmod +x ./gradlew
        ./gradlew installDebug
    else
        gradle installDebug
    fi
    
    print_message "应用安装完成"
}

# 主函数
main() {
    print_message "Android TV Live TV 构建脚本"
    print_message "================================"
    
    # 检查环境
    check_java
    check_android_sdk
    check_gradle
    
    # 处理命令行参数
    case "${1:-debug}" in
        "debug")
            print_message "构建Debug版本"
            build_project "debug"
            ;;
        "release")
            print_message "构建Release版本"
            build_project "release"
            ;;
        "clean")
            print_message "清理项目"
            clean_project
            ;;
        "install")
            print_message "构建并安装应用"
            build_project "debug"
            install_app
            ;;
        *)
            print_error "未知参数: $1"
            print_message "使用方法: $0 [debug|release|clean|install]"
            exit 1
            ;;
    esac
    
    print_message "构建脚本执行完成"
}

# 执行主函数
main "$@"
