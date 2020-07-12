package com.hh.sell.util;

import com.hh.sell.vo.ResultVO;

public class ResultVOUtil {

    //成功
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        return resultVO;
    }

    //成功
    public static ResultVO success(){
        return success(null);
    }

    //失败
    public static ResultVO error(Integer code, String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
