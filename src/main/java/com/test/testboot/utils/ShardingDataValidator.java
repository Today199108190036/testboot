package com.test.testboot.utils;
import com.test.testboot.entity.Student;

import java.sql.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class ShardingDataValidator {
    // 数据库配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "zx6923531";

    // 分片表配置
    private static final String SOURCE_TABLE = "student2";
    private static final String[] SHARD_TABLES = {"student2_0", "student2_1", "student2_2"};
    private static final int SHARDING_FACTOR = 3;

    public static void main(String[] args) {
        List<String> discrepancies = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            for (int shardIndex = 0; shardIndex < SHARDING_FACTOR; shardIndex++) {
                String validationSQL = buildValidationSQL(SHARD_TABLES[shardIndex], shardIndex);
                System.out.println(validationSQL);
                discrepancies.addAll(validateShard(conn, validationSQL));
            }

            if (discrepancies.isEmpty()) {
                System.out.println("✅ 所有分片数据校验通过");
            } else {
                System.out.println("❌ 发现不一致数据：");
                discrepancies.forEach(System.out::println);
            }
        } catch (Exception e) {
            handleException("数据库连接异常", e);
        }
    }

    private static String buildValidationSQL(String shardTable, int shardIndex) {
        return String.format(
                "SELECT * FROM (" +
                        "SELECT " +
                        "s1.id as s1id, " +
                        "MD5(CONCAT(s1.id, s1.name, s1.sex, s1.birth, s1.department, s1.address)) as hash1, " +
                        "s2.id as s2id, " +
                        "MD5(CONCAT(s2.id, s2.name, s2.sex, s2.birth, s2.department, s2.address)) as hash2 " +
                        "FROM %s s1 LEFT JOIN %s s2 ON s1.id = s2.id" +
                        ") t WHERE s1id %% %d = %d AND (hash2 IS NULL OR hash1 != hash2) " +
                "union all " +
                "select * from (" +
                        "select " +
                        "s1.id as s1id, " +
                        "MD5(concat(s1.id, s1.name, s1.sex, s1.birth, s1.department, s1.address)) as hash1, " +
                        "s2.id as s2id, " +
                        "MD5(concat(s2.id, s2.name, s2.sex, s2.birth, s2.department, s2.address)) as hash2 " +
                        "FROM %s s1 RIGHT JOIN %s s2 ON s1.id = s2.id" +
                        ") t WHERE hash1 IS NULL OR hash1 != hash2",
                SOURCE_TABLE, shardTable, SHARDING_FACTOR, shardIndex, SOURCE_TABLE, shardTable, SHARDING_FACTOR, shardIndex);
    }

    private static List<String> validateShard(Connection conn, String sql) {
        List<String> results = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int sourceId = rs.getInt("s1id");
                int shardId = rs.getInt("s2id");
                String status = null;
                if(shardId == 0 || sourceId == 0){
                    status = "数据缺失";
                } else {
                    status = "哈希不匹配";
                }
                results.add(String.format("原表ID:%d → 分表ID:%d [%s]", sourceId, shardId, status));
            }
        } catch (Exception e) {
            handleException("分片校验异常", e);
        }
        return results;
    }

    // 计算MD5哈希（与SQL逻辑一致）
    public static String calculateMD5Hash(Student student) throws Exception {
        String rawData = String.join("|",
                String.valueOf(student.getId()),
                student.getName(),
                student.getSex(),
                String.valueOf(student.getBirth()),
                student.getDepartment(),
                student.getAddress()
        );

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(rawData.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static void handleException(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
        System.exit(1);
    }
}
