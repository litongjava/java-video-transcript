package com.litongjava.video.transcript.service;

import java.io.File;

import com.litongjava.media.NativeMedia;
import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.openai.whisper.WhisperClient;
import com.litongjava.openai.whisper.WhisperResponseFormat;
import com.litongjava.yt.utils.YtDlpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VideoTranscriptService {

  long splitSize = 24 * 1024 * 1024; // 10MB分片

  /**
   * 根据youtube link 返回字幕
   * @param url
   * @return
   */
  public String transcript(String videoId) {
    //下载字幕
    String subTitle = YtDlpUtils.downlodSubtitle(videoId, false);
    if (subTitle != null) {
      return subTitle;
    }
    //下载自动生成字幕
    subTitle = YtDlpUtils.downloadAutoSubtitle(videoId, "srt", false);
    if (subTitle != null) {
      return subTitle;
    }
    // 下载文件
    File file = YtDlpUtils.downloadMp3(videoId, false);
    if (file == null) {
      return null;
    }

    return transcript(file);
  }

  public String transcript(File file) {
    long length = file.length();
    StringBuffer stringBuffer = new StringBuffer();
    if (length > splitSize) {
      String[] outputFiles = NativeMedia.splitMp3(file.getAbsolutePath(), splitSize);
      if (outputFiles != null) {
        for (int i = 0; i < outputFiles.length; i++) {
          log.info("part: " + outputFiles[i]);
          File partFile = new File(outputFiles[i]);
          ResponseVo responseVo = useOpenAi(partFile);
          if (responseVo.isOk()) {
            stringBuffer.append("part ").append(i + 1).append(":\r\n").append(responseVo.getBodyString());
          } else {
            log.error(responseVo.getBodyString());
          }
        }
      }
      return stringBuffer.toString();
    } else {
      ResponseVo responseVo = useOpenAi(file);
      if (responseVo.isOk()) {
        return responseVo.getBodyString();
      } else {
        log.error(responseVo.getBodyString());
      }
    }
    return null;
  }
  
  public String transcriptVideo(String videoId) {
    // 下载文件
    File file = YtDlpUtils.downloadMp3(videoId, false);
    if (file == null) {
      return null;
    }

    return transcript(file);
  }

  /**
   * 转写113分钟 需要 332454(ms).
   * @param file
   * @return
   */
  private ResponseVo useOpenAi(File file) {
    ResponseVo responseVo = WhisperClient.transcriptions(file, WhisperResponseFormat.srt);
    return responseVo;
  }


}
