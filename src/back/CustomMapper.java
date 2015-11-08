package back;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class CustomMapper implements Mapper<String, String, String, String> {

    @Override
    public void map(String s, String s2, Context<String, String> stringStringContext) {

    }
}
