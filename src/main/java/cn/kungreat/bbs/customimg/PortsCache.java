package cn.kungreat.bbs.customimg;

import cn.kungreat.bbs.vo.QueryResult;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class PortsCache {
    private static final Map<Long,Map<Long,QueryResult>> javaCache
            = new LinkedHashMap<>();


    public synchronized static QueryResult getJavaCache(Long id,Long currentPage){
        Map<Long, QueryResult> integerQueryResultMap = javaCache.get(id);
        if(integerQueryResultMap != null && !integerQueryResultMap.isEmpty()){
            QueryResult result = integerQueryResultMap.get(currentPage);
            if(result != null && result.getDatas().size() > 0){
                return result;
            }
        }
        return null;
    }

    public synchronized static void setJavaCache(Long id,Long currentPage,QueryResult result){
        Map<Long, QueryResult> integerQueryResultMap = javaCache.get(id);
        if(javaCache.size() > 30 && integerQueryResultMap == null){
            Long[] num = (Long[]) javaCache.keySet().toArray();
            javaCache.remove(num[0]);
        }
        if(integerQueryResultMap == null){
            Map<Long,QueryResult> map = new HashMap<>();
            map.put(currentPage,result);
            javaCache.put(id,map);
        }else{
            integerQueryResultMap.put(currentPage,result);
        }
    }

    public synchronized static void deleteJavaCache(Long id){
        javaCache.remove(id);
    }
}
