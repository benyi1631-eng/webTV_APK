#!/bin/bash

# Docker构建脚本 - 在容器中构建Android APK
# 使用方法: ./build-docker.sh

set -e

# 颜色定义
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${BLUE}[INFO]${NC} 开始使用Docker构建Android APK..."
echo -e "${BLUE}[INFO]${NC} 这可能需要几分钟时间，请耐心等待..."

# 检查Docker是否安装
if ! command -v docker &> /dev/null; then
    echo -e "${YELLOW}[WARNING]${NC} Docker未安装，正在尝试安装..."
    
    # 尝试使用Homebrew安装Docker
    if command -v brew &> /dev/null; then
        echo -e "${BLUE}[INFO]${NC} 使用Homebrew安装Docker..."
        brew install --cask docker
        echo -e "${BLUE}[INFO]${NC} 请启动Docker Desktop应用，然后重新运行此脚本"
        exit 1
    else
        echo -e "${YELLOW}[WARNING]${NC} 请手动安装Docker Desktop: https://www.docker.com/products/docker-desktop"
        exit 1
    fi
fi

# 检查Docker是否运行
if ! docker info &> /dev/null; then
    echo -e "${YELLOW}[WARNING]${NC} Docker未运行，请启动Docker Desktop"
    exit 1
fi

echo -e "${BLUE}[INFO]${NC} Docker环境检查通过"

# 构建Docker镜像
echo -e "${BLUE}[STEP]${NC} 构建Docker镜像..."
docker build -t androidtv-build .

# 运行容器并构建APK
echo -e "${BLUE}[STEP]${NC} 在Docker容器中构建APK..."
docker run --rm -v "$(pwd)/output:/app/output" androidtv-build bash -c "
    # 创建输出目录
    mkdir -p /app/output
    
    # 构建Debug APK
    echo '构建Debug APK...'
    ./gradlew assembleDebug
    
    # 构建Release APK
    echo '构建Release APK...'
    ./gradlew assembleRelease
    
    # 复制APK文件到输出目录
    cp app/build/outputs/apk/debug/app-debug.apk /app/output/
    cp app/build/outputs/apk/release/app-release.apk /app/output/
    
    echo 'APK构建完成！'
    ls -la /app/output/
"

# 检查输出
if [ -f "output/app-debug.apk" ] && [ -f "output/app-release.apk" ]; then
    echo -e "${GREEN}[SUCCESS]${NC} APK构建成功！"
    echo -e "${BLUE}[INFO]${NC} 文件位置："
    echo -e "  Debug APK: $(pwd)/output/app-debug.apk"
    echo -e "  Release APK: $(pwd)/output/app-release.apk"
    
    # 显示文件大小
    echo -e "${BLUE}[INFO]${NC} 文件大小："
    ls -lh output/
else
    echo -e "${YELLOW}[WARNING]${NC} APK构建可能失败，请检查Docker容器日志"
fi

# 清理Docker镜像（可选）
read -p "是否清理Docker镜像以节省空间？(y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo -e "${BLUE}[INFO]${NC} 清理Docker镜像..."
    docker rmi androidtv-build
    echo -e "${GREEN}[SUCCESS]${NC} 清理完成"
fi

echo -e "${GREEN}[SUCCESS]${NC} 构建脚本执行完成！"
