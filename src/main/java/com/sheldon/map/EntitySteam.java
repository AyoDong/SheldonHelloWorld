package com.sheldon.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fangxiaodong
 * @date 2022/01/12
 */
public class EntitySteam {

    public static void main(String[] args) {
        Entity entity1 = new Entity(1L, null);
        Entity entity2 = new Entity(2L, "2");

        List<Entity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        Map<Long, String> collect = list.stream().collect(Collectors.toMap(Entity::getId, Entity::getName));
        System.out.println(collect);
    }

}
