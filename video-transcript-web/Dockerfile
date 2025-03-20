FROM litongjava/jdk:8u411-stable-slim

RUN apt-get update && apt-get install -y ffmpeg libmp3lame0

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件到容器
COPY video-transcript-web-ui /app/video-transcript-web-ui
COPY target/video-transcript-web-1.0.0.jar /app/

# 运行 JAR 文件
CMD ["java", "-jar", "video-transcript-web-1.0.0.jar"]