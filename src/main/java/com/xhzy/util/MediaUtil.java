package com.xhzy.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xhzy.config.MediaBaseInfo;
import com.xhzy.config.MediaDetailInfo;
import com.xhzy.config.MediaInfo;
import com.xhzy.config.PlayConfig;

import java.util.*;

/**
 * @author xhzy
 */
public class MediaUtil {

    public static MediaInfo getVideoInfo(String filePath){
        MediaInfo mediaInfo = new MediaInfo();
        if(Objects.isNull(filePath) || filePath.trim().length() == 0){
            return mediaInfo;
        }

        //查看文件组成信息
        String command = "ffprobe -print_format json -show_streams " + filePath;

        StringBuilder builder = new StringBuilder();
        CommandUtil.execShell(command).forEach(builder::append);
        JSONObject jsonObject = JSON.parseObject(builder.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("streams");

        List<MediaDetailInfo> medias = new ArrayList<>();
        for(Object obj:jsonArray){
            medias.add(JSON.parseObject(obj.toString(), MediaDetailInfo.class));
        }
        mediaInfo.setDetail(medias);

        //查看文件基本信息
        Map<String,Object> map = new HashMap<>(16);
        final String separator = "=";
        command = "ffprobe -show_format " + filePath;
        CommandUtil.execShell(command).forEach(e -> {
            if(e.contains(separator)){
                String[] split = e.split(separator);
                map.put(split[0],split[1]);
            }
        });

        MediaBaseInfo baseInfo = new MediaBaseInfo();
        baseInfo.setFilename(String.valueOf(map.get("filename")));
        baseInfo.setFormatName(String.valueOf(map.get("format_name")));
        baseInfo.setStartTime(String.valueOf(map.get("start_time")));
        baseInfo.setDuration(String.valueOf(map.get("duration")));
        baseInfo.setSize(String.valueOf(map.get("size")));
        baseInfo.setBitRate(String.valueOf(map.get("bit_rate")));
        baseInfo.setProbeScore(String.valueOf(map.get("probe_score")));
        mediaInfo.setBaseInfo(baseInfo);

        //查看帧信息
        //command = "ffprobe -show_frames " + filePath;
        //查看包信息
        //command = "ffprobe -show_packets " + filePath;

        return mediaInfo;
    }

    public void getVideoOrAudio(String inputPath,String outputPath,boolean...v){
        String codec = "copy";
        boolean video = true;
        if(v.length > 0){
            video = v[0];
        }

        String command ;
        if(video){
            //提取视频
            command = "ffmpeg -i " + inputPath + " -an -vcodec " + codec + " "+outputPath;
        }else{
            //提取音频
            command = "ffmpeg -i " + inputPath + " -vn -acodec " + codec + " "+outputPath;
        }
        CommandUtil.execShell(command);
    }

    public void combine(String inputPath1,String inputPath2,String outputPath,boolean...direction){
        boolean v = true;
        if(direction.length > 0){
            v = direction[0];
        }

        String command ;
        if(v){
            //垂直合并
            command = "ffmpeg -i " + inputPath1 + " -i " +
                    inputPath2 + " -filter_complex [0][1]vstack " +
                    outputPath;
        }else{
            //水平合并
            command = "ffmpeg -i " + inputPath1 + " -i " +
                    inputPath2 + " -filter_complex [0][1]hstack " +
                    outputPath;
        }
        CommandUtil.execShell(command);
    }

    public static void play(PlayConfig config){
        if(Objects.isNull(config)){
            return ;
        }
        String filePath = config.getFilePath();
        if(Objects.isNull(filePath) || filePath.trim().length() == 0){
            return ;
        }

        String command = "ffplay -i " + filePath +
                " -vf setpts=PTS/"+ config.getRate() +" -af atempo=" + config.getRate() + //调节视频音频速度
                " -loop " + config.getLoop() + //循环次数
                " -sync " + config.getType(); //同步类型：video/audio
        CommandUtil.execShell(command);
    }

}
