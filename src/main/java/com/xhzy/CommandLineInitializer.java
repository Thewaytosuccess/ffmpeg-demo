package com.xhzy;

import com.xhzy.config.PlayConfig;
import com.xhzy.util.CommandUtil;
import com.xhzy.util.MediaUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author xhzy
 */
@Slf4j
public class CommandLineInitializer {

    public static void main1(String[] args) {
        String inputPath1 = "/Users/xhzy/Downloads/test.mp4";
        String inputPath2 = "/Users/xhzy/Downloads/test.mp4";
        String outputPath = "/Users/xhzy/Downloads/output.mp4";

        File file = new File(outputPath);
        if(file.exists() && file.delete()){
            System.out.println("[delete success]");
        }

        //获取ffmpeg支持的格式
        String command = "ffmpeg -formats";

        //视频剪切
        String startTime = "00:00:10.0";
        String codec = "copy";
        int duration = 10;
        command = "ffmpeg -i " + inputPath1 + " -ss " +startTime +
                  " -codec " + codec + " -t " + duration + " " + outputPath;


        //视频转gif
        //outputPath = "/Users/xhzy/Downloads/output.gif";
        command = "ffmpeg -i " + inputPath1 +" -vf scale=100:-1 -t 5 -r 10 " + outputPath;

        //淡入淡出效果
        command = "ffmpeg -i " + inputPath1 + " -filter_complex afade=t=in:ss=0:d=5 " + outputPath;
        command = "ffmpeg -i " + inputPath1 + " -filter_complex afade=t=out:st=38:d=5 " + outputPath;

        //调节音量
        command = "ffmpeg -i " +inputPath1 +" -af volume="+1+" "+ outputPath;

        //视频旋转:横屏转竖屏
        command = "ffmpeg -i " + inputPath1 + " -vf transpose=1 -b:v 600k " + outputPath;

        //视频裁剪
        command = "ffmpeg -i " + inputPath1 + " -an -vf crop=120:180:60:0 -vcodec libx264 -b:v 600k " + outputPath;

        //添加文字水印
        command = "ffmpeg -i " + inputPath1 + " -vf drawtext=fontfile=simhei.ttf:text=waterprint:x=100:y=10:" +
                "fontsize=24:fontcolor=yellow:shadowy=2 " + outputPath;

        //添加图片水印
        String iconPath = "icon.png";
        command = "ffmpeg -i " + inputPath1 + " -i " + iconPath + " -filter_complex overlay " + outputPath;

        //转码
        command = "ffmpeg -i " + inputPath1 + " -vcodec libx264 -acodec copy " + outputPath;

        //添加字幕
        String filePath="";
        //command = "ffmpeg -i " + inputPath1 + " -vf subtitles=" + filePath + " " + outputPath;

        //显示设备名称
        //command = "ffmpeg -list_devices 1 -f dshow -i dummy";

        CommandUtil.execShell(command).forEach(System.out::println);
    }

    public static void main(String[] args) {
        String filePath = "/Users/xhzy/Downloads/test.mp4";
        System.out.println(MediaUtil.getVideoInfo(filePath));
        MediaUtil.play(new PlayConfig(filePath));
    }





}
