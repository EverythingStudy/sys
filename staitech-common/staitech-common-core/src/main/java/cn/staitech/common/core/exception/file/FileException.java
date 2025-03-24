package cn.staitech.common.core.exception.file;

import cn.staitech.common.core.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author staitech
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
