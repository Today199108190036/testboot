package com.test.testboot.utils;

public class ShardingUtil {
    private static final int SHARD_COUNT = 3;

    public static String getShardingTableName(Integer id) {
        int shard = id % SHARD_COUNT;
        return "student2_" + shard;
    }
}
