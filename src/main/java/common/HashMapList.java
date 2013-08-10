/* 
 * Copyright 2005 Shanghai CJTech Co. Ltd.
 * All right reserved.
 * Created on 2005-7-26
 */
package common;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author plh
 */
public class HashMapList {
    private List mList = new java.util.ArrayList();
    public void put(String name, String[] paramValues){
        if(paramValues == null){
            return;
        }
        
        for(int i = 0; i < paramValues.length; i ++){     
            HashMap map = get(i);
            map.put(name, paramValues[i]);
        }
    }
    
    public HashMap get(int index){
        HashMap retMap;
        if(mList.size() > index){ 
            retMap = (HashMap)mList.get(index);
        } else {
            retMap = new HashMap();
            mList.add(retMap);
        }
        return retMap;
    }
    
    public int size(){
        return mList.size();
    }
}
