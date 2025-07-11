package com.manibala.test.groq.db;

import com.manibala.application.groq.api.config.ConfigProperties;
import com.manibala.application.groq.api.data.CompletionsDataPojo;
import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.application.groq.api.request.CompletionsRequest;
import com.manibala.framework.database.QueryImpl;
import com.manibala.framework.encrypt.Crypt;
import com.manibala.framework.util.ActorUtils;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class GroqDbTest {

    @Test
    @Title("DB Groq - Single Result")
    public void test1() {
        new QueryImpl().getSingleResultInRfam(ActorUtils.getActor(), "select name from author where author_id=1");
    }

    @Test
    @Title("DB Groq - Rows List")
    public void test2() {
        new QueryImpl().getRowsInListInRfam(ActorUtils.getActor(), "select name from author where author_id<10");
    }

    @Test
    @Title("DB Groq - Rows & Columns")
    public void test3() {
        new QueryImpl().getRowsColumnsInMapInRfamIfExists(ActorUtils.getActor(), "select name, last_name, initials from author where author_id<10");
    }
}
