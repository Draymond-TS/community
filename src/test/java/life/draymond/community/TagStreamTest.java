package life.draymond.community;

import life.draymond.community.cache.TagCache;
import life.draymond.community.dto.TagDTO;
import life.draymond.community.mapper.CommentExtMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagStreamTest {


    @Test
    public void test1(){
        String [] split={"aaa","java"};
        List<TagDTO> tagDTOS = TagCache.get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        System.out.println(invalid);
    }
}
