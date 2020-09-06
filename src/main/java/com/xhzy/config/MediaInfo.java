package com.xhzy.config;

import lombok.Data;

import java.util.List;

@Data
public class MediaInfo {

    private MediaBaseInfo baseInfo;

    private List<MediaDetailInfo> detail;
}
