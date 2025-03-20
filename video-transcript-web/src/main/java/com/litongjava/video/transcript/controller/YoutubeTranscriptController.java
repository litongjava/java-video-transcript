package com.litongjava.video.transcript.controller;

import com.litongjava.annotation.RequestPath;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.tio.boot.http.TioRequestContext;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.tio.utils.youtube.YouTubeIdUtil;
import com.litongjava.video.transcript.service.VideoTranscriptService;

@RequestPath("/v1/youtube")
public class YoutubeTranscriptController {
  VideoTranscriptService videoTranscriptService = Aop.get(VideoTranscriptService.class);

  public HttpResponse transcript(String videoId, String url) {
    if (StrUtil.isNotBlank(url)) {
      videoId = YouTubeIdUtil.extractVideoId(url);
    }
    HttpResponse response = TioRequestContext.getResponse();
    String transcript = videoTranscriptService.transcript(videoId);
    if (transcript != null) {
      response.setString(transcript);
    } else {

    }
    return response;
  }
  
  public HttpResponse transcriptVideo(String videoId, String url) {
    if (StrUtil.isNotBlank(url)) {
      videoId = YouTubeIdUtil.extractVideoId(url);
    }
    HttpResponse response = TioRequestContext.getResponse();
    String transcript = videoTranscriptService.transcriptVideo(videoId);
    if (transcript != null) {
      response.setString(transcript);
    } else {

    }
    return response;
  }
}
