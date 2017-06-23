package com.lesson.utils;

import java.util.UUID;

/**
 * Created by tian on 16/9/27.
 */

public class UUIDGenerator {
        private UUIDGenerator(){}
        public static String getCode() {
            String newCode = UUID.randomUUID().toString();
            return newCode;
        }
}
