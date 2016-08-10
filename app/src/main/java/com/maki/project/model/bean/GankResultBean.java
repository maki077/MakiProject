package com.maki.project.model.bean;

import java.util.List;

/**
 * gank 返回对象
 */
public class GankResultBean<T> {
    /**
     error : false
     results : [{"_id":"57a15d05421aa91e2606476e",
                 "createdAt":"2016-08-03T10:55:01.123Z",
                 "desc":"一组漂亮的 TextField 交互特效",
                 "publishedAt":"2016-08-03T11:12:47.159Z",
                 "source":"chrome",
                 "type":"iOS",
                 "url":"https://github.com/raulriera/TextFieldEffects","used":true,"who":"代码家"},
                 {}
                 ]
     */
    private boolean error;
    private List<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
