package com.xhzy.config;

import com.xhzy.enums.MediaTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhzy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayConfig {

    private String filePath;

    /**
     * 播放速度
     */
    private float rate = 1;

    /**
     * 循环次数
     */
    private int loop = 1;

    /**
     * 同步类型
     */
    private String type = MediaTypeEnum.VIDEO.getKey();

    public PlayConfig(String filePath){
        this.filePath = filePath;
    }
}
