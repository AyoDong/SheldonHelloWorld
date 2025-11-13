package com.sheldon.ai;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ExtractResource {

    public static void main(String[] args) throws IOException {
        JSONObject jsonObject = extractResourceFromJson("D:\\Sheldon\\SheldonHelloWorld\\src\\main\\java\\com\\sheldon\\ai\\Resource.json");
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject object = data.getJSONObject("object");
        JSONArray items = object.getJSONArray("items");

        for (int i = 0; i < items.size(); i++){
            JSONObject item = items.getJSONObject(i);
            JSONObject metadata = item.getJSONObject("metadata");
            String name = metadata.getString("name");

            JSONObject spec = item.getJSONObject("spec");
            String replicas = spec.getString("replicas");

            JSONObject template = spec.getJSONObject("template");
            JSONObject spec1 = template.getJSONObject("spec");
            JSONArray containers = spec1.getJSONArray("containers");
            JSONObject container = containers.getJSONObject(0);
            JSONObject resources = container.getJSONObject("resources");
            JSONObject limits = resources.getJSONObject("limits");
            JSONObject cpuObject = limits.getJSONObject("cpu");
            String cpu = cpuObject.getString("string");

            JSONObject memoryObject = limits.getJSONObject("memory");
            String memory = memoryObject.getString("string");

            System.out.println("name: " + name + ", replicas: " + replicas + ", cpu: " + cpu + ", memory: " + memory);
        }
    }

    public static JSONObject extractResourceFromJson(String filePath) throws IOException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), JSONObject.class);
    }


}
