package com.litongjava.video.transcript.service;

import java.io.File;

import org.junit.Test;

import com.litongjava.media.NativeMedia;
import com.litongjava.tio.utils.environment.EnvUtils;

public class VideoTranscriptServiceTest {

  @Test
  public void test() {
    EnvUtils.load();
    long start = System.currentTimeMillis();
    File file = new File("D:\\dev_workspace\\eclipse-jee-2022-6\\java-video-transcript\\video-transcript-web\\downloads\\6WikKtekikg\\mp3\\黄奇帆演讲，透露中国将如何应对特朗普【演讲】.mp3");
    VideoTranscriptService videoTranscriptService = new VideoTranscriptService();
    String transcript = videoTranscriptService.transcript(file);
    System.out.println(transcript);
    long end = System.currentTimeMillis();
    System.out.println((end - start) + "(ms)");
  }

  @Test
  public void testSplit() {
    long splitSize = 25 * 1024 * 1024; // 10MB分片
    File file = new File("D:\\dev_workspace\\eclipse-jee-2022-6\\java-video-transcript\\video-transcript-web\\downloads\\6WikKtekikg\\mp3\\黄奇帆演讲，透露中国将如何应对特朗普【演讲】.mp3");
    System.out.println(file.exists());
    String[] outputFiles = NativeMedia.splitMp3(file.getAbsolutePath(), splitSize);
    System.out.println(outputFiles);
  }

}
