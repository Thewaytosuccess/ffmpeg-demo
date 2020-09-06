package com.xhzy.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author xhzy
 */
@Data
@ToString
public class MediaDetailInfo {

    private String codec_name;

    private String profile;

    private String codec_type;

    private Integer width;

    private Integer height;

    private String start_time;

    private String duration;

    private String bit_rate;
}
