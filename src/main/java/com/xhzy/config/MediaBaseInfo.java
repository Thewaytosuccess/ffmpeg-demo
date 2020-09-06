package com.xhzy.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author xhzy
 */
@Data
@ToString
public class MediaBaseInfo {

    private String filename;

    private String formatName;

    private String startTime;

    private String duration;

    private String size;

    private String bitRate;

    private String probeScore;

}
