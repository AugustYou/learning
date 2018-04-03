package top.felixu.v1;

/**
 * @author felixu
 * @datetime 2018/4/2 05:48
 */
public class TestV1 {
    public static void main(String[] args) {
        SqlSession sqlSession = new SqlSession(new Configuration(), new SimpleExecutor());
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
        Person person = mapper.selectByPrimaryKey(1);
        System.out.println(person);
    }
}
