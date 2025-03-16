package com.atguigu.daijia.common.mock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockDataStrategyFactory {
    private static final Map<MockDataEnum, MockDataStrategy> STRATEGY_MAP = new ConcurrentHashMap<>();

    public static MockDataStrategy getStrategy(MockDataEnum value) {
        return STRATEGY_MAP.get(value);
    }

    public static void registerStrategy(MockDataEnum value, MockDataStrategy strategy) {
        STRATEGY_MAP.put(value, strategy);
    }
}
