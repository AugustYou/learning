package top.felixu.v3.mappers;

import top.felixu.v1.Person;
import top.felixu.v3.annotation.ISelect;

/**
 * @author felixu
 * @datetime 2018/4/1 22:54
 */
public interface PersonMapper {
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @ISelect("SELECT * FROM person WHERE id = ?")
    Person selectByPrimaryKey(Integer id);
}
