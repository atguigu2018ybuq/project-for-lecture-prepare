package com.atguigu.spring.boot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testZset() {
        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> chengjiSet = operations.rangeWithScores("chengji", 0, -1);
        for (ZSetOperations.TypedTuple<String> stringTypedTuple : chengjiSet) {
            String value = stringTypedTuple.getValue();
            Double score = stringTypedTuple.getScore();
            System.out.println(value+"="+score);
        }
    }

    @Test
    public void testHash() {
        HashOperations<String, Object, Object> operations = stringRedisTemplate.opsForHash();
        Map<Object, Object> studentMap = operations.entries("student");
        Set<Map.Entry<Object, Object>> entries = studentMap.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key+"="+value);
        }
    }

    @Test
    public void testSet() {
        SetOperations<String, String> operations = stringRedisTemplate.opsForSet();
        Set<String> animalSet = operations.members("animal");
        for (String animal : animalSet) {
            System.out.println("animal="+animal);
        }
    }

    @Test
    public void testList() {
        ListOperations<String, String> operations = stringRedisTemplate.opsForList();
        List<String> fruitList = operations.range("fruit", 0, -1);
        for (String fruit : fruitList) {
            System.out.println(fruit);
        }
    }

    @Test
    public void testString() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("hello", "hello-value");
    }

    @Test
    public void test() {
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        stringRedisTemplate.expire("animal", 5000, TimeUnit.SECONDS);

        Long expire = stringRedisTemplate.getExpire("animal");
        System.out.println("expire="+expire);

        stringRedisTemplate.persist("animal");

        expire = stringRedisTemplate.getExpire("animal");
        System.out.println("expire="+expire);
    }

    @Test
    public void testStringRedisTemplate() {
        SetOperations<String, String> setOperation = stringRedisTemplate.opsForSet();
        Set<String> animalSet = setOperation.members("animal");
        for (String animal : animalSet) {
            System.out.println(animal);
        }
    }

    @Test
    public void testRedisTemplate() {
        ListOperations<String, String> listOperation = redisTemplate.opsForList();
        listOperation.leftPush("fruit", "apple");
        listOperation.leftPush("fruit", "banana");
        List<String> fruitList = listOperation.range("fruit", 0, -1);

        for (String fruit : fruitList) {
            System.out.println(fruit);
        }

    }

}
