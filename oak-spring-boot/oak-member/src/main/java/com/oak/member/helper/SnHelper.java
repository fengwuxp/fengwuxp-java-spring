package com.oak.member.helper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * @Author lkb
 * @Description //sn 生成
 * @Date 2019/9/3 15:29
 * @Version 1.0
 **/

public class SnHelper {


    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    public static String[] nums = new String[] {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9"};


    public static String generateShortUuid() {
        StringBuffer headBuffer = new StringBuffer();
        StringBuffer middleBuffer = new StringBuffer();
        StringBuffer tailBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            if (i < 5)
            {
                middleBuffer.append(nums[x % 0xA]);
            }
            if (i<2) {
                headBuffer.append(chars[x % 0x3E]);
            }
            if (i>=5) {
                tailBuffer.append(chars[x % 0x3E]);
            }
        }

        return headBuffer.append(middleBuffer).append(tailBuffer).toString();

    }

    public static String makeRandom(int digit) throws IllegalArgumentException {
        if (digit <= 0) {
            throw new IllegalArgumentException();
        } else {
            String code = "";
            Random random = new Random();

            for(int i = 1; i <= digit; ++i) {
                int r = random.nextInt(10);
                code = code + r;
            }
            return code;
        }
    }

    public static void main(String[] args) {
        Set<String> codeSet = new HashSet();
        for(int i=0;i<10000000;i++){
            String code = generateShortUuid();
            if(codeSet.contains(code)){
                System.out.println("重复的code："+code);
                continue;
            }
            codeSet.add(code);
            System.out.println(code);
        }
        System.out.println("生成完毕");
        System.out.println(codeSet.size());

    }

}
