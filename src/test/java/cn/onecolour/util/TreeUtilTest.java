package cn.onecolour.util;


import cn.onecolour.entity.Org;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2023/2/8
 * @description
 */
public class TreeUtilTest {

    private static List<Org> ORGS;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        URL resource = TreeUtilTest.class.getResource("/org.json");
        File jsonFile = new File(Objects.requireNonNull(resource).toURI());
        String jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
        ORGS = JsonUtils.parseObject(new TypeReference<List<Org>>() {
        }, jsonString);
    }

    @Test
    void utilTest() {
        List<Org> tree1 = TreeUtil.collectionToTree(ORGS, Org::getParentId, Org::getId, Org::getChildren);
        List<Org> tree2 = TreeUtil.collectionToTree(ORGS.stream().peek(org -> {
            if (org.getParentId() == -1L) {
                org.setParentId(null);
            }
        }).collect(Collectors.toList()), "parentId", "id", "children");
        Assertions.assertEquals(tree1, tree2);
        //noinspection unchecked
        Set<Org> set = TreeUtil.collectionToTree(ORGS, Org::getParentId, Org::getId, Org::getChildren, Set.class);
        Set<Org> set1 = TreeUtil.treeToSet(set, Org::getParentId, Org::getId, Org::getChildren);
        Assertions.assertEquals(set1.size(), ORGS.size());


    }
}
