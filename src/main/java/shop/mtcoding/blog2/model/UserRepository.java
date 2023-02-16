package shop.mtcoding.blog2.model;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

        public List<User> findAll();

        public User findById(int id);

        public int insert(@Param("username") String username, @Param("password") String password,
                        @Param("email") String email);

        public int deleteById(int id);

        public int updateById(@Param("id") int id,
                        @Param("username") String username,
                        @Param("password") String password,
                        @Param("email") String email,
                        @Param("profile") String profile,
                        @Param("createdAt") Timestamp createdAt);

        public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

        public User findByUsername(String username);

        public List<User> findByUsernameLike(String searchString);
}
